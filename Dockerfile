# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw \
    && ./mvnw -B -ntp -DskipTests dependency:resolve dependency:resolve-plugins

COPY src ./src
RUN ./mvnw -B -ntp -DskipTests package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN groupadd --system spring \
    && useradd --system --gid spring --home-dir /app --shell /usr/sbin/nologin spring \
    && chown spring:spring /app

COPY --from=build --chown=spring:spring /workspace/target/prestamoservice-*.jar /app/app.jar

USER spring

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
