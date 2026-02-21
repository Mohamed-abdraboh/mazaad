# 🏷️ Mazaad — Online Auction Platform

> A production-ready Spring Boot REST API for a real-time auction and bidding platform with JWT authentication, WhatsApp OTP verification, and AWS S3 image storage.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Security](#security)
- [Contributing](#contributing)

---

## Overview

**Mazaad** (Arabic for "auction") is a backend REST API that powers an online auction marketplace. Users can list items for auction, place bids, and get authenticated via phone number + WhatsApp OTP. The system enforces role-based access control (Admin/User) and provides a complete self-hosted auction lifecycle — from item listing to bid resolution.

---

## ✨ Features

- 🔐 **JWT Authentication** — Stateless auth with access & refresh tokens, token blacklisting on logout
- 📱 **WhatsApp OTP Verification** — Phone-based registration with OTP sent via WhatsApp
- 🏷️ **Auction Management** — Create, update, delete, and list auctions with pagination and sorting
- 💰 **Real-time Bidding** — Place bids on active auctions with automatic validation
- 📦 **Item Listings** — Attach items with images, quantity, unit, location, and type to auctions
- 🖼️ **AWS S3 Image Upload** — Upload and serve auction item images from S3
- 🛡️ **RBAC** — Role-based access: Admins manage auctions, Users place bids
- 📄 **Swagger/OpenAPI Docs** — Interactive API documentation available at `/swagger-ui.html`
- 🔒 **Account Security** — Account activation, account lockout, and password management
- 📊 **Auditing** — All entities track `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate`

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Framework** | Spring Boot 3.2.4 |
| **Language** | Java 17 |
| **Database** | PostgreSQL |
| **ORM** | Spring Data JPA / Hibernate |
| **Security** | Spring Security + JWT (JJWT 0.12.5) |
| **Mapping** | MapStruct 1.4.2 |
| **Boilerplate** | Lombok |
| **Storage** | AWS S3 (aws-java-sdk-s3 1.12.707) |
| **Messaging** | WhatsApp API (OTP) |
| **API Docs** | SpringDoc OpenAPI 2.5.0 (Swagger UI) |
| **Build Tool** | Maven |

---

## 🏗️ Architecture

The project follows a clean **layered architecture** with a domain-driven package structure:

```
endpoint (Controller)  →  service (Business Logic)  →  repository (Data Access)  →  entity (Domain Model)
                                  ↕
                             dto / mapper
```

Each domain module is self-contained with its own:
- `entity` — JPA entities
- `dto` — Request/Response DTOs
- `mapper` — MapStruct mappers (entity ↔ DTO)
- `repository` — Spring Data JPA repositories
- `service` — Business logic
- `endpoint` — REST controllers
- `exception` — Domain-specific exceptions

---

## 📡 API Endpoints

### Authentication (`/api/v1/auth/`)

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/register` | Register a new user | Public |
| `POST` | `/authenticate` | Login and get JWT tokens | Public |
| `GET` | `/otp` | Request OTP via WhatsApp | Public |
| `POST` | `/activate` | Activate account with OTP code | Public |
| `POST` | `/refresh` | Refresh access token | Authenticated |
| `POST` | `/logout` | Logout (blacklist token) | Authenticated |

### Auctions (`/api/v1/auctions`)

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/` | List auctions (paginated, filterable) | Public |
| `POST` | `/` | Create a new auction with image(s) | Admin |
| `PUT` | `/{id}` | Update an existing auction | Admin |
| `DELETE` | `/{id}` | Delete an auction | Admin |

### Bids (`/api/v1/bids`)

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/` | Place a bid on an auction | User |

> 📖 Full interactive API documentation is available at `http://localhost:8080/swagger-ui.html` when the app is running.

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL** — running instance with a database created
- **AWS Account** — S3 bucket for image storage
- **WhatsApp API credentials** — for OTP delivery

### Configuration

Create `backend/src/main/resources/application.yaml` (excluded from git for security):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db_name
    username: your_db_user
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

jwt:
  secret-key: your-256-bit-secret-key
  access-token-expiration: 86400000   # 24 hours in ms
  refresh-token-expiration: 604800000 # 7 days in ms

aws:
  s3:
    bucket-name: your-s3-bucket-name
    region: your-aws-region
    access-key: your-access-key
    secret-key: your-secret-key

whatsapp:
  api-url: https://your-whatsapp-api-url
  token: your-whatsapp-api-token
```

> ⚠️ **Never commit your `application.yaml` with real credentials.** It is already excluded by `.gitignore`.

### Running the Application

```bash
# Clone the repository
git clone https://github.com/your-username/knooze.git
cd knooze/backend

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The app starts at: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📁 Project Structure

```
knooze/
└── backend/
    ├── src/
    │   └── main/
    │       ├── java/com/global/mazaad/
    │       │   ├── MazaadApplication.java       # Entry point
    │       │   ├── auction/                     # Auction module
    │       │   ├── bid/                         # Bidding module
    │       │   ├── user/                        # User management
    │       │   ├── security/                    # Auth & JWT
    │       │   │   ├── config/                  # Security config & constants
    │       │   │   ├── jwt/                     # JWT filter, validator, blacklist
    │       │   │   ├── service/                 # Auth & registration services
    │       │   │   └── exception/               # Auth-specific exceptions
    │       │   ├── itemsOffer/                  # Auction item listings
    │       │   ├── storage/                     # AWS S3 file storage
    │       │   ├── whatsapp/                    # WhatsApp OTP service
    │       │   └── common/                      # Shared utilities & exception handler
    │       └── resources/
    │           └── application.yaml             # App configuration (git-ignored)
    └── pom.xml
```

---

## 🔒 Security

- **JWT Tokens** — Stateless authentication with separate access and refresh tokens
- **Token Blacklisting** — Logged-out tokens are blacklisted to prevent reuse
- **BCrypt Passwords** — Passwords are hashed with BCrypt before storage
- **Account Activation** — New accounts require WhatsApp OTP verification before login
- **Role-Based Access Control**:
  - `ROLE_ADMIN` — Can create, update, and delete auctions
  - `ROLE_USER` — Can view auctions and place bids
- **CORS** — Configurable cross-origin resource sharing (currently open for development)
- **Stateless Sessions** — No server-side session storage; JWT is the source of truth

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'feat: add your feature description'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please follow the existing code style and ensure your changes are tested.

---

## 📄 License

This project is open-source. See [LICENSE](LICENSE) file for details.
