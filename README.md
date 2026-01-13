# OneKakao - AI Hacker Hackathon Backend

Spring Boot REST API backend for the OneKakao project.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.1**
- **Gradle 9.2.1**
- **H2 Database** (Development)
- **MySQL** (Production)
- **Docker & Docker Compose**
- **Swagger/OpenAPI** (API Documentation)

## Quick Start

### Prerequisites

- Java 21+
- Docker & Docker Compose (optional)
- Git

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd onekakao
   ```

2. **Run with Gradle**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the application**
   - Root: http://localhost:8080 (redirects to Swagger UI)
   - API: http://localhost:8080/api
   - Health Check: http://localhost:8080/api/health
   - Swagger UI: http://localhost:8080/api/swagger-ui/index.html
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (leave empty)

### Docker Development

```bash
# Development (H2)
docker-compose -f docker-compose.dev.yml up --build

# Production-like (MySQL)
docker-compose up --build
```

## Project Structure

```
src/
├── main/
│   ├── java/backend/onekakao/
│   │   ├── OnekakaoApplication.java
│   │   └── common/
│   │       ├── config/         # Configuration classes
│   │       ├── controller/     # Common controllers (health)
│   │       ├── dto/           # Common DTOs (ApiResponse)
│   │       ├── exception/     # Exception handling
│   │       └── interceptor/   # Request interceptors
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── logback-spring.xml
└── test/
```

## API Documentation

API documentation is available via Swagger UI at `/api/swagger-ui.html` when running in development mode.

### API Response Format

All API responses follow a unified format:

**Success Response:**
```json
{
  "success": true,
  "data": { ... },
  "timestamp": "2026-01-13T12:00:00"
}
```

**Error Response:**
```json
{
  "success": false,
  "error": {
    "code": "COMMON-001",
    "message": "Error description",
    "details": { ... }
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

## Environment Variables

Create a `.env` file from `.env.example`:

```bash
cp .env.example .env
```

Key environment variables:
- `SPRING_PROFILES_ACTIVE`: Active profile (dev/prod)
- `DATABASE_URL`: Database connection URL (production)
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password
- `CORS_ALLOWED_ORIGINS`: Allowed CORS origins

## Testing

```bash
# Run all tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport
```

## Deployment

### Docker

```bash
docker build -t onekakao:latest .
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod onekakao:latest
```

### CI/CD

CI/CD pipelines are configured for GitHub Actions:
- **CI**: Runs on push to main/develop branches
- **CD**: Deploys on push to main branch
- **Docker**: Builds and pushes image on version tags

## Available Endpoints

- `GET /` - Root (redirects to Swagger UI)
- `GET /api/health` - Health check endpoint
- `GET /api/swagger-ui/index.html` - Swagger UI (dev only)
- `GET /api/docs` - OpenAPI specification
- `GET /h2-console` - H2 database console (dev only)

## Features

- ✅ Multi-environment configuration (dev/prod)
- ✅ Unified API response format
- ✅ Global exception handling
- ✅ Swagger/OpenAPI documentation
- ✅ CORS configuration for frontend
- ✅ Structured logging with Logback
- ✅ Request/Response logging interceptor
- ✅ Docker containerization
- ✅ CI/CD with GitHub Actions
- ✅ Health check endpoint

## Contributing

1. Create a feature branch
2. Make your changes
3. Write/update tests
4. Submit a pull request

## License

Apache 2.0
