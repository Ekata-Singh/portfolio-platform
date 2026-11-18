# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw package -DskipTests -B

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --uid 1001 spring \
    && mkdir -p /data/uploads && chown -R spring /data
COPY --from=build /app/target/*.jar app.jar
USER spring

# Free hosting tiers give ~512MB RAM: cap the heap and keep startup lean.
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -XX:+UseSerialGC -XX:TieredStopAtLevel=1 -Xss512k"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
