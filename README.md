# ATM Simulation app

## Project Requirements
- Maven
- PostgreSQL DB 
- JDK version 8 or later

## To run the project :
`mvn spring-boot:run`

## To run the project from JAR file : 
Create an executable JAR file from the application

with Maven
- on the project root, type `mvn clean install`
- head to target folder, then run `java -jar {jar_name}.jar`

without Maven
if you are using Intellij IDE, you can create JAR by
- Go to **File > Project Structure**
- Tab **Artifact** > click "+" button.
- Choose **JAR > From modules with dependencies..**
- Select your main class and click OK
- Build the artifact on menu **Build > Build Artifacts**
- From the JAR directory, Run it with the command `java -jar {jar_name}.jar`


## ATM-Simulation 3
- Add Maven Support
- Add Spring Boot Support
- Add Thymeleaf templating view

## ATM-Simulation 2

- Add capability to read User Data from CSV file
- Externalize file path configuration

## ATM-Simulation 1

- Withdraw feature
- Fund Transfer feature
