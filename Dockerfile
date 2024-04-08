FROM openjdk:17

ARG JAR_FILE=dotori-0.0.1-SNAPSHOT.jar

COPY ./build/libs/${JAR_FILE} .

ENV HOST 0.0.0.0
EXPOSE 8080

CMD java -jar -Duser.timezone=Asia/Seoul dotori-0.0.1-SNAPSHOT.jar