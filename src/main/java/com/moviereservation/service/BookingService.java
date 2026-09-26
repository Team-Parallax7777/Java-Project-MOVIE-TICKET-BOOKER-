package com.moviereservation.service;

import com.moviereservation.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    // simple holder class just to carry joined booking details back to Main
    public static class BookingInfo {
        public int bookingId;
        public int seatId;
        public String movieTitle;
        public String seatNumber;
        public String bookingDate;
    }

    public List<BookingInfo> getMyBookings(int userId) {
        List<BookingInfo> list = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.seat_id, m.title, s.seat_number, b.booking_date " +
                "FROM bookings b " +
                "JOIN movies m ON b.movie_id = m.movie_id " +
                "JOIN seats s ON b.seat_id = s.seat_id " +
                "WHERE b.user_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookingInfo info = new BookingInfo();
                info.bookingId = rs.getInt("booking_id");
                info.seatId = rs.getInt("seat_id");
                info.movieTitle = rs.getString("title");
                info.seatNumber = rs.getString("seat_number");
                info.bookingDate = rs.getString("booking_date");
                list.add(info);
            }
        } catch (Exception e) {
            System.out.println("Could not fetch bookings: " + e.getMessage());
        }
        return list;
    }

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
