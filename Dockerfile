FROM openjdk:17-jdk
WORKDIR /app
COPY target/cafe-0.0.1-SNAPSHOT.jar /app/cafe.jar

EXPOSE 8080

CMD ["java", "-jar", "cafe.jar"]