# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY untitled/pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application's source code
COPY untitled/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a smaller base image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar ./app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]