# Datadog Service

A Spring Boot REST API microservices project with user and profile management.

## Prerequisites

- Java 21
- Maven

## Services

| Service | Port | Description |
|---------|------|-------------|
| user-service | 8080 | User management |
| profile-service | 8081 | Address and Education management |

## Running the Applications

**User Service:**
```bash
./mvnw spring-boot:run -pl user-service
```

**Profile Service:**
```bash
./mvnw spring-boot:run -pl profile-service
```

The user service runs on `http://localhost:8080` and profile service runs on `http://localhost:8081`.

## User Service API Endpoints (Port 8080)

### User Model

```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "active": true
}
```

### Create User

**POST** `/api/users`

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "active": true
  }'
```

### Get User by ID

**GET** `/api/users/{id}`

```bash
curl http://localhost:8080/api/users/1
```

### Get All Users

**GET** `/api/users`

```bash
curl http://localhost:8080/api/users
```

With pagination:

```bash
curl "http://localhost:8080/api/users?page=0&size=10"
```

### Get User Count

**GET** `/api/users/count`

```bash
curl http://localhost:8080/api/users/count
```

### Update User

**PUT** `/api/users/{id}`

```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john.updated@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "active": false
  }'
```

### Delete User

**DELETE** `/api/users/{id}`

```bash
curl -X DELETE http://localhost:8080/api/users/1
```

---

## Profile Service API Endpoints (Port 8081)

### Address Model

```json
{
  "id": 1,
  "address1": "123 Main Street",
  "address2": "Apt 4B",
  "area": "Downtown",
  "city": "New York",
  "pincode": "10001",
  "userId": 1
}
```

### Create Address

**POST** `/api/addresses`

```bash
curl -X POST http://localhost:8081/api/addresses \
  -H "Content-Type: application/json" \
  -d '{
    "address1": "123 Main Street",
    "address2": "Apt 4B",
    "area": "Downtown",
    "city": "New York",
    "pincode": "10001",
    "userId": 1
  }'
```

### Get Address by ID

**GET** `/api/addresses/{id}`

```bash
curl http://localhost:8081/api/addresses/1
```

### Get All Addresses

**GET** `/api/addresses`

```bash
curl http://localhost:8081/api/addresses
```

With pagination:

```bash
curl "http://localhost:8081/api/addresses?page=0&size=10"
```

### Get Addresses by User ID

**GET** `/api/addresses/user/{userId}`

```bash
curl http://localhost:8081/api/addresses/user/1
```

### Get Address Count

**GET** `/api/addresses/count`

```bash
curl http://localhost:8081/api/addresses/count
```

### Update Address

**PUT** `/api/addresses/{id}`

```bash
curl -X PUT http://localhost:8081/api/addresses/1 \
  -H "Content-Type: application/json" \
  -d '{
    "address1": "456 Oak Avenue",
    "address2": "Suite 100",
    "area": "Midtown",
    "city": "New York",
    "pincode": "10002",
    "userId": 1
  }'
```

### Delete Address

**DELETE** `/api/addresses/{id}`

```bash
curl -X DELETE http://localhost:8081/api/addresses/1
```

### Delete All Addresses by User ID

**DELETE** `/api/addresses/user/{userId}`

```bash
curl -X DELETE http://localhost:8081/api/addresses/user/1
```

---

### Education Model

```json
{
  "id": 1,
  "stream": "Computer Science",
  "startDate": "2020-08-01",
  "endDate": "2024-05-15",
  "per": 85.5,
  "userId": 1
}
```

### Create Education

**POST** `/api/educations`

```bash
curl -X POST http://localhost:8081/api/educations \
  -H "Content-Type: application/json" \
  -d '{
    "stream": "Computer Science",
    "startDate": "2020-08-01",
    "endDate": "2024-05-15",
    "per": 85.5,
    "userId": 1
  }'
