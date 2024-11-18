# Step 1: Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from the host's libs directory to the container's /app directory
COPY libs/park-0.0.1-SNAPSHOT.jar /app/park-0.0.1-SNAPSHOT.jar

# Step 4: Expose port 8080 (the default Spring Boot port)
EXPOSE 8080

# Step 5: Define the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/park-0.0.1-SNAPSHOT.jar"]
