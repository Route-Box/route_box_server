FROM gradle:8.2-jdk17-alpine AS build
WORKDIR /app

# 의존성 캐싱을 위한 단계 - 소스 코드 변경 시에도 이 레이어는 재사용됨
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon

# 소스 코드 복사 및 빌드
COPY src ./src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# 메모리 설정 최적화
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT java $JAVA_OPTS -jar /app/app.jar