```

### Get Education by ID

**GET** `/api/educations/{id}`

```bash
curl http://localhost:8081/api/educations/1
```

### Get All Educations

**GET** `/api/educations`

```bash
curl http://localhost:8081/api/educations
```

With pagination:

```bash
curl "http://localhost:8081/api/educations?page=0&size=10"
```

### Get Educations by User ID

**GET** `/api/educations/user/{userId}`

```bash
curl http://localhost:8081/api/educations/user/1
```

### Get Education Count

**GET** `/api/educations/count`

```bash
curl http://localhost:8081/api/educations/count
```

### Update Education

**PUT** `/api/educations/{id}`

```bash
curl -X PUT http://localhost:8081/api/educations/1 \
  -H "Content-Type: application/json" \
  -d '{
    "stream": "Computer Science",
    "startDate": "2020-08-01",
    "endDate": "2024-05-15",
    "per": 90.0,
    "userId": 1
  }'
```

### Delete Education

**DELETE** `/api/educations/{id}`

```bash
curl -X DELETE http://localhost:8081/api/educations/1
```

### Delete All Educations by User ID

**DELETE** `/api/educations/user/{userId}`

```bash
curl -X DELETE http://localhost:8081/api/educations/user/1
```

---

## Response Codes

| Code | Description |
|------|-------------|
| 200  | Success |
| 201  | Created |
| 204  | No Content (successful deletion) |
| 400  | Bad Request |
| 404  | Not Found |

## Datadog APM Setup (Local Development)

### Step 1: Install Datadog Agent

Download and install the Datadog Agent for Windows:

https://docs.datadoghq.com/agent/supported_platforms/windows/

During installation, you'll need:
- **API Key**: Create one at https://app.datadoghq.com/organization-settings/api-keys
- **Username and Password**: Your Datadog account credentials

For more information about API keys: https://docs.datadoghq.com/account_management/api-app-keys/#api-keys

### Step 2: Download the Java APM Agent

Open PowerShell as **Administrator** and run:

```powershell
# Create a directory for the agent
New-Item -Path "C:\datadog" -ItemType Directory -Force

# Download the Datadog Java agent
Invoke-WebRequest -Uri "https://dtdg.co/latest-java-tracer" -OutFile "C:\datadog\dd-java-agent.jar"
```

### Step 3: Configure JVM Options

Add the following JVM arguments when running the application:

```
-javaagent:C:\datadog\dd-java-agent.jar
-Ddd.service=datadog-service
-Ddd.env=local
-Ddd.version=1.0.0
-Ddd.logs.injection=true
-Ddd.trace.sample.rate=1
-Ddd.agent.host=localhost
-Ddd.agent.port=8126
```

**IntelliJ IDEA:**
1. Go to **Run > Edit Configurations**
2. Add the above to **VM options** (as a single line, space-separated)

**Command Line:**
```bash
java -javaagent:C:\datadog\dd-java-agent.jar -Ddd.service=datadog-service -Ddd.env=local -Ddd.version=1.0.0 -Ddd.logs.injection=true -Ddd.trace.sample.rate=1 -Ddd.agent.host=localhost -Ddd.agent.port=8126 -jar target/datadog-service-0.0.1-SNAPSHOT.jar
```

### Step 4: Configure Datadog Log Collection

Create a configuration file for log collection:

```
C:\ProgramData\Datadog\conf.d\datadog-service.d\conf.yaml
```

Add the following content:

```yaml
logs:
  - type: file
    path: "D:\\Projects\\Test\\Code\\datadog-service\\logs\\user-service.log"
    service: "user-service"
    source: "java"
    env: "uat"
    tags:
      - "team:sysout-team"
      - "version:1.0"

  - type: file
    path: "D:\\Projects\\Test\\Code\\datadog-service\\logs\\profile-service.log"
    service: "profile-service"
    source: "java"
    env: "uat"
    tags:
      - "team:sysout-team"
      - "version:1.0"
