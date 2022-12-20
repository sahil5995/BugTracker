FROM openjdk:11-jdk
EXPOSE 8081
VOLUME /tmp
ARG JAR_FILE=target/BugTracker-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} BugTracker.jar
ENTRYPOINT ["java","-jar","/BugTracker.jar"]