<<<<<<< HEAD
# 빌드 이미지 jdk 17 & gradle 8.7.0
=======
<<<<<<< HEAD
>>>>>>> ff389f3 (docs : 도커 이미지 만듦)
FROM gradle:8.7.0-jdk17 AS build

# 소스코드를 복사할 작업 디렉토리
WORKDIR /app

# 라이브러리 설치에 필요한 파일만 복사
COPY build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

# 호스트 머신의 소스코드를 작업 디렉토리로 복사
COPY . /app

# gradle 빋드를 실행하여 JAR 파일 생성
RUN gradle clean build --no-daemon

# 런타임 이미지로 openjdk 17 지정
FROM openjdk:17-jdk-slim

# 애플리케이션을 실행할 작업 디렉토리를 생성
WORKDIR /app

# 빌드 이미지에서 생성된 JAR 파일을 런타임 이미지 복사
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
