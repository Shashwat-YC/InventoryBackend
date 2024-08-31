FROM gradle:8.4.0-jdk17-alpine as BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM eclipse-temurin:17.0.10_7-jre-alpine
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/server.jar
RUN touch /app/.env
ENTRYPOINT ["java","-jar","/app/server.jar"]
