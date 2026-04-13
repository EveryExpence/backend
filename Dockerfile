FROM maven:4.0.0-rc-5-eclipse-temurin-25-noble

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn", "spring-boot:run"]