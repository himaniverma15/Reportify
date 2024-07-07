FROM amazoncorretto:8

WORKDIR /app

COPY build/libs/reportify.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
