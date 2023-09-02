# MovieService
Restful Movie Service with Spring Boot and MongoDB. Save the movies you watched and your favorites.

## Swagger UI
http://localhost:8080/movieservice/swagger-ui/index.html#/

## Kafka
Run the command prompt in the C:\Kafka folder and run the following commands in order

Start the ZooKeeper service

.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties   

 Start the Kafka broker service 
 
.\bin\windows\kafka-server-start.bat .\config\server.properties

## Docker
Docker build -t movieservice:1.0 .
docker run -p 8080:8080 --name MovieService movieservice:1.0 





