# Quarkus Product Management System

This project is built using **[Quarkus](https://quarkus.io/)** — a modern, Kubernetes-native Java framework tailored for GraalVM and HotSpot.

## 📌 Project Overview

This is a simple **Product Management System** exposing RESTful APIs. It demonstrates how to build a reactive microservice using Quarkus.

## 🛠 Prerequisites

- Java 8 or later
- Maven 3.8+
- Optional: Docker (for containerization)

## 🚀 Running the Application in dev mode

```bash
./mvnw compile quarkus:dev
```

## Build the application

Use the following command to build the application locally:

```bash
mvn clean install
```

## Building the application with Integration test.

```
 ./mvnw clean verify
 ```

## Note: Quarkus includes a built-in Dev UI accessible at:
```
http://localhost:8080/q/dev/
```

## Provided code structure.
```
src
└── main
    ├── java
    │   └── com.api.banking
    │       ├── resource      # REST API endpoints
    │       ├── service       # Business logic
    │       ├── entity        # Data models (entities)
    │       └── repository    # Persistence layer
    └── resources
        └── application.properties  # Configuration file
```

## Useful Links
```
https://github.com/quarkusio/quarkus
```

Quarkus GitHub Repository
```
https://github.com/quarkusio/quarkus
```

Quarkus RESTEasy Reactive Guide
```
https://quarkus.io/guides/rest
```
