# Echo Server/Client App

Simple college project.

Compilation and excecution procedure below.

## EchoServer

### Maven

```shell
cd EchoServer;
mvn clean install;
java -jar /target/EchoServer-0.jar;
```

### Non-Maven

```shell
cd .\EchoServer\src\main\java\br\ufjf\b4hive\;
java .\EchoServer.java;
```

## EchoClient



### Maven

```shell
cd EchoClient;
mvn clean install;
java -jar /target/EchoClient-0.jar <Server IP>;
```

### Non-Maven

```shell
cd .\EchoClient\src\main\java\br\ufjf\b4hive\;
java .\EchoClient.java <Server IP>;
```
