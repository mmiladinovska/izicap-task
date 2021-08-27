# izicap-task
This project is a solution to a task to retrieve company information for the purpose of updating the database
of active businesses in the system.

# Build the application
```mvn clean package```

## Run the application
Open `IzicapTaskApplication` class and select Run/Debug.

## Run tests
Navigate to the project root folder and run the command
`mvn test`

## Run docker image
`docker run springio/gs-spring-boot-docker:latest`

## Run jenkins pipeline
Prior to building the project by running the jenkins pipeline, please make sure to have defined Maven installation
in the Global Tool Configuration: add Maven -> set name to M3 -> select latest version 3.8.2

## About the solution:
The businesses are identified by their SIRET.

The solution is a microservice which queries the public API for given SIRET numbers and saves new or updates the 
company information from the response in the local storage system.

It is a Spring Boot application for storing this info in a form of csv files, in the local storage system, 
i.e. in a csv file with a header of the following fields:

` 
  siret
  id
  nic
  full address
  creation date
  full name
  TVA number
  company name
  last update date 
`

The full name is taken as concatenation of the last name, usualname, firstname1, firstname2, firstname3, and 
firstname4.
The siret number is not mentioned to be stored within the task, however as it is an identifier, it is added, as well as
company name which can also be used for search of the businesses using the SIRENE API (along with the siret), and 
the update date, so that we could use this information for heck if the record has been updated since it was last saved.

To achieve this behaviour without risk of losing the existing information in the csv file, when updating the 
company information we create new temporary file. 
For each siret we query, the old csv file is traversed. All the existing information in the file for sirets that
are not included in the query set are copied in the temporary file, the information for the queried siret valued is 
taken from the service and written in the temporary file first. After the writing to the file is completed successfully, 
the path of the temporary file is replaced with the path of the old file, so that we have the updates in the same file path.