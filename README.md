# How to run
[1] Install latest [JRE 11](https://www.oracle.com/technetwork/java/javase/downloads/index.html) or higher or Make sure you have [OpenJDK](https://openjdk.java.net/install/)

[2] Ensure you have version 11 or higher
```` shell script
java --version
````
[3] Install and configure [maven](https://maven.apache.org/).

[4] Make sure you have Maven 3.6 or higher:
```` shell script
mvn -v
```` 
[5] Make sure you are application's directory.

[6] Run the application using following command:
```` shell script
mvn spring-boot:run
```` 

- You can see the swagger API documentation here:
http://localhost:8080/swagger-ui.html#/

## Notes
- API runs on 8080 Port make sure the port is available.
- All API are on `http://localhost:8080/api/v1/members/`
- Make sure application have permission for `uploadDir` you can get the directory's location using `health check` API 

## List of APIs

| HTTP Verb | URL | Description |
| --------- | --- | ----------- | 
| GET | /api/v1/members | Get list of all members |
| POST | /api/v1/members | Create new member |
| GET | /api/v1/members/{id} | Get a member by given `{id}` in path |
| PUT | /api/v1/members/{id} | Update a given Member by `{id}` |
| DELETE | /api/v1/members/{id} | delete a given Member by `{id}` |
| GET | /api/v1/members/downloadPicture/{fileName} | download a file by name |
| POST | /api/v1/members/uploadPicture/{id} | upload a picture file for a given Member by `{id}` |
| GET | /api/v1/members/health | health check |

# CodingChallenge Developer:

Create your own branch and commit to that branch only.    
When you completed the assignment, please make a pull request, so we get a notifaction that you are done.     
Submit your best work, where you follow all the best practices of software development.    
Imagine this is not a test but it's your daily work.


# Epic/Story
As a technical lead I want to have a "Member" service so that I can easily:
* Create a new member
* Read an existing member
* Update an existing member
* Delete members which are no longer used
* List existing members
* No frontend needed, only REST services are needed

# Acceptance Criteria
* RESTful Web Service with Spring/Spring Boot
* Member has the following attributes:
  * First name
  * Last name
  * Date of birth
  * Postal code (ZIP)
  * Picture (as File)
* Accepts JSON or XML
* Response in JSON or XML
* JDK8 or higher
* Build with Maven or Gradle
* Data storage: (in memory) database
* Documentation how to start and how to call the service

# HINT
## Keep in mind:
* Agile manifesto
* Clean code
* Software craftsmanship
