# Student Management Backend

A Spring Boot REST API for managing students with JWT authentication and MySQL database.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman

## Features

- User Registration
- User Login
- JWT Authentication
- Add Student
- View All Students
- View Student by ID
- Update Student
- Delete Student

## API Endpoints

### Authentication

POST `/auth/register`

POST `/auth/login`

### Student Management

POST `/students`

GET `/students`

GET `/students/{id}`

PUT `/students/{id}`

DELETE `/students/{id}`

## Database

Database name:

`student_management`

## How to Run

1. Create the MySQL database.
2. Update MySQL username and password in `application.properties`.
3. Run the Spring Boot application.
4. Test the APIs using Postman.

## Author

Aboli Bhojane