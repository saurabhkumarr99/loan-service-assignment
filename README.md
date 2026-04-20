# 🏦 Loan API Integration Service

> A clean, production-ready Spring Boot service for fetching loan EMI details, storing audit trails, and exposing structured REST endpoints.

---

## 📌 Overview

**Loan API Integration Service** is a Spring Boot REST API that connects to an external loan provider, processes EMI data, persists audit logs for traceability, and returns clean, standardized responses to the consumer.

---

## ✨ Features

- 🔌 Seamless integration with external Loan API via `RestTemplate`
- 📊 Extracts and filters due EMI details
- 🗃️ Audit logging — every API call is persisted in the database
- 📦 Generic `ApiResponseDTO` wrapper for consistent response structure
- 🔍 Global exception handling via `@RestControllerAdvice`
- ✅ Input validation with meaningful error messages
- 🪵 SLF4J structured logging throughout
- 🏗️ Clean layered architecture (Controller → Service → Integration)

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot |
| API Style | REST |
| Boilerplate Reduction | Lombok |
| Logging | SLF4J |
| Persistence | Spring Data JPA |
| HTTP Client | RestTemplate |

---

## 📂 Project Structure

```
com.assignment.loanservice
│
├── controller
│   ├── LoanController.java           # Handles loan detail requests
│   └── LoanAuditController.java      # Handles audit log requests
│
├── service
│   ├── LoanService.java
│   └── LoanAuditService.java
│
├── service/impl
│   ├── LoanServiceImpl.java          # Core business logic & EMI filtering
│   └── LoanAuditServiceImpl.java     # Audit retrieval logic
│
├── integration
│   └── LoanApiClient.java            # External API caller (RestTemplate)
│
├── dto
│   ├── ApiResponseDTO.java           # Generic wrapper for all responses
│   ├── LoanDTO.java
│   ├── LoanApiResponseDTO.java
│   ├── LoanAuditDTO.java
│   └── ErrorResponseDTO.java
│
├── model
│   └── LoanAudit.java                # DB entity for audit records
│
├── repository
│   └── LoanAuditRepository.java
│
└── exception
    ├── GlobalExceptionHandler.java         # Centralized error handling
    └── LoanDetailNotFoundException.java    # Custom exception
```

---

## 🔗 API Reference

### 1️⃣ Fetch Loan Details

Fetches loan EMI details for a given loan account number from the external API.

```http
GET /loanAccount/{loanAccountNumber}
```

**Example Request**
```
GET http://localhost:8082/loanAccount/1
```

**Success Response** `200 OK`
```json
{
  "status": "SUCCESS",
  "message": "Loan details fetched successfully",
  "timestamp": "2026-04-20T16:33:05.1407262",
  "data": {
    "loanAccountNumber": "1",
    "loanDetails": [
      {
        "dueDate": "April 2024",
        "emiAmount": 10000.0
      }
    ]
  }
}
```

---

### 2️⃣ Get Audit Logs by Loan Account

Returns all stored audit records for a given loan account number.

```http
GET /loan/audit/{loanAccountNumber}
```

**Example Request**
```
GET http://localhost:8082/loan/audit/1
```

**Success Response** `200 OK`
```json
{
  "status": "SUCCESS",
  "message": "Audit logs fetched for account: 1",
  "timestamp": "2026-04-20T16:35:06.904262",
  "data": [
    {
      "id": 1,
      "loanAccountNumber": "1",
      "responseJson": "{...}",
      "createdAt": "2026-04-20T16:17:01.928687"
    }
  ]
}
```

---

## ✅ Validation Rules

The `loanAccountNumber` path variable is validated on every request:

| Rule | Annotation | Message |
|------|-----------|---------|
| Must not be blank | `@NotBlank` | `"Loan AccountNumber is required"` |
| Must be numeric | `@Pattern(regexp = "\\d+")` | `"Loan Account Number must be numeric"` |

---

## 🧠 Application Flow

```
Request
   │
   ▼
Controller  (validates input)
   │
   ▼
Service Layer  (orchestrates logic)
   │
   ▼
Integration Layer  (RestTemplate → External Loan API)
   │
   ▼
EMI Processing  (filters due EMIs)
   │
   ▼
Audit Storage  (persists raw response to DB)
   │
   ▼
Structured Response  (wrapped in ApiResponseDTO)
```

---

## 💾 Audit Strategy

Every call to the loan details endpoint automatically creates an audit record containing:

| Field | Description |
|-------|------------|
| `loanAccountNumber` | The queried account |
| `responseJson` | Full raw API response as JSON string |
| `createdAt` | Timestamp of the request |

> 👉 This enables debugging, replay, and end-to-end traceability without re-calling the external API.

---

## ❗ Exception Handling

All exceptions are handled globally using `@RestControllerAdvice`.

**Error Response Format**
```json
{
  "status": "FAILED",
  "message": "Loan not found for account: 2",
  "timestamp": "2026-04-20T16:40:00"
}
```

| Exception | HTTP Status | Scenario |
|-----------|------------|---------|
| `LoanDetailNotFoundException` | `404 Not Found` | No loan data found for the account |
| Validation failure | `400 Bad Request` | Invalid or missing `loanAccountNumber` |
| Unexpected errors | `500 Internal Server Error` | Any unhandled exception |

---

## ⚙️ Configuration

Set the following properties in `application.properties` or `application.yml`:

```properties
server.port=8082

# External Loan API base URL
loan.api.base-url=https://external-loan-api.example.com

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/loandb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## 🚀 Running Locally

```bash
# Clone the repository
git clone https://github.com/your-username/loan-api-integration-service.git
cd loan-api-integration-service

# Build
./mvnw clean install

# Run
./mvnw spring-boot:run
```

The service starts at: `http://localhost:8082`

---

## 👤 Author

**Saurabh Kumar**

---

## 📄 License

This project is intended for assignment/demonstration purposes.