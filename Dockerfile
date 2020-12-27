FROM maven:3.3-jdk-8 AS build

WORKDIR /java
COPY src ./src
COPY pom.xml .

RUN mvn clean package

FROM anapsix/alpine-java:8u201b09_server-jre_nashorn AS runtime
COPY --from=build /java/target/kafka-quacker-1.0.0-RELEASE-jar-with-dependencies.jar ./service.jar
ENTRYPOINT ["java","-jar","service.jar"]
