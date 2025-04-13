FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y findutils
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
