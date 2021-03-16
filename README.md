# ATM Simulation app

## To run the project :

1. Create an executable JAR file from the application

      if you are using Intellij IDE, you can create JAR by
      
      - Go to **File > Project Structure**
      - Tab **Artifact** > click "+" button.
      - Choose **JAR > From modules with dependencies..**
      - Select your main class and click OK
      - Build the artifact on menu **Build > Build Artifacts**

2. From the JAR directory, Run it with the command `java -Dfile={file_csv_source_path} -jar {jar_name}.jar`
      - if -Dfile not provided, the app will throw error 


## ATM-Simulation 2

- Add capability to read User Data from CSV file
- Externalize file path configuration

## ATM-Simulation 1

- Withdraw feature
- Fund Transfer feature
