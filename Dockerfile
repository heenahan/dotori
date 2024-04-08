<<<<<<< HEAD
FROM gradle:8.7.0-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

COPY . /app

RUN gradle clean build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/dotori-0.0.1-SNAPSHOT.jar /app/dotori.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "dotori.jar"]
=======
FROM openjdk:17

RUN mkdir -p /app
WORKDIR /app

COPY build/libs/dotori-0.0.1-SNAPSHOT.jar .

ENV HOST 0.0.0.0
EXPOSE 8080

CMD java -jar -Duser.timezone=Asia/Seoul dotori-0.0.1-SNAPSHOT.jar
>>>>>>> f05407e (docs : 도커 이미지 만듦)
