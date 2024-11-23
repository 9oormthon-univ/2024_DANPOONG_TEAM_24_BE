FROM eclipse-temurin:21 as builder
WORKDIR /app
COPY . /app
RUN ./gradlew build -x test

FROM eclipse-temurin:21
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=prod"]
