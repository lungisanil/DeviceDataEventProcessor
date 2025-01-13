
FROM amazoncorretto:17-alpine-jdk
COPY target/DeviceDataEventProcessor-1.0.0-SNAPSHOT.war DeviceDataEventProcessor.war
ENTRYPOINT ["java", "-jar", "/DeviceDataEventProcessor.war"]

#docker build -t device-data-event-processor-image .