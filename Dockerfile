## Etap 1: Budowanie aplikacji
#FROM maven:3.8.3-openjdk-17-slim AS build
#WORKDIR /app
#COPY src ./src
#COPY pom.xml .
#RUN mvn clean package -DskipTests
#
## Etap 2: Uruchamianie aplikacji
#FROM openjdk:17-slim
#WORKDIR /app
#COPY --from=build /app/target/hangman-3.jar ./app.jar
#
#CMD ["java", "-jar", "app.jar"]

# Etap 1: Budowanie aplikacji
# Używanie obrazu Mavena specyficznego dla architektury ARM

FROM --platform=linux/arm64/v8 maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

# Etap 2: Uruchamianie aplikacji
# Używanie obrazu OpenJDK specyficznego dla architektury ARM
FROM --platform=linux/arm64/v8 openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/hangman-3.jar ./app.jar

CMD ["java", "-jar", "app.jar"]