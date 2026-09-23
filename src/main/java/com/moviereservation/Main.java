package com.moviereservation;

import com.moviereservation.model.Movie;
import com.moviereservation.model.Seat;
import com.moviereservation.model.User;
import com.moviereservation.service.AuthService;
import com.moviereservation.service.BookingService;
import com.moviereservation.service.MovieService;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AuthService authService = new AuthService();
    static MovieService movieService = new MovieService();
    static BookingService bookingService = new BookingService();
    static User loggedInUser = null;

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== MOVIE RESERVATION SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

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
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();

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
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                viewMoviesAndBook();
            }
        } while (choice != 2);

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

        System.out.print("Enter movie ID to book: ");
        int movieId = sc.nextInt();
        sc.nextLine();

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
}
