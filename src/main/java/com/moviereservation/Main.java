package com.moviereservation;

import com.moviereservation.model.Movie;
import com.moviereservation.model.Seat;
import com.moviereservation.model.User;
import com.moviereservation.service.AuthService;
import com.moviereservation.service.BookingService;
import com.moviereservation.service.BookingService.BookingInfo;
import com.moviereservation.service.MovieService;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AuthService authService = new AuthService();
    static MovieService movieService = new MovieService();
    static BookingService bookingService = new BookingService();
    static User loggedInUser = null;

    // safely reads an integer, re-prompting on bad input instead of crashing
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== MOVIE RESERVATION SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    if (loggedInUser != null) {
                        showMainMenu();
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    static void registerUser() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Name, email, and password cannot be empty.");
            return;
        }

        boolean success = authService.register(name, email, password, phone);
        if (success) {
            System.out.println("Registration successful. Please login.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    static void loginUser() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        loggedInUser = authService.login(email, password);
        if (loggedInUser != null) {
            System.out.println("Welcome, " + loggedInUser.getName() + "!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    static void showMainMenu() {
        int choice;
        do {
            System.out.println("\n----- MAIN MENU -----");
            System.out.println("1. View Movies & Book Seat");
            System.out.println("2. My Bookings");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Logout");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    viewMoviesAndBook();
                    break;
                case 2:
                    viewMyBookings();
                    break;
                case 3:
                    cancelBooking();
                    break;
            }
        } while (choice != 4);

        loggedInUser = null;
    }

    static void viewMoviesAndBook() {
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("\nAvailable Movies:");
        for (Movie m : movies) {
            System.out.println(m);
        }

        int movieId = readInt("Enter movie ID to book: ");

        List<Seat> seats = movieService.getSeatsForMovie(movieId);
        if (seats.isEmpty()) {
            System.out.println("Invalid movie ID or no seats found.");
            return;
        }

        System.out.println("\nSeat Layout (X = booked):");
        for (Seat s : seats) {
            String status = s.isBooked() ? "[X]" : "[" + s.getSeatNumber() + "]";
            System.out.print(status + " ");
        }
        System.out.println();

        System.out.print("\nEnter seat number to book (e.g. A3): ");
        String seatNumber = sc.nextLine();

        Seat selectedSeat = null;
        for (Seat s : seats) {
            if (s.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                selectedSeat = s;
                break;
            }
        }

        if (selectedSeat == null) {
            System.out.println("Seat not found.");
            return;
        }

        boolean booked = bookingService.bookSeat(loggedInUser.getUserId(), movieId, selectedSeat.getSeatId());
        if (booked) {
            System.out.println("Seat " + seatNumber + " booked successfully!");
        }
    }

    static void viewMyBookings() {
        List<BookingInfo> bookings = bookingService.getMyBookings(loggedInUser.getUserId());

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
            return;
        }

        System.out.println("\n----- MY BOOKINGS -----");
        for (BookingInfo b : bookings) {
            System.out.println("Booking ID: " + b.bookingId
                    + " | Movie: " + b.movieTitle
                    + " | Seat: " + b.seatNumber
                    + " | Booked on: " + b.bookingDate);
        }
    }

    static void cancelBooking() {
        List<BookingInfo> bookings = bookingService.getMyBookings(loggedInUser.getUserId());

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        System.out.println("\n----- MY BOOKINGS -----");
        for (BookingInfo b : bookings) {
            System.out.println("Booking ID: " + b.bookingId
                    + " | Movie: " + b.movieTitle
                    + " | Seat: " + b.seatNumber);
        }

        int bookingId = readInt("\nEnter Booking ID to cancel: ");

        int seatId = -1;
        for (BookingInfo b : bookings) {
            if (b.bookingId == bookingId) {
                seatId = b.seatId;
                break;
            }
        }

        if (seatId == -1) {
            System.out.println("Booking ID not found.");
            return;
        }

        boolean cancelled = bookingService.cancelBooking(bookingId, seatId);
        if (cancelled) {
            System.out.println("Booking cancelled successfully.");
        }
    }
}
