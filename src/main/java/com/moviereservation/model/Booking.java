package com.moviereservation.model;

public class Booking {

    private int bookingId;
    private int userId;
    private int movieId;
    private int seatId;

    public Booking() {
    }

    public Booking(int bookingId, int userId, int movieId, int seatId) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.seatId = seatId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
