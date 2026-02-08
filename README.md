# API Dependency Analyzer

A backend system that tracks **API dependencies** and performs **impact analysis** using a combination of **MySQL** and **Neo4j**.  
The project helps understand how failures in one API affect other dependent APIs in a microservices architecture.

---

## Features

- Service and API management
- API dependency graph using Neo4j
- Impact analysis for upstream API failures
- Natural language explanation of impact
- REST APIs built using **Core Java (no Spring Boot)**
- Strong data integrity with MySQL foreign keys
- Graph traversal–based dependency resolution

---

##  Problem Statement

In microservice-based systems, APIs often depend on other APIs.  
When one API fails, it can cause cascading failures.

Traditional relational databases are not efficient for:
- Traversing dependencies
- Finding indirect impacts

This project solves that problem using a **Graph Database (Neo4j)**.

---

##  Architecture Overview

- **MySQL**
  - Stores Services and APIs
  - Enforces relational integrity (foreign keys, unique constraints)

- **Neo4j**
  - Stores APIs as graph nodes
  - Stores dependencies as relationships
  - Enables fast impact analysis using graph traversal

- **Java Core HTTP Server**
  - Lightweight REST API implementation
  - Custom handlers for each endpoint
  - No frameworks (Spring Boot not used)

---

##  Tech Stack

- Java (Core Java, HttpServer)
- MySQL
- Neo4j
- JDBC
- Git & GitHub
- Postman (for testing)

---

##  Database Design

### MySQL Tables

**services**
