# Algolia Java Articles

## Overview
This Java-based application is developed using Spring Boot (version 3.2.4) and Java 17. It provides an API to manage articles with features like JWT-based authentication and scheduled tasks for data management. The application utilizes PostgreSQL as its database, and it's containerized using Docker for easy setup and deployment.

## Prerequisites
Before you can run the application, you need to have the following installed on your system:
- Docker
- Docker Compose

## Setup and Running the Application

### Clone the Repository
First, clone the repository to your local machine:
```bash
git clone git@github.com:EZannStudios/Algolia-Java-Project.git
cd Algolia-Java-Project
```
### Application and Database secrets
username, password and secret key are stored in the application.yml file

### Running with Docker Compose
To start the application along with the PostgreSQL database using Docker Compose, run the following command from the root of the project:
```bash
mvn clean package -DskipTests
```
```bash
docker-compose up --build
```

## Accessing the Application
Once the application is running, you can access the API through:

- **Swagger UI:** [http://localhost:8088/swagger-ui/](http://localhost:8088/swagger-ui/) - This page provides a visual interface for interacting with the API.

## Scheduled Tasks
The application includes a scheduled task that refreshes the data periodically. This can be configured via the application's configuration files.

## JWT Authentication
The application uses JWT for secure authentication. You can obtain a token by accessing the `/auth/login` endpoint. Use this token as a Bearer token in the Authorization header for subsequent requests to protected endpoints.

## Populating the Database
Upon startup, the application uses a data initializer to populate the database with predefined data. This is ideal for demo purposes and initial testing.

## Testing the application
An H2 in memory database was added to the application to run the tests.
I also added a collection for the insomnia tool in \insomnia\Insomnia_2024-04-24.json with the login, get articles create article and delete article, in case Swagger is not enough.

## GitHub Actions
A ci.yml file was added here \sebastian-sanchez-java-be\.github\workflows\ci.yml to rnu checkstyle linter and the application tests when pushing or opening a pull request.

## Test coverage
![image](https://github.com/EZannStudios/Algolia-Java-Project/assets/69906433/94e7a2c9-06f7-4528-b11f-14076836113a)

## Assumptions and Choices
- **Java 17 and Spring Boot 3.2.4** are chosen for their robustness and community support.
- **PostgreSQL** is used for data persistence, chosen for its powerful features and compatibility with Docker.
- **Docker** is used to containerize the application and database, ensuring consistency across different environments.
- **JWT** provides a secure way to handle authentication.
- **Swagger** is integrated for API documentation and testing.
