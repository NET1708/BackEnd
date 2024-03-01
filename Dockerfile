FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]