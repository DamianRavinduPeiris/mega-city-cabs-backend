# Use official Tomcat 10.1.34 image with JDK 21
FROM tomcat:10.1.34-jdk21

# Set environment variables (for local dev & Render)
ENV PORT=8080

# Copy the WAR file to the Tomcat webapps directory
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
