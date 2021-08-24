# izicap-task

This project is a solution to a task to retrieve company information for the purpose of updating the database
of active businesses in the system. 
The businesses are identified by their company description, which in France is called the SIRET.
SIRET is unique identifier for a business in France and this info is public, available on the 

The solution is a microservice which queries the public API for given SIRET numbers and saves or updates the company information
from the response in the local storage system.

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

The full name is taken as concatenation of the last name, usual name, first name 1, first name 2, first name 3, and 
first name 4. 
The siret number is not mentioned to be stored within the task, however as it is an identifier, it is added, as well as
company name which can also be used for search of the businesses using the SIRENE API (along with the siret), and 
the update date, so that we can check if the record has been updated by comparing this field only.

If the record has different (later) update date from the one stored in the csv file, then we compare the rest of the
fields to identify the changed fields, and the new values should replace the old in the csv file.

To achieve this behaviour without risk of losing the existing information in the csv file, 
if there are changes to be made in any of the company information, those are identified first by comparing the existing
with the information received from the sirene service for the list of siret values.
After this, another temporary file is created, including the unchanged and the updated (changed) records replacing the
old ones. After all the records are written successfully, the old file is deleted and the new one is renamed
to the same name as the old one.

## Build the application
```mvn clean package```
## Run the application
Open `IzicapTaskApplication` class and select Run/Debug.

##Run docker image
`docker run springio/gs-spring-boot-docker:latest`

## Run tests
Navigate to the project root folder and run the command
```mvn test```
