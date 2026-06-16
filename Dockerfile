FROM maven:4.0.0-rc-5-eclipse-temurin-25-noble AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM maven:4.0.0-rc-5-eclipse-temurin-25-noble
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]