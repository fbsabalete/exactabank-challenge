FROM maven:3-amazoncorretto-17 AS build

COPY src /opt/src
COPY pom.xml /opt

RUN mvn -f /opt/pom.xml clean package -DskipTests

FROM amazoncorretto:17

ENV APP_NAME=exactabank
COPY --from=build /opt/target/${APP_NAME}.jar /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
EXPOSE 8080