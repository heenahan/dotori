FROM openjdk:17

RUN mkdir -p /app
WORKDIR /app

COPY ./dotori-0.0.1-SNAPSHOT.jar .

ENV HOST 0.0.0.0
EXPOSE 8080

CMD java -jar -Duser.timezone=Asia/Seoul dotori-0.0.1-SNAPSHOT.jar