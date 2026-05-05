FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY . .

WORKDIR /app/backend

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build /app/backend/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]