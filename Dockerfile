FROM openjdk:17 as build
LABEL authors="Quang Vinh <3"
COPY target/*jar /tmp
WORKDIR /tmp
EXPOSE 8080 8080
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]