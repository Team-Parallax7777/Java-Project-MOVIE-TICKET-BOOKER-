package com.moviereservation.service;

import com.moviereservation.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingService {

    public boolean bookSeat(int userId, int movieId, int seatId) {
        String checkSql = "SELECT is_booked FROM seats WHERE seat_id = ?";
        String bookSql = "INSERT INTO bookings (user_id, movie_id, seat_id) VALUES (?, ?, ?)";
        String updateSql = "UPDATE seats SET is_booked = TRUE WHERE seat_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, seatId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next() && rs.getBoolean("is_booked")) {
                System.out.println("Seat already booked.");
                return false;
            }

            PreparedStatement bookPs = con.prepareStatement(bookSql);
            bookPs.setInt(1, userId);
            bookPs.setInt(2, movieId);
            bookPs.setInt(3, seatId);
            bookPs.executeUpdate();

            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setInt(1, seatId);
            updatePs.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelBooking(int bookingId, int seatId) {
        String deleteSql = "DELETE FROM bookings WHERE booking_id = ?";
        String updateSql = "UPDATE seats SET is_booked = FALSE WHERE seat_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement deletePs = con.prepareStatement(deleteSql);
            deletePs.setInt(1, bookingId);
            deletePs.executeUpdate();

            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setInt(1, seatId);
            updatePs.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Cancellation failed: " + e.getMessage());
            return false;
        }
    }
}
