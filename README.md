# Exactabank

**Table of Contents**
- [Introduction](#introduction)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Technologies Used](#technologies-used)
- [Testing](#testing)
- [Documentation](#documentation)

## Introduction

Exactabank is a platform designed to manage personal transactions. It allows users to record and view their transactions, as well as track the total amount of inflows and outflows. Transactions can involve various activities such as sending money via PIX, mobile phone recharges, deposits, and withdrawals.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Docker and docker-compose

### Installation

1. Clone the repository.
   ```bash
   git clone https://github.com/your-username/exactabank.git

2. Navigate to the project directory.
    ```bash
    cd exactabank
   
3. Build and run the application using Docker. This will run the application and the database together.
    ```bash
   docker-compose up --build

## Technologies Used

- Kotlin
- Spring Boot 3
- MySQL
- Flyway
- Spring Data JPA
- JUnit
- Testcontainers
- Docker
- Swagger, OpenApi

## Testing

Have docker engine running, and run
```bash
    mvn test
```

## Documentation

Explore the API documentation to understand available endpoints and request/response formats:
http://localhost:8080/swagger-ui/index.html
