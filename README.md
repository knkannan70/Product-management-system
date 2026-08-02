# Product Management System

A robust RESTful API built with Spring Boot for managing products. This application provides a comprehensive suite of CRUD operations for products while ensuring secure access through JWT-based authentication and role-based authorization.

## Features

- **Product Management:** Complete CRUD (Create, Read, Update, Delete) operations for products.
- **Secure Authentication:** User registration and login utilizing JSON Web Tokens (JWT) for secure, stateless authentication.
- **Role-Based Access Control (RBAC):** Restricts certain endpoints (like viewing or deleting users) to Admin roles only.
- **Data Validation:** Input validation using Spring Boot Validation to ensure data integrity.
- **Database Integration:** Persistent storage using MySQL and Spring Data JPA.
- **Lombok Integration:** Reduces boilerplate code (getters, setters, constructors).

## Technologies Used

- **Java 17:** Core programming language.
- **Spring Boot 3.2.5:** Framework for building the REST API.
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Boot Validation
- **JSON Web Tokens (jjwt):** For secure authentication and authorization.
- **MySQL:** Relational database management system.
- **Maven:** Build automation and dependency management tool.
- **Lombok:** Java library to minimize boilerplate code.

## Application Architecture

The application follows a standard layered architecture:
- **Controllers:** Handle incoming HTTP requests and map them to appropriate services.
- **Services:** Contain business logic and process data before interacting with the database.
- **Repositories:** Interfaces extending Spring Data JPA for database interactions.
- **Entities:** JPA entities representing the database tables.
- **DTOs:** Data Transfer Objects used to transfer data between the client and server.
- **Security:** Configurations, filters, and entry points for handling JWT validation and authentication.

## API Endpoints

### Authentication
| HTTP Method | Endpoint | Description | Access |
|-------------|----------|-------------|--------|
| POST | `/register` | Register a new user | Public |
| POST | `/login` | Authenticate user and get JWT | Public |

### Products
*Note: All product endpoints require a valid JWT token in the `Authorization` header (`Bearer <token>`).*

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | `/products` | Retrieve a list of all products |
| GET | `/products/{id}` | Retrieve details of a specific product by ID |
| POST | `/products` | Create a new product |
| PUT | `/products/{id}` | Update an existing product by ID |
| DELETE | `/products/{id}` | Delete a product by ID |

### Users (Admin Only)
*Note: These endpoints require a valid JWT token belonging to an Admin user.*

| HTTP Method | Endpoint | Description | Access |
|-------------|----------|-------------|--------|
| GET | `/users` | Retrieve a list of all users | Admin |
| DELETE | `/users/{id}` | Delete a user by ID | Admin |

## Setup and Installation

### Prerequisites
- **Java Development Kit (JDK) 17** installed.
- **Apache Maven** installed.
- **MySQL Server** installed and running.

### 1. Database Configuration
1. Open your MySQL client and create a new database for the application.
   ```sql
   CREATE DATABASE product_management;
   ```
2. Navigate to `src/main/resources/application.properties` (or `.yml`) and update the database connection properties with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/product_management
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```

### 2. Build the Application
Open a terminal in the root directory of the project and run the following Maven command to download dependencies and build the project:
```bash
mvn clean install
```

### 3. Run the Application
You can run the application using Maven:
```bash
mvn spring-boot:run
```
Alternatively, you can run the generated `.jar` file in the `target` directory:
```bash
java -jar target/product-management-0.0.1-SNAPSHOT.jar
```

The application will start on port `8080` (unless configured otherwise).

## Usage Example

1. **Register a User:**
   Send a `POST` request to `http://localhost:8080/register` with user details (username, password, etc.).

2. **Login:**
   Send a `POST` request to `http://localhost:8080/login` with your credentials. You will receive a JWT token in the response.

3. **Access Protected Endpoints:**
   Include the token in the `Authorization` header of your HTTP requests to access products:
   ```
   Authorization: Bearer <your_jwt_token_here>
   ```

## License
This project is open-source and available under the MIT License.
