FROM gradle:8.13-jdk-21-and-23-alpine

WORKDIR /app

COPY /app .

RUN ["./gradlew","clean", "build"]

CMD ["./gradlew", "run"]