FROM maven:3.6.1-jdk-11 AS builder
VOLUME ./maven-repo/ /root/.m2./
WORKDIR /mymaven
COPY . /mymaven
RUN mvn clean install

FROM openjdk:11
WORKDIR /myapp/
#COPY config/config.yml /myapp/
COPY --from=builder /mymaven/target/GSM-Scales-1.0.jar /myapp/
#ENTRYPOINT [ "java", "-jar", "GSM-Scales-1.0.jar" ]