```

Restart the Datadog Agent after creating the configuration:

```powershell
& "C:\Program Files\Datadog\Datadog Agent\bin\agent.exe" restart-service
```

### Step 5: Verify Traces

Monitor the Datadog trace agent logs:

```powershell
Get-Content "C:\ProgramData\Datadog\logs\trace-agent.log" -Wait -Tail 20
```

View your traces in the Datadog APM dashboard: https://app.datadoghq.com/apm/traces

View your logs in the Datadog Logs dashboard: https://app.datadoghq.com/logs

## Correlation ID Filter

The application includes a `CorrelationIdFilter` that automatically generates or extracts a correlation ID for each request. This is essential for tracing requests across distributed microservices and correlating logs in observability platforms like Datadog.

### What is a Correlation ID?

A **Correlation ID** (also known as Request ID or Trace ID) is a unique identifier that follows a request as it travels through multiple microservices. It enables:

- **Distributed Tracing**: Track a single user request across user-service, profile-service, and any other downstream services
- **Log Correlation**: Search all logs related to a specific request using a single ID
- **Debugging**: Quickly identify the flow of a request when investigating issues
- **Datadog Integration**: Correlates with Datadog's APM tracing when using `x-datadog-trace-id` header

### How CorrelationIdFilter Works

The `CorrelationIdFilter` is a Spring `HttpFilter` with `@Order(1)` priority (runs first). Located at:
`common/src/main/java/com/datadog/common/config/CorrelationIdFilter.java`

**Processing Flow:**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           INCOMING REQUEST                                  │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  1. Check for 'x-datadog-trace-id' header                                   │
│     ├── Header present? → Use existing trace ID as correlation ID           │
│     └── Header absent?  → Generate new UUID                                 │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  2. Check for 'x-user-platform' header                                      │
│     ├── Header present? → Use provided platform (uppercase)                 │
│     └── Header absent?  → Default to "POSTMAN"                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  3. Store in MDC (Mapped Diagnostic Context)                                │
│     ├── MDC.put("correlationId", correlationId)                             │
│     └── MDC.put("platform", platform)                                       │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  4. Add 'X-Correlation-ID' to response header                               │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  5. Continue filter chain → Controller → Service → Repository               │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  6. Finally block: Clean up MDC to prevent memory leaks                     │
│     ├── MDC.remove("correlationId")                                         │
│     └── MDC.remove("platform")                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Request Headers

| Header | Required | Description | Default |
|--------|----------|-------------|---------|
| `x-datadog-trace-id` | No | Datadog trace ID to use as correlation ID | Auto-generated UUID |
| `x-user-platform` | No | Platform identifier (e.g., WEB, MOBILE, API) | POSTMAN |

### MDC Keys

The filter stores the following keys in SLF4J's MDC:

| MDC Key | Description |
|---------|-------------|
| `correlationId` | Unique identifier for request tracing |
| `platform` | Source platform of the request |

### Usage Examples

**Without headers (auto-generated correlation ID):**
```bash
curl http://localhost:8080/api/users
```

**With Datadog trace ID:**
```bash
curl -H "x-datadog-trace-id: abc123-trace-id" http://localhost:8080/api/users
```

**With platform identifier:**
```bash
curl -H "x-user-platform: MOBILE" http://localhost:8080/api/users
```

**With both headers:**
```bash
curl -H "x-datadog-trace-id: abc123-trace-id" \
     -H "x-user-platform: WEB" \
     http://localhost:8080/api/users
