package com.moviereservation.model;

public class Seat {

    private int seatId;
    private int movieId;
    private String seatNumber;
    private boolean isBooked;

    public Seat() {
    }

    public Seat(int seatId, int movieId, String seatNumber, boolean isBooked) {
        this.seatId = seatId;
        this.movieId = movieId;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
