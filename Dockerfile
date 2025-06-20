# --------------------------
# Stage 1: Build the WAR using Maven and Java 11
# --------------------------
FROM maven:3.8-openjdk-11 AS build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Copy local JARs
COPY lib/*.jar /app/lib/

# Install the local JARs into Docker’s Maven repo
RUN mvn install:install-file -Dfile=lib/aspose-cad-20.11.jar -DgroupId=com.aspose -DartifactId=aspose-cad -Dversion=20.11 -Dpackaging=jar && \
    mvn install:install-file -Dfile=lib/dxfparser-1.0.jar -DgroupId=com.example -DartifactId=dxfparser -Dversion=1.0 -Dpackaging=jar && \
    mvn install:install-file -Dfile=lib/flanagan-1.0.jar -DgroupId=com.example -DartifactId=flanagan -Dversion=1.0 -Dpackaging=jar

# Build the project (now it can resolve all dependencies)
RUN mvn clean package -DskipTests

# --------------------------
# Stage 2: Deploy the WAR to Tomcat
# --------------------------
FROM tomcat:9.0-jdk11

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from previous stage to Tomcat's webapps
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