```

### Log Output Format

The correlation ID appears in every log entry via the logback pattern:

```
%d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %X{correlationId} %X{customerId} %X{APIPlatform} %logger{36} - %msg%n
```

**Example log output:**
```
08-01-2026 10:30:45.123 [http-nio-8080-exec-1] INFO  a7d8f2ce-4b5a-4c3d-9e1f-2a3b4c5d6e7f   POSTMAN c.d.user.controller.UserController - REST request to create user: johndoe
08-01-2026 10:30:45.125 [http-nio-8080-exec-1] DEBUG a7d8f2ce-4b5a-4c3d-9e1f-2a3b4c5d6e7f   POSTMAN c.d.user.service.UserService - Saving user to database
```

### Response Header

The correlation ID is returned in the response header for client tracking:

```
X-Correlation-ID: a7d8f2ce-4b5a-4c3d-9e1f-2a3b4c5d6e7f
```

---

## PropagateHeadersInterceptor

The `PropagateHeadersInterceptor` is a Feign `RequestInterceptor` that automatically forwards headers between microservices during inter-service communication.

### What is Header Propagation?

When user-service calls profile-service via Feign client, the original request headers (including correlation ID) need to be forwarded to maintain request traceability across the distributed system.

### How PropagateHeadersInterceptor Works

Located at: `common/src/main/java/com/datadog/common/config/PropagateHeadersInterceptor.java`

**Processing Flow:**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    USER-SERVICE (Feign Client Call)                         │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  PropagateHeadersInterceptor.apply(RequestTemplate)                         │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  1. Get RequestAttributes from RequestContextHolder                         │
│     └── Access the original HttpServletRequest                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  2. Iterate through all incoming request headers                            │
│     └── Filter headers starting with "x-" prefix                            │
│         (e.g., x-datadog-trace-id, x-user-platform, x-custom-header)        │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  3. Copy matching headers to outgoing Feign request template                │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  4. Override X-Correlation-ID with value from MDC                           │
│     └── Ensures correlation ID is always from current context               │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      PROFILE-SERVICE (Receives Call)                        │
│                      Headers preserved for tracing!                         │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Key Features

1. **Automatic Header Forwarding**: All headers starting with `x-` are automatically propagated
2. **Correlation ID Override**: The `X-Correlation-ID` header is explicitly set from MDC to ensure consistency
3. **Highest Precedence**: Runs with `@Order(Ordered.HIGHEST_PRECEDENCE)` to execute before other interceptors
4. **Trace Logging**: Headers being propagated are logged at TRACE level for debugging

### Headers Propagated

| Header Pattern | Example Headers |
|----------------|-----------------|
| `x-*` | `x-datadog-trace-id`, `x-user-platform`, `x-request-id`, `x-forwarded-for` |
| `X-Correlation-ID` | Always propagated from MDC (overrides any existing value) |

### End-to-End Request Flow

```
┌──────────┐     ┌──────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Client  │────▶│ User-Service │────▶│ Profile-Service │────▶│    Response     │
└──────────┘     └──────────────┘     └─────────────────┘     └─────────────────┘
     │                  │                      │                       │
     │  Headers:        │  CorrelationIdFilter │  CorrelationIdFilter  │
     │  x-datadog-      │  extracts/generates  │  extracts correlation │
     │  trace-id        │  correlation ID      │  ID from header       │
     │  x-user-platform │                      │                       │
     │                  │  PropagateHeaders    │                       │
     │                  │  Interceptor copies  │                       │
     │                  │  x-* headers to      │                       │
     │                  │  Feign request       │                       │
     │                  │                      │                       │
     ▼                  ▼                      ▼                       ▼
   Logs:              Logs:                  Logs:                  All logs
   N/A                correlationId=abc123   correlationId=abc123   share same
                      platform=WEB           platform=WEB           correlation ID!
```

### Configuration

The interceptor is registered as a Spring Bean in `InterceptorConfig.java`:

```java
@Bean
public PropagateHeadersInterceptor propagateHeadersInterceptor() {
    return new PropagateHeadersInterceptor();
}
```

### Why This Matters

Without header propagation:
- Each microservice would generate its own correlation ID
- Logs would be disconnected and hard to trace
- Datadog APM traces would be fragmented

With header propagation:
- Single correlation ID flows through all services
- Easy to search all logs for a specific request
- Complete distributed trace visibility in Datadog