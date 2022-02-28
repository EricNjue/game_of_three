# Game Of Three  - Coding Challenge

### Technologies Used

* Java
* Maven
* Spring Boot
* Docker & Docker Compose
* JUnit & Mockito
* MySQL

# Running The Project

## IDE Approach
* Ensure you've an instance of MySQL running.
* Open the project using your IDE of choice. e.g IntelliJIDEA
* Under `application.yml` file, edit the `spring.datasource.*` values to
  match your MySQL instance.
* Run the application. This will wire up the application on port **9090**

## Docker Approach

Using this approach will autowire an instance of MySQL and also open the
application on port **9090**.

**NB**:- _This MySQL instance will try to use port **3306**. Make sure no other application(s)
is using this port._

Within the project root folder execute the following commands :-
* `mvn clean install`
* `docker-compose up`


# Thank You :smile:
Eric G Njue