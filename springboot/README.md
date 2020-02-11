# SpringBoot Trading App
## Introduction 
This Java Application is an online stock trading simulation REST API that allows trader to buy and sell stocks built using  
SpringBoot MicroService and MVC Architecture, and Maven is used for project management. All stocks related data are extracted from IEX cloud which is a platform that makes
financial data and services accessible to everyone, Application data will be persisted in PostgreSql databases, and JDBC will be used to connect those 
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
    After both containers are up and running, we need to make HTTP call using SWAGGER UI to consumer the REST API  
    entering http://localhost:5000/swagger-ui.html from your web browser to consume the REST API  
    
    

##  Architecture
Component Diagram

1.Controller Layer: Controller Layer handles HTTP request from the clients, each controller handles different requests, 
and calling different methods from corresponding services and then, send HTTP responses back to the clients.  

2.Service Layer: Service Layer handles all the business logic within the application, such as deposit or withdraw funds to accounts,
buy or sell orders etc, and it calls corresponding DAO layers for data access.  

3.DAO Layer: DAO layers implements several data access objects, persist data from Psql Database using JDBC, and retrieve market data from IEX Cloud
using HTTP requests. 

4.SpringBoot: Spring Boot framework is used in this application to create a micro service and manage dependencies, and the embedded tomcat servlet is used to deploy the whole app.

5.PSQL and IEX: PostgresSQL database is used to host application data, in case of application failed or crushed, the entire application does 
not lose any history data, IEX Cloud is a REST API that provides market data, those data will be extracted to MarketData DAO via HTTP client.

##  REST API Usage
###  Swagger
Swagger allows you to describe the structure of your APIs so that machines can read them, Swagger can automatically generate clients libraries for 
your API by asking the API to return a JSON that contains detailed description fo your entire API.  
In this trading App, Swagger provides description to all the API endpoints and all Model entities. 

### [Quote Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/QuoteController.java)
Quote Controller handles HTTP requests that access, modify information for `Quote` Table from PostgresDatabase, and retrieval from IEX Cloud
 It has 5 endpoints:  
 - GET/quote/dailylist: retrieve the latest Quote information using   
 - GET/quote/iex/ticker/{tickers}: retrieve 
 - POST/quote/tickerID/{tickerId}  
 - PUT/quote/
 - PUT/quote/iexMarketData 
 
### [Trader Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/TraderAccountController.java)  

- DELETE/trader/tradeId/{traderId}: delete a trader
- POST/trader/: create a trader and an account with DTO
- POST/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{emails}: create a 
trader and an account
- PUT/trader/deposite/traderId/{traderId}/amount/{amount}: Deposit a fund
- PUT/trader/withdraw/tradeId/{traderId}/amount/{amount}: Withdraw a fund

### [Order Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/OrderController.java) 

- POST/order/marketOrder: submit a market order
### [App Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller) 
- GET/health : health
### [DashBoard Controller](./springboot/src/main/java/ca/jrvs/apps/trading/controller/DashboardController.java) 

- GET/dashboard/portfolio/traderId/{traderId}: show portfolio by traderId
- GET/dashboard/profile/traderId/{traderId}: show trader profile by traderId

## Docker Deployment 
docker diagram 

## Improvements