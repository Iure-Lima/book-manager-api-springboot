# Book Manager
Book Manager is a REST API developed in Spring Boot for managing a book catalog. It allows you to register, search, update, and remove books. It also features advanced search filters (by title, author, year, status, categories, etc.) and interactive documentation via Swagger.

## Tools and Technologies
* Java 17+
* Spring Boot

    * Spring Web
    * Spring Data MongoDB
    * Validation
    * Springdoc OpenAPI (Swagger)
    * Lombok
* Maven
* MongoDB (NoSQL)
* Docker & Docker Compose

## Running with Docker Compose (Database + Application)

You can start **both MongoDB and the Book Manager API** with a single command:

```bash
docker compose up --build -d
# or (depending on your Docker version)
docker-compose up --build -d
```

This will:

* Create and start a MongoDB container (port **27017**)
* Build and start the Book Manager application container (port **8081**)
* Persist database data in the `mongo_data` volume

After it’s up:

* API: [http://localhost:8081](http://localhost:8081)
* Swagger UI: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

> If you change MongoDB credentials after the first run, remove volumes to recreate users/data:
>
> ```bash
> docker compose down -v && docker compose up --build -d
> ```

## Running the Project Locally (without Docker)

1. Ensure MongoDB is running locally (default port 27017).
2. Configure your `application.properties` if necessary:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/bookmanager
spring.data.mongodb.database=bookmanager
server.port=8081
```

3. Compile and run the project:

```bash
./mvnw spring-boot:run

# Or

mvn spring-boot:run
```

4. The server will be available at: [http://localhost:8081](http://localhost:8081)

## Accessing Swagger Documentation

With the project running, open Swagger UI in your browser:
[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

There you can:

* View all available endpoints
* Test requests directly from the browser
* See request/response examples
