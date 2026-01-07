# Datadog Service

A Spring Boot REST API service for user management.

## Prerequisites

- Java 21
- Maven

## Running the Application

```bash
./mvnw spring-boot:run
```

The service will start on `http://localhost:8080`.

## API Endpoints

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

### Step 4: Verify Traces

Monitor the Datadog trace agent logs:

```powershell
Get-Content "C:\ProgramData\Datadog\logs\trace-agent.log" -Wait -Tail 20
```

View your traces in the Datadog APM dashboard: https://app.datadoghq.com/apm/traces