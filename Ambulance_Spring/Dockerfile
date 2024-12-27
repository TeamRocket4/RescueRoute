# Build 
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .  
COPY src ./src  
RUN mvn clean package -DskipTests  

# Runtime 
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Ambulance_Spring-0.0.1-SNAPSHOT.jar ambulance_spring.jar  
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ambulance_spring.jar"]
