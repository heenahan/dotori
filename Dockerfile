FROM gradle:8.7.0-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle depencies --no-daemon

COPY . /app

RUN gradle clean build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/dotori-0.0.1-SNAPSHOT.jar /app/dotori.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "dotori.jar"]