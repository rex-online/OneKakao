# API Standards

## Response Format

All API responses follow a unified format to ensure consistency across the application.

### Success Response

```json
{
  "success": true,
  "data": {
    // Response data here
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

### Error Response

```json
{
  "success": false,
  "error": {
    "code": "COMMON-001",
    "message": "Error description",
    "details": {
      // Additional error details (optional)
    }
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

## Error Codes

### Common Errors (COMMON-xxx)

| Code | HTTP Status | Description |
|------|-------------|-------------|
| COMMON-001 | 400 | Invalid input value |
| COMMON-002 | 500 | Internal server error |
| COMMON-003 | 404 | Entity not found |
| COMMON-004 | 400 | Invalid type value |
| COMMON-005 | 405 | Method not allowed |
| COMMON-006 | 403 | Access denied |

### Adding Domain-Specific Error Codes

When adding new business domains, create error codes with domain prefixes:

```java
// In ErrorCode.java
USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "User not found"),
USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER-002", "User already exists"),

ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER-001", "Order not found"),
ORDER_INVALID_STATUS(HttpStatus.BAD_REQUEST, "ORDER-002", "Invalid order status"),
```

## Naming Conventions

### Endpoints

- Base path: `/api`
- Resource naming: Use plural nouns (`/api/users`, `/api/orders`)
- Resource ID: Use path variables (`/api/users/{userId}`)
- Actions: Use HTTP methods, not verbs in URL
  - Good: `DELETE /api/users/{userId}`
  - Bad: `/api/users/{userId}/delete`

### Query Parameters

Use camelCase for query parameters:
```
GET /api/users?pageSize=10&pageNumber=1&sortBy=createdAt
```

### Request/Response Bodies

Use camelCase for JSON fields:
```json
{
  "userId": 123,
  "userName": "John Doe",
  "createdAt": "2026-01-13T12:00:00"
}
```

## HTTP Methods

| Method | Usage | Idempotent |
|--------|-------|-----------|
| GET | Retrieve resources | Yes |
| POST | Create new resources | No |
| PUT | Update entire resource | Yes |
| PATCH | Partial update | No |
| DELETE | Remove resource | Yes |

## HTTP Status Codes

### Success (2xx)

- `200 OK` - Successful GET, PUT, PATCH, or DELETE
- `201 Created` - Successful POST that creates a resource
- `204 No Content` - Successful request with no response body

### Client Errors (4xx)

- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Authenticated but not authorized
- `404 Not Found` - Resource not found
- `405 Method Not Allowed` - HTTP method not supported
- `409 Conflict` - Resource conflict (e.g., duplicate)

### Server Errors (5xx)

- `500 Internal Server Error` - Unexpected server error
- `503 Service Unavailable` - Server temporarily unavailable

## Headers

### Request Headers

```
Content-Type: application/json
Accept: application/json
Authorization: Bearer <token>  (if authentication required)
```

### Response Headers

```
Content-Type: application/json
```

## Pagination

For list endpoints, use the following query parameters:

```
GET /api/users?page=0&size=20&sort=createdAt,desc
```

Response format:
```json
{
  "success": true,
  "data": {
    "content": [...],
    "totalElements": 100,
    "totalPages": 5,
    "size": 20,
    "number": 0
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

## Date/Time Format

Use ISO 8601 format for all date and time values:

- Date: `2026-01-13`
- DateTime: `2026-01-13T12:00:00`
- DateTime with timezone: `2026-01-13T12:00:00+09:00`

## Validation

Use Bean Validation annotations in DTOs:

```java
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @Email(message = "Invalid email format")
    private String email;
}
```

## Example API Usage

### Create User

**Request:**
```http
POST /api/users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Success Response (201 Created):**
```json
{
  "success": true,
  "data": {
    "userId": 123,
    "username": "john_doe",
    "email": "john@example.com",
    "createdAt": "2026-01-13T12:00:00"
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

**Error Response (400 Bad Request):**
```json
{
  "success": false,
  "error": {
    "code": "COMMON-001",
    "message": "Validation failed",
    "details": [
      "username: Username is required",
      "email: Invalid email format"
    ]
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

### Get User

**Request:**
```http
GET /api/users/123
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "data": {
    "userId": 123,
    "username": "john_doe",
    "email": "john@example.com",
    "createdAt": "2026-01-13T12:00:00"
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "error": {
    "code": "USER-001",
    "message": "User not found"
  },
  "timestamp": "2026-01-13T12:00:00"
}
```

## Best Practices

1. **Always use ApiResponse wrapper** for consistent response format
2. **Define domain-specific error codes** for better error tracking
3. **Validate input data** using Bean Validation annotations
4. **Log all exceptions** in GlobalExceptionHandler
5. **Use appropriate HTTP status codes** for different scenarios
6. **Document all endpoints** with Swagger annotations
7. **Version your API** if breaking changes are introduced (`/api/v1`, `/api/v2`)
8. **Use HTTPS** in production for secure communication
9. **Implement rate limiting** for public APIs
10. **Follow RESTful principles** for resource design
