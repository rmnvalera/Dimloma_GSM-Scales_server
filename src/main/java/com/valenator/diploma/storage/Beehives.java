package com.valenator.diploma.storage;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import com.valenator.diploma.util.Constants;

import static com.codahale.metrics.MetricRegistry.name;

public class Beehives {

    private FaultTolerantDatabase database;

    private final String TABLE = "beehives";
    private final String ID = "id";
    private final String DATA = "data";

    private final MetricRegistry metricRegistry = SharedMetricRegistries.getOrCreate(Constants.METRICS_NAME);
    private final Timer get = metricRegistry.timer(name(Beehives.class, "getBeehive"));

    public Beehives(FaultTolerantDatabase database) {
        this.database = database;
    }


}
