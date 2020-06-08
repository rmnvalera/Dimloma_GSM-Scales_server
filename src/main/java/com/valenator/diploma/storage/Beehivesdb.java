package com.valenator.diploma.storage;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import com.valenator.diploma.util.Constants;
import com.valenator.diploma.entities.Message;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;

import static com.codahale.metrics.MetricRegistry.name;

public class Beehivesdb {

    private FaultTolerantDatabase database;

    private final String TABLE = "beehives";
    private final String ID = "id";
    private final String DATA = "data";

    private final MetricRegistry metricRegistry = SharedMetricRegistries.getOrCreate(Constants.METRICS_NAME);
    private final Timer createTimer = metricRegistry.timer(name(Beehivesdb.class, "create"));

    public Beehivesdb(FaultTolerantDatabase database) {
        this.database = database;
    }

    public boolean create(Message messages) {
        return database.with(jdbi -> jdbi.inTransaction(TransactionIsolationLevel.SERIALIZABLE, handle -> {
            try (Timer.Context ignored = createTimer.time()) {
                int rows = 0;
                rows = handle.createUpdate("INSERT INTO database (dht_humidity_1, dht_humidity_2, dht_temperature_1, dht_temperature_2, weight, balance, timestamp) VALUES (:dht_humidity_1, :dht_humidity_2, :dht_temperature_1, :dht_temperature_2, :weight, :balance, :timestamp)")
                        .bind("dht_humidity_1",     messages.getDhtHumidity1()    == 0?null:messages.getDhtHumidity1()      )
                        .bind("dht_temperature_1",  messages.getDhtTemperature1() == 0?null:messages.getDhtTemperature1()   )

                        .bind("dht_humidity_2",     messages.getDhtHumidity2()    == 0?null:messages.getDhtHumidity2()      )
                        .bind("dht_temperature_2",  messages.getDhtTemperature2() == 0?null:messages.getDhtTemperature2()   )

                        .bind("weight",             messages.getWeight()          == 0?null:messages.getWeight()            )
                        .bind("balance",            messages.getBalance()                                                   )
                        .bind("timestamp",          System.currentTimeMillis())
                        .execute();
                return rows == 1;
            }
        }));
    }

}
