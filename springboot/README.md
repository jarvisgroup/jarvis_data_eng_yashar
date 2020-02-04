# SpringBoot Trading App
## Introduction 
This Java Application is an online stock trading simulation REST API that allows trader to buy and sell stocks built using  
SpringBoot MicroService and MVC Architecture, and Maven is used for project management. All stocks related data are extracted from IEX cloud which is a platform that makes
financial data and services accessible to everyone, those data then will be persisted in PostgreSql databases. 

## Quick Start 
- Prerequisites
    - A Machine running CENTOS 7
    - Docker installed 
    - An IEX Cloud Account for API token access
    - Set up environment variables (`PSQL_USER`, `PSQL_PASSWORD`, `IEX_PUB_TOKEN`)
- Docker Script:
    
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