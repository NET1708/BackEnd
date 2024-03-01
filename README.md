# How to start project

## Install make
```shell
choco install make
```
## make: build to start docker container
```shell
make
```

## make up to start run api
```shell
make up
```

## After make up like this you can run mvn spring-boot:run to start migration data to database
![image](https://github.com/NET1708/BackEnd/assets/76089021/e6c34d47-12c3-4046-bfb8-d86c90c5e5da)

```shell
mvn spring-boot:run
```

## make down to stop docker
```shell
make down
```

## make restart to restart docker
```shell
make restart
```

## delete system prune volumes
```shell
make prune
```