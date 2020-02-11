# SpringBoot Trading App
## Introduction 
This Java Application is an online stock trading simulation REST API that allows trader to buy and sell stocks built using  
SpringBoot MicroService and MVC Architecture, and Maven is used for project management. All stocks related data are extracted from IEX cloud which is a platform that makes
financial data and services accessible to everyone, those data then will be persisted in PostgreSql databases, and JDBC will be used to connect those 
data to the application.
 
## Quick Start 
- Prerequisites
    - A Machine running CENTOS 7
    - Docker installed 
    - An IEX Cloud Account for API token access
    - Set up environment variables (`PSQL_URL`, `PSQL_USER`, `PSQL_PASSWORD`, `IEX_PUB_TOKEN`)
- Docker Script:
    - Build images  
    ```bash
        #Start docker 
        systemctl status docker || systemctl start docker 
        #Build images from Dockerfile 
        docker image build -t trading-psql ./springboot/psql/Dockerfile
        docker image build -t trading-app ./springboot/Dockerfile
        #List all images to verify
        docker image ls       
    ```
    - Create docker network   
    ```bash 
        #create a docker network, so that app image and psql image can interact with  each other  
        docker network create --driver birdge trading-net
    ```
    - Start containers
    ```bash
        #Start psql docker container, and attach the container to the trading-net netowrk 
        docker run --name <psql_container_name> -e "PSQL_USER=${PSQL_USER}" \
        -e "PSQL_PASSWORD=${PSQL_PASSWORD}" \ -e "IEX_PUB_TOKEN=${IEX_PUB_TOKEN}" \ 
        -- network trading-net \ -p 5432:5432 -t trading-psql
        
        #Start app docker container, and attch it to the network 
        docker run -d --name <app_container_name>  -e "PSQL_URL=${PSQL_URL}" \ 
        -e "PSQL_USER=${PSQL_USER}" \ -e "PSQL_PASSWORD=${PSQL_PASSWORD}" \
        -e "IEX_PUB_TOKEN=${IEX_PUB_TOKEN}" \ --network trading-net \ -p 5000:8080 -t trading-app
  
        #Verify two running docker containers 
        docker ps 
    ```
    
- TradingApp with SwaggerUI: 

##  Architecture
Component Diagram

1.Controller Layer

2.Service Layer

3.DAO Layer

4.SpringBoot

5.PSQL and IEX

##  REST API Usage
###  Swagger

### [Quote Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/QuoteController.java) 
### [Trader Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/TraderAccountController.java)  
### [Order Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/OrderController.java) 
### [App Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller) 
### [DashBoard Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/DashboardController.java) 

## Docker Deployment 
docker diagram 

## Improvements