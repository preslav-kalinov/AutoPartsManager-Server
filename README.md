# AutoPartsManager-Server
Auto parts management system REST API.

## Used technologies
- Java 11;
- MySQL;
- Spring REST.

## How to run
1. Clone this repository.
2. Create a new database using the autoparts.sql file from the sql folder.
3. Create a new MySQL user that has SELECT, INSERT, UPDATE and DELETE permissions on the newly created database.
4. Rename the application.properties.dist file in the src/main/resources folder to application.properties and modify it as follows:
    - Change the spring.datasource.username and spring.datasource.password parameters accordingly to your newly created MySQL user.
    - Change the spring.mail.username and spring.mail.password parameters to your Gmail account credentials.
    - Set autopartsmgmt.mailservice.receiver to the email you would like to receive notifications to.
    - Set autopartsmgmt.fileservice.filepath to the path where your sale logs will be saved.
    - Change the example.com host in http.allowed-origins to the host where your web client is located.
5. Run the app using mvn spring-boot:run.