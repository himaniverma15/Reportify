FROM openjdk:8-jre-slim
VOLUME /tmp
COPY build/libs/reportify.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
