# SEPA Payment Processing Application

## Overview
This project provides functionality to process SEPA (Single Euro Payments Area) payments. It supports payment initiation, validation, processing, and status tracking for SEPA credit transfers.

## Features
- SEPA payment request processing
- Payment validation and verification
- Transaction status tracking
- Error handling and logging
- Secure payment data management

## Prerequisites
- Java / Node.js / Python (update based on your technology stack)
- Database connectivity
- Access to SEPA payment gateway or banking APIs

## Installation
1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```

## API Listing Service (added)

This repository includes a small Node.js/Express service that lists API details, functions/channels used, upcoming projects, deployed changes, and upcoming releases.

- Files:
   - [package.json](package.json)
   - [index.js](index.js)
   - [routes/api.js](routes/api.js)
   - [data/apiData.json](data/apiData.json)

- Endpoints:
   - `GET /api/details` — API endpoints and descriptions
   - `GET /api/functions` — Functions used
   - `GET /api/channels` — Channels used
   - `GET /api/upcoming-projects` — Upcoming projects
   - `GET /api/deployed-changes` — Recently deployed changes
   - `GET /api/upcoming-releases` — Upcoming releases

### Run locally

Install dependencies and start the service:

```bash
cd $(pwd)
npm install
npm start
```

The service listens on port `3000` by default.

### Shop Route

The Express app now exposes `/shop` and redirects to the Spring Boot shop app at `http://localhost:8081`.

To use the shop UI, run the Spring Boot app separately and then visit:

```bash
cd shop-app
mvn spring-boot:run
```

Browse the shop at `http://localhost:8081` or request `http://localhost:3000/shop`.

## Spring Boot Shop App

A new Spring Boot application has been added to `shop-app/`.

### What it includes
- `shop-app/pom.xml` — Maven configuration
- `shop-app/src/main/java/com/example/shop/ShopApplication.java` — Spring Boot entrypoint
- `shop-app/src/main/java/com/example/shop/controller/ProductController.java` — Product API
- `shop-app/src/main/java/com/example/shop/model/Product.java` — Product model
- `shop-app/src/main/resources/static/index.html` — Shop UI
- `shop-app/src/main/resources/static/shop.js` — Cart + chart logic
- `shop-app/src/main/resources/static/styles.css` — Shop styles
- `shop-app/src/main/resources/application.properties` — server port config

### Run the shop app

```bash
cd shop-app
mvn spring-boot:run
```

Open `http://localhost:8081` in your browser after starting the application.
