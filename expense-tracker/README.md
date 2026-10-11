# Expense Tracker

A small expense tracking web app backed by a Java Spring Boot REST API and H2 database.

## Features
- Add, list, edit and delete expenses
- Categorize expenses
- Filter by category
- Calculate total spending and category totals
- Query expenses between two dates
- Basic browser UI served by Spring Boot
- Validation for positive amounts and required fields

## Requirements
- Java 17+
- Maven 3.6+

## Run locally
```bash
mvn spring-boot:run
```
Open http://localhost:8081. API base URL: `http://localhost:8081/api/expenses`.

## Example API requests
Create an expense:
```bash
curl -X POST http://localhost:8081/api/expenses -H "Content-Type: application/json" -d '{"description":"Lunch","category":"Food","amount":180.50,"expenseDate":"2026-10-11"}'
```
Get all expenses:
```bash
curl http://localhost:8081/api/expenses
```
Summary:
```bash
curl http://localhost:8081/api/expenses/summary
```
Filter by category:
```bash
curl "http://localhost:8081/api/expenses?category=Food"
```
Date range:
```bash
curl "http://localhost:8081/api/expenses/between?start=2026-10-01&end=2026-10-31"
```

## Main concepts demonstrated
Spring MVC REST controllers, JPA entities, repository interfaces, request validation, HTTP status codes and JSON.

## Possible improvements
Add authentication, monthly charts, pagination, export to CSV and MySQL configuration.

## Author
Vedant
