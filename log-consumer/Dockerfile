FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
COPY target/log-consumer-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]