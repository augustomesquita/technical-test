# Technical Test

This project was developed using SpringBoot [Spring Initializr](start.spring.io) and the following dependencies were selected:
1. Web
2. JPA
3. Lombok

*Reference:* https://spring.io/guides/tutorials/bookmarks/

The following sections details each step to get the project up and running.

## 1. Clone the project and build it
Run the command below in your terminal to clone the project:
```
$ git clone https://github.com/Augustomesquita/technical-test.git
```
Go inside project folder:
```
$ cd technical-test
```
Build the project:
```
$ mvn clean package -DskipTests
```
## 2. Start MySQL locally in a Docker container

Let's first create a Docker network. It will be useful in section 4. 
```
$ docker network create technical-test-network
```
Now we can start the MySQL 8 container:
```
docker run -d -p 3306:3306 --name=technical-test-db --network=technical-test-network --env="MYSQL_USER=spring-user" --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=secret" --env="MYSQL_DATABASE=test" mysql:8.0
```
*It will take some seconds.*

Run the command below to follow its initialization:
```
$ docker logs technical-test-db
```
MySQL is ready to use when the below output log is printed:
```console
2021-03-14T15:58:14.022693Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.13'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
2021-03-14T15:58:14.101713Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Socket: '/var/run/mysqld/mysqlx.sock' bind-address: '::' port: 33060
```

Try accessing MySQL container with the below command:
```
$ docker exec -it technical-test-db mysql -uspring-user -psecret
```
You will be connected to MySQL. Type `exit` to exit.

## 3. Launch the application and interact with it
From the project root folder, run the command below to launch the application:
```
$ mvn clean spring-boot:run
```
A successful output log will be:
```console
2021-03-14 14:08:19.985  INFO 4064 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-03-14 14:08:19.989  INFO 4064 --- [           main] c.m.s.SpringApplication         : Started SpringApplication in 8.592 seconds (JVM running for 20.038)
2021-03-14 14:08:20.125  INFO 4064 --- [           main] c.m.s.load.LoadUserDatabase              : Preloading User(id=1, name=Augusto Mesquita, email=augustomesquita@email.com)
```
### 3.1 Curl
First, lets test it using curl to get all users:
```
$ curl -X GET "http://localhost:8080/api/users"
```
The output log should be:
```console
[{"id":1,"name":"Augusto Mesquita","email":"augustomesquita@email.com"}]
```
### 3.2 Swagger
We can use Swagger to easily interact with the REST API.

Access using your internet browser the link at <http://localhost:8080/swagger-ui/index.html>.

We can use this UI to run POST, PUT, GET and DELETE calls.

Let's add a new user. Open the **POST** section and click on **"Try it out"** button.

The below window will appear. Fill with the JSON information want  for your new user and click **"Execute"** button in the bottom.

![alt text](https://github.com/Augustomesquita/technical-test/blob/master/figures/figure1.png)

The following output log messages indicates that the call was successful:

![alt text](https://github.com/Augustomesquita/technical-test/blob/master/figures/figure2.png)

Try the other sections: GET, PUT and DELETE.

## 4. Run the REST API application in a container
Let's now deploy our SpingBoot application as a Docker image and run it as a container.

First step is to check if your docker is with the option "Expose daemon on tcp://localhost:2375 without TLS" enabled. To check this do the following steps:
1) Run Docker, go to Settings -> General
2) Click on "Expose daemon on tcp://localhost:2375 without TLS"
3) Restart

After this, run the command below which will create the application image:
```
$ mvn clean package docker:build -DskipTests
```
The successful output of the process should be:
```console
Successfully built 39af6a93108a
Successfully tagged docker.mycompany.com/technical-test:latest
[INFO] Built docker.lazydev.com/technical-test
[INFO] Tagging docker.lazydev.com/technical-test with 0.0.1-SNAPSHOT
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
docker run -d -p 9000:8080 --name=technical-test-api --network=technical-test-network -e MYSQL_ADDR=technical-test-db docker.lazydev.com/technical-test:latest
```
After some seconds, try to query the users using curl command on the container's port 9000: 
```
$ curl -X GET "http://localhost:9000/api/users"
```

## 5. Cleannig up
To stop MySQL and REST API application containers:
```
$ docker stop technical-test-db technical-test-api
```
To remove stopped containers:
```
$ docker rm technical-test-db technical-test-api
```
To delete the Docker images created:
```
$ docker rmi docker.lazydev.com/technical-test:0.0.1-SNAPSHOT docker.lazydev.com/technical-test:latest
```
To delete Docker network:
```
$ docker network rm technical-test-network
```
