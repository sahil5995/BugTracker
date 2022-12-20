# Bug Tracker

Tool to create and manage the bugs of an application.

## Prerequisites

* Java 11
* Maven 3.8.*
* Docker(Optional)

## Usage
1) Import the project as Java maven project into IDE.
3) Compile and build the the code using ```./mvnw clean install```
4) Run application locally using ```./mvnw spring-boot:run```
5) Or build it and run it as Java application from BugTrackerApplication.java class.
6) Application should be running now at http://localhost:8081.

## Docker (Optional)
* Run the following command to create and run the image
```
docker build -t bug-tracker:1.0 .
docker run -p 8081:8081 bug-tracker:1.0
```

## H2 Database
* H2 database should be accessible at http://localhost:8081/h2


## API Usage
* To use the API, hit the API URL http://localhost:8081/bugtracker (GET)
* It will show the bug details in response as JSON format.


## Health Check EndPoint
* The production ready features like health check is implemented for the tool.
* The health check can be performed with the help of URL:
  ```http://localhost:8081/actuator/health```

* This will show if the tool is up or down. Example response:
  ```{"status":"UP"}```