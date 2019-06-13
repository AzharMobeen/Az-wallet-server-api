### Az-wallet-server-api ###
It's gRPC server application in which I have configured Spring Boot gRPC framework with [LogNet/grpc-spring-boot-starter](https://github.com/LogNet/grpc-spring-boot-starter "LogNet Detail")

##### Technologies #####
* Java 8+
* Spring Boot 2.1.5
* Spring Data JPA
* Hibernate
    * Hibernate Core {5.3.10.Final}
    * Hibernate Commons Annotations {5.0.4.Final}
* MySQL 5.6
* gRPC
    * io.github.lognet:grpc-spring-boot-starter:3.3.0
    * io.grpc:protoc-gen-grpc-java:1.21.0
    * com.google.protobuf:protoc:3.7.1
* Gradle
    * protobuf-gradle-plugin:0.8.8
* JUnit

#### My Important choices: ####
* I'm using Spring Boot 2.x default Connection pooling (Hikari)
* MySQL DB default value for number of connections is **151** so I make my max connection pool size 151.
* I have configured ThreadPoolTaskExecutor with respect to my machine
    * My machine: *3rd Generation Intel Core i5-3320M (2.60 GHz, 3MB L3 cache, 35 W, 2 cores)*
    * I used `threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());`
    in which rejected thread will be added again to blockingQueue
* Logging
* Comments on Class level, Method level and some time in side method for important logic.
* For Testing purpose you can also use [evans CLI](https://github.com/ktr0731/evans#from-github-releases) it's very nice tool to test gRPC server.
* Some required dummy data will be inserted by spring boot from these files    

    * [schema.sql](https://github.com/AzharMobeen/Az-wallet-server-api/blob/master/src/main/resources/schema.sql)
    * [data.sql](https://github.com/AzharMobeen/Az-wallet-server-api/blob/master/src/main/resources/schema.sql)

##### How to Run *[Help](https://spring.io/guides/gs/spring-boot/ "Help")*: #####
* Please create schema
 ``` 
 Drop schema if exists user_wallet_db;
 Create schema user_wallet_db;
 ```
* We can run in different ways: 
    * From project folder run this command 
  ```
  gradlew build && java -jar build/libs/wallet-server-0.0.1-SNAPSHOT.jar   
  ```
    * Or you can run
  ```
  gradle :bootrun
  ```
* Az-Wallet-Client-App [Link](https://github.com/AzharMobeen/Az-wallet-client-app)