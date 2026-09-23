-- Movie Reservation System - Database Schema

CREATE DATABASE IF NOT EXISTS movie_reservation;
USE movie_reservation;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15)
);

CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    show_time VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    total_seats INT NOT NULL
);

CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    is_booked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    seat_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

-- Sample movies
INSERT INTO movies (title, show_time, price, total_seats) VALUES
('Inception', '10:00 AM', 200.0, 10),
('Interstellar', '1:00 PM', 220.0, 10),
('The Dark Knight', '5:00 PM', 250.0, 10);

-- Generate seats A1-A10 for each movie
INSERT INTO seats (movie_id, seat_number, is_booked)
SELECT m.movie_id, CONCAT('A', n.num), FALSE
FROM movies m
JOIN (
    SELECT 1 AS num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
    UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
) n;
