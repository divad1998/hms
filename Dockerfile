FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY src/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]