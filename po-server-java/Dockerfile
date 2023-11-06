# Stage 1: Build the Java application
FROM openjdk:11-jdk AS build
WORKDIR /workspace/app

# Copy the source code into the image
COPY src /workspace/app/src

# Copy the libraries if any
COPY lib /workspace/app/lib

# Compile the Java application
RUN javac -d bin -cp src:lib/* src/*.java

# Stage 2: Run the Java application
FROM openjdk:11.0.11-jre-slim-buster
COPY --from=build /workspace/app/bin /app/bin
COPY --from=build /workspace/app/lib /app/lib

# Set the working directory
WORKDIR /app

# Run the Java application
CMD ["java", "-cp", "bin:lib/*", "App"]
