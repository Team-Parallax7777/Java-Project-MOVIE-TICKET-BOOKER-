# Movie Reservation System

A simple Java console application for booking movie tickets, built with JDBC and MySQL.

## Features
- User registration and login
- View available movies
- View seat layout and book a seat
- Prevents double-booking of seats

## Tech Stack
- Java (JDK 17)
- JDBC
- MySQL
- Maven

## Setup

1. Install MySQL and make sure it's running.
2. Run the schema file to create the database and tables:
   ```
   mysql -u root -p < database/schema.sql
   ```
3. Copy `src/main/resources/config.properties.example` to `src/main/resources/config.properties` and fill in your MySQL username/password.
4. Open the project in IntelliJ as a Maven project.
5. Run `Main.java`.

## Project Structure
```
src/main/java/com/moviereservation/
├── Main.java              - entry point, console menu
├── db/DBConnection.java   - handles MySQL connection
├── model/                 - User, Movie, Seat, Booking classes
└── service/                - AuthService, MovieService, BookingService
```

## Team
- (add your names here)
