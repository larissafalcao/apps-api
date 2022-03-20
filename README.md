# Apps API
This project is an API to manage apps.

### Requires ###
* Java 11
* Docker - https://docs.docker.com/install/
* Docker compose - https://docs.docker.com/compose/install/
* Maven - https://maven.apache.org/download.cgi

## Execute docker-compose for local environment
The docker-compose file its located at the project root.
```sh
docker-compose up -d
```
This command it's going to start:
- Postgres server.

### Start application server from code by maven ###
```sh
mvn spring-boot:run
```

After the application started access the url:
```sh
http://localhost:8080/swagger-ui.html
```
You should see the swagger documentation

### Tests ###
To run test open the file AppControllerTest in src/test/java/com.rogalabs.rogalabsapi
First run the test called saveSuccess(), after you can run all the other tests.
