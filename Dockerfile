FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY app.jar app.jar

# 메모리 설정 최적화
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT java $JAVA_OPTS -jar /app/app.jar
