# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring AI-powered real estate application that uses large language models (LLMs) with function calling to enable intelligent real estate searches. The application exposes REST APIs for:
- Querying real estate data directly (houses by type, zip code, property ID)
- Chat-based queries using an AI agent that intelligently selects appropriate tools based on user intent

### Technology Stack

- **Framework**: Spring Boot 3.3.4
- **Language**: Java 17
- **AI Integration**: Spring AI 1.1.6 with Ollama backend
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Data Source**: CSV file (`ny_real_estate.csv`)

## Building and Running

### Build the Project

```bash
# Using Maven wrapper (PowerShell)
.\mvnw clean package

# Or using Maven if installed
mvn clean package
```

### Run the Application

```bash
# Using Maven (PowerShell)
.\mvnw spring-boot:run

# Or run the packaged JAR
java -jar target/real-estate-app-0.0.1-SNAPSHOT.jar
```

The application starts on `http://localhost:8080`.

### Prerequisites for Full Functionality

The AI agent features require Ollama to be running locally on `http://localhost:11434` with the `llama3.2` model available. Without Ollama running, the chat endpoints will fail, but the standard REST endpoints for house queries will still work.

### Run Tests

Currently, there are no test files in the project (`src/test/java/` is empty).

```bash
# Run all tests (when added)
.\mvnw test

# Run a single test class
.\mvnw test -Dtest=ClassName

# Run a specific test method
.\mvnw test -Dtest=ClassName#methodName
```

## Architecture

### High-Level Design

The application follows a layered architecture:

```
Controller Layer
    ↓
Service Layer
    ↓
Repository Layer (JPA)
    ↓
H2 Database
```

**AI Integration Point**: The Chat service uses Spring AI's ChatClient with tool calling to enable the LLM to invoke search functions dynamically.

### Module Structure

- **Controller Layer** (`controller/`)
  - `AppController`: REST endpoints for direct house queries (GET /v1/houses, /v1/houses/{id}, /v1/houses/find/{type}, /v1/houses/find/{type}/{zip})
  - `ChatController`: REST endpoints for AI-powered chat interactions (POST /v1/chat/simple, /v1/chat/houses, GET /v1/chat/list-models)

- **Service Layer** (`service/`)
  - `AppService`: Business logic for house queries (getAllHouses, findHousesByType, findHousesByTypeAndZipCode, getHouseByPropertyId)
  - `ChatService`: AI interaction logic with two main paths:
    - `llmChat()`: Simple pass-through to LLM for basic chat
    - `agentFindHouses()`: Uses tool calling to enable LLM to invoke search functions intelligently

- **Tools** (`tools/SearchTools`)
  - Spring AI `@Tool`-annotated methods that the LLM can invoke:
    - `findHousesByType(type)`: Returns list of property IDs for houses of a given type
    - `findHousesByTypeAndZipCode(type, zipCode)`: Filters by both type and zip code
    - `findHouseByPropertyId(propertyId)`: Returns full house details for a specific property
    - `findAllHouses()`: Returns all property IDs without filters
  - Three tools use `returnDirect = true` (`findHousesByType`, `findHousesByTypeAndZipCode`, `findAllHouses`) — their results bypass further LLM formatting and return directly to the client. `findHouseByPropertyId` does NOT use `returnDirect`, so the LLM formats the full `House` object into a natural-language response before returning

- **Model Layer** (`model/`)
  - `House`: JPA entity mapped to HOUSES table with 11 columns (propertyId, type, description, prices, dimensions, zip code, city)
  - `LLMPrompt`: Request DTO for chat endpoints with user_prompt and optional system_prompt

- **Repository Layer** (`repository/AppRepository`)
  - JPA repository with custom query methods for type and type+zip filters
  - Uses LIKE clauses for flexible type matching

### Data Initialization

The H2 database is initialized via `schema.sql` which reads from `ny_real_estate.csv` at startup using H2's `CSVREAD()` function. The CSV must be present in the working directory where the app is launched (project root when using `.\mvnw spring-boot:run`). `schema.sql` also drops a `PLAYERS` table before creating `HOUSES` — a leftover from a prior version of the codebase.

### Configuration

- **Application Config** (`application.yml`):
  - Server runs on port 8080
  - Ollama base URL: `http://localhost:11434`
  - Default model: `llama3.2` with temperature 0.2
  - H2 console enabled at `http://localhost:8080/h2-console`
  - Logging: SLF4J with Log4j2 backend

- **Spring AI Integration**: ChatClient is auto-configured via Spring Boot's auto-configuration and built as a bean in `HouseApplication`.

## Key Development Patterns

### Tool-Based AI Agent Pattern

The application implements Spring AI's tool-based agent pattern:

1. User sends a prompt via `POST /v1/chat/houses` with user_prompt and optional system_prompt
2. `ChatService.agentFindHouses()` uses `ChatClient` with `.tools(searchTools)` to expose the four search functions to the LLM
3. The LLM reads the tool descriptions to decide which function to call based on user intent
4. The selected tool executes and returns results to the LLM
5. The LLM formats a response to the user

If no system_prompt is provided, a default system prompt guides the LLM to use the appropriate tool based on what the user provides (type only → findHousesByType, type + zip → findHousesByTypeAndZipCode, property ID → findHouseByPropertyId, no filter → findAllHouses).

### Logging Strategy

- Uses SLF4J with Log4j2 backend
- Configuration in `log4j2.xml` with console and file appenders
- Each service logs key operations (e.g., "Tool called => findHousesByType", "Response in controller")
- Logs written to `logs/application.log`

## Testing and Integration

### API Testing Resources

Example HTTP requests are provided in `collection/`:
- `chat_requests.txt`: cURL examples for Ollama endpoints
- `GetAllPlayers.http` and `GetPlayerById.http`: REST client request files (note: endpoints mention "players" but actually test "houses")

### Database Console

H2 console is available at `http://localhost:8080/h2-console` for debugging. Use:
- Driver: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:housedb`
- User: `sa`
- Password: (leave blank)

## Important Notes

- The application name is "baseball" (in `application.yml`) even though it's a real estate app — this is a configuration artifact
- Test files have not been added yet — consider adding integration tests for the chat service and tool invocation logic
- The project metadata in `pom.xml` mentions "player-service-java" as the description; consider aligning with actual real estate naming
- CSV data loading happens at startup via `schema.sql`; ensure `ny_real_estate.csv` is in the project root
- Tool descriptions in `SearchTools` are critical for proper LLM decision-making; update them if tool behavior changes
