# Aviation-Stack-MCP

A Spring Boot MCP (Model Context Protocol) server that exposes [AviationStack](https://aviationstack.com/) flight data as tools for AI applications.

## Overview

This server acts as a bridge between AI assistants (via MCP) and the AviationStack REST API, enabling natural language queries for real-time aviation data such as flight status, routes, and airline information.

## Tech Stack

- **Java 25** / Spring Boot 3.5
- **Spring AI MCP Server** — exposes aviation tools over the Model Context Protocol
- **AviationStack API** — external data source
- **Docker** — containerized deployment

## Configuration

Set the following environment variables before running:

| Variable | Description |
|---|---|
| `AVIATIONSTACK_API_KEY` | Your AviationStack API key |
| `X_API_HEADER_KEY` | Auth key for MCP client requests |

## Running

```bash
# With Gradle
./gradlew bootRun

# With Docker
docker build -t aviation-stack-mcp .
docker run -p 8080:8080 \
  -e AVIATIONSTACK_API_KEY=your_key \
  -e X_API_HEADER_KEY=your_secret \
  aviation-stack-mcp
```

The server starts on port `8080`.
