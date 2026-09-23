package com.moviereservation.service;

import com.moviereservation.db.DBConnection;
import com.moviereservation.model.Movie;
import com.moviereservation.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setShowTime(rs.getString("show_time"));
                movie.setPrice(rs.getDouble("price"));
                movie.setTotalSeats(rs.getInt("total_seats"));
                movies.add(movie);
            }
        } catch (Exception e) {
            System.out.println("Could not fetch movies: " + e.getMessage());
        }
        return movies;
    }

    public List<Seat> getSeatsForMovie(int movieId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE movie_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setMovieId(rs.getInt("movie_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setBooked(rs.getBoolean("is_booked"));
                seats.add(seat);
            }
        } catch (Exception e) {
            System.out.println("Could not fetch seats: " + e.getMessage());
        }
        return seats;
    }
}
