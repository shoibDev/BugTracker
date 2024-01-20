FROM openjdk:20
COPY target/*.jar BugTracker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","BugTracker.jar"]