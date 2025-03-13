# Use Maven to build the WAR file first
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests

# Use official Tomcat 10.1.34 with JDK 21
FROM tomcat:10.1.34-jdk21

# Copy the generated WAR file to Tomcat's webapps directory
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
