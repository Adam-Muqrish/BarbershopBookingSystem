package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.customer.model.Feedback;
import com.hugi.barbershop.common.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


public class FeedbackDAO {
    // Insert a new feedback (FEEDBACK_ID is auto-incremented)
    public String insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO FEEDBACKS (COMMENTS, RATING, APPOINTMENT_ID) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"FEEDBACK_ID"})) {
            stmt.setString(1, feedback.getComments());
            stmt.setInt(2, feedback.getRating());
            stmt.setInt(3, feedback.getAppointmentId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getString(1); // Return generated FEEDBACK_ID
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get feedback by appointment ID
    public Feedback getFeedbackByAppointment(int appointmentId) {
        String sql = "SELECT * FROM FEEDBACKS WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(rs.getString("FEEDBACK_ID"));
                feedback.setComments(rs.getString("COMMENTS"));
                feedback.setRating(rs.getInt("RATING"));
                feedback.setAppointmentId(rs.getInt("APPOINTMENT_ID"));
                return feedback;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update existing feedback
    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE FEEDBACKS SET COMMENTS = ?, RATING = ? WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, feedback.getComments());
            stmt.setInt(2, feedback.getRating());
            stmt.setInt(3, feedback.getAppointmentId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete feedback by appointment ID
    public boolean deleteFeedback(int appointmentId) {
        String sql = "DELETE FROM FEEDBACKS WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Get all feedback (staff)
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.*, c.CUST_NAME " +
                "FROM FEEDBACKS f " +
                "JOIN APPOINTMENTS a ON f.APPOINTMENT_ID = a.APPOINTMENT_ID " +
                "JOIN CUSTOMERS c ON a.CUST_ID = c.CUST_ID " +
                "ORDER BY f.FEEDBACK_ID DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setCustomerName(rs.getString("CUST_NAME")); 
                feedback.setFeedbackId(rs.getString("FEEDBACK_ID"));
                feedback.setComments(rs.getString("COMMENTS"));
                feedback.setRating(rs.getInt("RATING"));
                feedback.setAppointmentId(rs.getInt("APPOINTMENT_ID"));
                feedbackList.add(feedback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
}
