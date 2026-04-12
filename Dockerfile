# ✅ Fixed Dockerfile
FROM eclipse-temurin:25-jdk-alpine

EXPOSE 8080

COPY ./target/java-maven-app*.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "java-maven-app*.jar"]