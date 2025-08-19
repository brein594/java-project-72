FROM gradle:8.12.1-jdk21

WORKDIR /app

COPY . .

RUN ["./make", "build"]

CMD ["./make", "run"]