# Technical Test

This project was developed using SpringBoot [Spring Initializr](start.spring.io) and the following dependencies were selected:
1. Web
2. JPA
3. Lombok

*Reference:* https://spring.io/guides/tutorials/bookmarks/

The following sections details each step to get the project up and running.


## 0. Requirements
* MVN Cli (<https://maven.apache.org/download.cgi>)
* Docker (<https://www.docker.com/products/docker-desktop>)
* Java (<https://www.java.com/en/download>)

After docker desktop installation you need to enable the option "Expose daemon on tcp://localhost:2375 without TLS" as the following steps:
1) Run Docker, go to Settings -> General
2) Click on "Expose daemon on tcp://localhost:2375 without TLS"
3) Restart Docker


## 1. Clone the project and build it
Run the command below in your terminal to clone the project:
```
$ git clone https://github.com/Augustomesquita/technical-test.git
```

## 2. Build and test easily using the bash script
Run the deploy.sh inside in the root project:
```
$ sh deploy.sh
```
Now sit back, relax and drink your coffee while all the applications are started.
After the applications starting you already can call the api's using your internet browser to the link at <http://localhost:8080/swagger-ui/index.html> and <http://localhost:8081/swagger-ui/index.html>.

## 3. The manual and hard way
### 3.1 Start MySQL locally in a Docker container

Let's first create a Docker network. It will be useful in section 4. 
```
$ docker network create technical-test-network
```
Now we can start the MySQL 8 container:
```
docker run -d -p 3306:3306 --name=stock-quote-manager-db --network=technical-test-network --env="MYSQL_USER=spring-user" --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=secret" --env="MYSQL_DATABASE=bootdb" mysql:8.0
```
*It will take some seconds.*

Run the command below to follow its initialization:
```
$ docker logs stock-quote-manager-db
```
MySQL is ready to use when the below output log is printed:
```console
2021-03-14T15:58:14.022693Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.13'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
2021-03-14T15:58:14.101713Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Socket: '/var/run/mysqld/mysqlx.sock' bind-address: '::' port: 33060
```

Try accessing MySQL container with the below command:
```
$ docker exec -it stock-quote-manager-db mysql -uspring-user -psecret
```
You will be connected to MySQL. Type `exit` to exit.

### 3.2 Run the REST API application in a container
Let's now deploy our SpingBoot application as a Docker image and run it as a container.
Inside stock-manager folder, run the command below which will create the application image:
```
$ mvn clean package -DskipTests
$ mvn clean package docker:build -DskipTests
```
The successful output of the process should be:
```console
Successfully built 39af6a93108a
Successfully tagged docker.lazydev.com/stock-manager:latest
[INFO] Built docker.lazydev.com/stock-quote-manager
[INFO] Tagging docker.lazydev.com/stock-quote-manager with 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 20.082 s
[INFO] Finished at: 2021-03-14T14:14:39-03:00
[INFO] Final Memory: 61M/396M
[INFO] ------------------------------------------------------------------------
```

Finally, run the container:
```
docker run -d -p 8080:8080 --name=stock-manager-api --network=technical-test-network docker.lazydev.com/stock-manager:latest
```
Repeat the same process inside the stock-quote-manager-api (mvn clean package commands), but replace the container running command for this following one:
```
docker run -d -p 8081:8081 --name=stock-quote-manager-api --network=technical-test-network -e MYSQL_ADDR='stock-quote-manager-db' -e STOCK_MANAGER_HOST='stock-manager-api' -e STOCK_MANAGER_PORT='8080' -e STOCK_QUOTE_MANAGER_HOST='stock-quote-manager-api' -e STOCK_QUOTE_MANAGER_PORT='8081' docker.lazydev.com/stock-quote-manager:latest
```
Note*: It is to follow the ordering. It means that stock-quote-manager-api must be the last container to be upped.

## 4. Swagger
We can use Swagger to easily interact with the REST API.

Access using your internet browser the link at <http://localhost:8080/swagger-ui/index.html> and <http://localhost:8081/swagger-ui/index.html>.
We can use this UI to run the operation calls of the projects.

## 5. Cleannig up
To stop MySQL and REST API application containers:
```
$ docker stop stock-quote-manager-db stock-quote-manager-api stock-manager-api
```
To remove stopped containers:
```
$ docker rm stock-quote-manager-db stock-quote-manager-api stock-manager-api
```
To delete the Docker images created:
```
$ docker rmi docker.lazydev.com/stock-quote-manager:0.0.1-SNAPSHOT docker.lazydev.com/stock-quote-manager:latest
$ docker rmi docker.lazydev.com/stock-manager:0.0.1-SNAPSHOT docker.lazydev.com/stock-manager:latest
```
To delete Docker network:
```
$ docker network rm technical-test-network
```
