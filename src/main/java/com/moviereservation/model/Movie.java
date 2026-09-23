package com.moviereservation.model;

public class Movie {

    private int movieId;
    private String title;
    private String showTime;
    private double price;
    private int totalSeats;

    public Movie() {
    }

    public Movie(int movieId, String title, String showTime, double price, int totalSeats) {
        this.movieId = movieId;
        this.title = title;
        this.showTime = showTime;
        this.price = price;
        this.totalSeats = totalSeats;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return movieId + ". " + title + " - " + showTime + " - Rs." + price;
    }
}
