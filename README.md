# Microservice Example Project Setup

This project allows you to run a microservice example using Docker, Java 19, and Maven. Follow the steps below to set up your environment, build each service, and run the project using Docker Compose.

## Requirements

1. **Git** - Git must be installed to clone the repository.
2. **Java 19** - Java 19 must be installed to compile and run the project.
3. **Maven** - Maven must be installed to build the individual microservices.
4. **Docker and Docker Compose** - Docker and Docker Compose must be installed to run the microservices using Docker.

### 1. Install Git

If Git is not installed, you can download and install it from the [Git Download Page](https://git-scm.com/downloads) for your operating system.

### 2. Install Java 19

To install Java 19, you can download the appropriate version from the official Java website:
- [Java 19 Download Page](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)

After installation, you can verify the installation by running the command `java -version` in the terminal.

### 3. Install Maven

If Maven is not installed, you can download and install it from the [Maven Download Page](https://maven.apache.org/download.cgi). Once installed, verify the installation by running `mvn -v` in your terminal.

### 4. Install Docker and Docker Compose

To install Docker and Docker Compose, follow these steps:

- **Install Docker**: You can download Docker for your operating system from the [Docker Get Started Page](https://www.docker.com/get-started).
- **Install Docker Compose**: Docker Compose usually comes with Docker. However, if you need to install it separately, you can follow the instructions on the [Docker Compose Install Page](https://docs.docker.com/compose/install/).

After installation, you can check that the installation was successful by running the commands `docker --version` and `docker-compose --version` in the terminal.

---

## Project Setup

### 5. **Clone the Project Repository**

To clone the project repository to your local machine, run the following command in the terminal:

```bash
git clone https://github.com/sebahat/MicroserviceExample.git 
```

### 6. **Build the Microservices**

The project contains several microservices that need to be built separately using **Maven**. These include:

- **`api-gateway`**
- **`currency-conversion-service`**
- **`currency-exchange-service`**
- **`naming-server`**

Follow the steps below to build each of these services:

#### 1. **Build the API Gateway**

Navigate to the **api-gateway** directory and run the Maven build command:

```bash
cd MicroserviceExample/api-gateway
mvn clean install
```

#### 2. **Build the Currency Conversion Service**

Navigate to the **currency-conversion-service** directory and run the Maven build command:

```bash
cd ../currency-conversion-service
mvn clean install
```
#### 3. **Build the Currency Exchange Service**

To build the **Currency Exchange Service**, navigate to the **currency-exchange-service** directory and run the Maven build command:

```bash
cd MicroserviceExample/currency-exchange-service
mvn clean install
```
#### 4. **Build the Naming Server**

Navigate to the **naming-server** directory and run the Maven build command:

```bash
cd ../naming-server
mvn clean install
```
### 7. **Run Docker Compose**

Once all the services have been built successfully, you can proceed to run Docker Compose to bring up the microservices.

#### Navigate to the Project Root Directory:

Go back to the root directory of the project where the **docker-compose.yml** file is located:

```bash
cd ..
```
#### **Build and Start the Containers:**

Run the following command to build the Docker images and start the services:

```bash
docker-compose up --build
```
### 8. **Verify the Services**

After running Docker Compose, you can verify that the services are running correctly by visiting the following test URLs in your browser or using tools like Postman or `curl`.

#### 🔗 Service URLs

- **Eureka Server (Service Discovery)**  
  [http://localhost:8761/](http://localhost:8761/)

- **RabbitMQ Management Console**  
  [http://localhost:15672/#/queues](http://localhost:15672/#/queues)

- **Zipkin (Distributed Tracing)**  
  [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)

- **Currency Exchange Service**  
  [http://localhost:8089/currency-exchange/from/USD/to/INR](http://localhost:8089/currency-exchange/from/USD/to/INR)  
  **Sample Response:**
  ```json
  {
    "id": 1081,
    "currencyFrom": "USD",
    "currencyTo": "INR",
    "conversionMultiple": 65.0
  }

- **Currency Exchange Service — RabbitMQ Version**  
  [http://localhost:8089/currency-exchange-rabbit/from/USD/to/INR](http://localhost:8089/currency-exchange-rabbit/from/USD/to/INR)  
  **Sample Response:**
  ```json
  {
    "id": 1081,
    "currencyFrom": "USD",
    "currencyTo": "INR",
    "conversionMultiple": 65.0
  }
- **Currency Conversion Service**  
  [http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10](http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10)  
  **Sample Response:**
  ```json
  {
    "id": 1081,
    "from": "USD",
    "to": "INR",
    "amount": 10,
    "conversionMultiple": 65.00,
    "totalCalculate": 650.00
  }
- **Currency Conversion — RabbitMQ Version**  
  [http://localhost:8100/currency-conversion-from-rabbit/from/USD/to/INR/quantity/10](http://localhost:8100/currency-conversion-from-rabbit/from/USD/to/INR/quantity/10)  
  **Sample Response:**
  ```json
  {
    "id": 1,
    "from": "USD",
    "to": "INR",
    "amount": 10,
    "conversionMultiple": 1.1,
    "totalCalculate": 11.0
  }
