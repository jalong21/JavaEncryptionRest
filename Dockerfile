# Use the official Gradle image to build the project
FROM gradle:7.6.2-jdk11 as builder

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the Gradle project files
COPY --chown=gradle:gradle . /home/gradle/project

# Build the project
RUN gradle build --no-daemon

# Use the official OpenJDK image to run the application
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the Gradle build container
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
