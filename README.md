# Book Manager
Book Manager is a REST API developed in Spring Boot for managing a book catalog. It allows you to register, search, update, and remove books. It also features advanced search filters (by title, author, year, status, categories, etc.) and interactive documentation via Swagger.

## Tools and Technologies
- Java 17+
- Spring Boot
  - Spring Web
  - Spring Data MongoDB
  - Validation
  - Springdoc OpenAPI (Swagger)
  - Lombok
- Maven
- MongoDB (banco NoSQL)
- Docker & Docker Compose

## Running the Database with Docker Compose
The project uses MongoDB as a base, currently it is possible to upload the database that we will use using the command below to run a container with this database.
```bash
  docker-compose up -d
```
This will:
- Create a MongoDB container
- Map port 27017
- Persist data in mongo_data

## Running the Project
1. Ensure MongoDB is running (via Docker or locally).
2. Configure your application.properties if necessary:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/bookmanager
spring.data.mongodb.database=bookmanager
```
3. Compile and run the project:
```bash
  ./mvnw spring-boot:run
  
  # Or
  
  mvn spring-boot:run
```
4. The server will be available at: http://localhost:8081

## Accessing Swagger Documentation
With the project running, access Swagger UI in the browser: http://localhost:8081/swagger-ui/index.html

There you can:
- View all available endpoints
- Test requests directly from the browser
- See request/response examples