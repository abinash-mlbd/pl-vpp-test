# VPP API - Spring Boot

## Overview

This is a REST API built using Spring Boot that allows users to register batteries and query them based on postcode range and capacity. The API uses PostgreSQL for data persistence, Flyway for database migrations, and integrates OpenAPI for documentation.

## Features

- Add multiple batteries in a single request.
- Query batteries within a postcode range.
- Filter results by minimum and maximum watt capacity.
- Retrieve sorted battery names along with total and average capacity.
- Concurrent handling of battery registrations.
- Integrated logging for system events.

## Technologies Used

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Flyway
- Lombok
- Mockito & JUnit 5 for testing
- OpenAPI (Swagger)

## Setup Instructions

### Prerequisites

- Java 17+
- PostgreSQL database

### Configuration

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/battery-api.git
   cd battery-api
   ```
2. Configure PostgreSQL connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vpp
   spring.datasource.username=your_username
   spring.datasource.password=your_password

   ```
3. Run Flyway migrations:
   ```sh
   mvn flyway:migrate
   ```
4. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### 1. Add Batteries

**POST** `/batteries`

- **Request Body:**
  ```json
  [
    {
      "name": "Cannington",
      "postcode": "6107",
      "capacity": 13500
    }
  ]
  ```
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Cannington",
      "postcode": "6107",
      "capacity": 13500
    }
  ]
  ```

### 2. Get Batteries by Postcode Range

**GET** `/batteries?startPostcode=6000&endPostcode=6100&minCapacity=10000&maxCapacity=50000`

- **Response:**
  ```json
  {
    "names": ["Battery A", "Battery B"],
    "totalCapacity": 45000,
    "avgCapacity": 22500.0
  }
  ```

## Running Tests

To run unit tests:

```sh
mvn test
```

## Logging

- The application logs significant events such as battery additions and queries.
- Logs are stored in `logs/application.log` (if configured).

## OpenAPI Documentation

Once the application is running, visit:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

