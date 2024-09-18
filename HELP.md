# Transaction Statistics API

## Overview
This is a RESTful API for tracking real-time statistics of transactions that happened in the last 30 seconds. The API supports three endpoints:
1. **POST /transactions** - Create a new transaction.
2. **GET /statistics** - Retrieve statistics for the last 30 seconds of transactions.
3. **DELETE /transactions** - Delete all transactions.

The solution is built using Java (Spring Boot), Docker, and follows best practices to ensure thread safety, constant time complexity, and memory efficiency. Unit tests are implemented to ensure the quality of the code.

## Features
- **Real-time statistics**: Calculates statistics for the transactions that occurred in the last 30 seconds.
- **Thread-safe**: Handles concurrent requests safely.
- **Efficient in time and memory (O(1))**: The GET /statistics and POST /transactions endpoints run in constant time and memory.
- **BigDecimal precision**: Transaction amounts are handled with precision, rounding to two decimal places using `HALF_ROUND_UP`.

## Requirements
To run this project locally, you will need:
- **Java 17** or later
- **Maven** (for building and testing)
- **Docker** (optional for containerized deployment)

## Endpoints

### 1. POST /transactions
This endpoint is used to create a new transaction. The transaction must contain:
- `amount`: A string representing the transaction amount, which is parsed as a `BigDecimal`.
- `timestamp`: The transaction timestamp in ISO 8601 format (UTC timezone).

**Request Body Example**:
```json
{
  "amount": "12.3343",
  "timestamp": "2023-09-16T09:59:51.312Z"
}
```

Response Codes:

- 201: Transaction successfully created.
- 204: Transaction is older than 30 seconds.
- 400: Invalid JSON input.
- 422: Unparsable fields or transaction date is in the future.


## How to Run the Application
1. Clone the Repository
   Start by cloning the project from GitHub

2. Build the Project
   Make sure you have Java 17+ and Maven installed. To build the project and ensure all dependencies are correctly resolved, run:

```bash
mvn clean package
```

3. Run the Application Locally
   Once the project is built, you can run the application locally using the following Maven command:

```bash
mvn spring-boot:run
```

By default, the application will be available at http://localhost:8080.

4. Running the Application with Docker
   You can also run the application in a Docker container if you have Docker installed. Follow these steps:

 - Step 4.1: Build the Docker Image
```bash
docker build -t transactionstats-api .
```

This command will create a Docker image named transaction-statistics-api.

Step 4.2: Run the Docker Container
To start the container, map it to port 8080:

```bash
docker run -p 8080:8080 transaction-statistics-api
```
The API will be accessible at http://localhost:8080.

5. Run Unit Tests
   To verify that the code passes all unit tests, run:

```bash
mvn clean test
```

6. Run Integration Tests
   To execute the integration tests, use:

```bash
mvn clean integration-test
```
