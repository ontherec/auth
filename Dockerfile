FROM openjdk:17-alpine as builder
COPY . .
RUN ./gradlew clean bootJar

FROM openjdk:17-alpine
COPY --from=builder /build/libs/*.jar application.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "application.jar"]
