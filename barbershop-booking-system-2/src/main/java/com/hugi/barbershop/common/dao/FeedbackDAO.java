package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.customer.model.Feedback;
import com.hugi.barbershop.common.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import com.hugi.barbershop.common.dao.FeedbackDAO;
import com.hugi.barbershop.staff.model.ViewFeedback;

public class FeedbackDAO {
	// Insert a new feedback
	public void insertFeedback(Feedback feedback) {
		String sql = "INSERT INTO FEEDBACKS (COMMENTS, RATING, APPOINTMENTID, CUSTID) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, feedback.getComments());
			stmt.setInt(2, feedback.getRating());
			stmt.setInt(3, feedback.getAppointmentId());
			stmt.setString(4, feedback.getCustId());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get feedback by appointment ID and customer ID
	public Feedback getFeedbackByAppointmentAndCustomer(int appointmentId, String custId) {
		String sql = "SELECT * FROM FEEDBACKS WHERE APPOINTMENTID = ? AND CUSTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, appointmentId);
			stmt.setString(2, custId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Feedback feedback = new Feedback();
				feedback.setFeedbackId(rs.getString("FEEDBACKID"));
				feedback.setComments(rs.getString("COMMENTS"));
				feedback.setRating(rs.getInt("RATING"));
				feedback.setAppointmentId(rs.getInt("APPOINTMENTID"));
				feedback.setCustId(rs.getString("CUSTID"));
				return feedback;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Update existing feedback
	public void updateFeedback(Feedback feedback) {
		String sql = "UPDATE FEEDBACKS SET COMMENTS = ?, RATING = ? WHERE APPOINTMENTID = ? AND CUSTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, feedback.getComments());
			stmt.setInt(2, feedback.getRating());
			stmt.setInt(3, feedback.getAppointmentId());
			stmt.setString(4, feedback.getCustId());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete feedback
	public void deleteFeedback(int appointmentId, String custId) {
		String sql = "DELETE FROM FEEDBACKS WHERE APPOINTMENTID = ? AND CUSTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, appointmentId);
			stmt.setString(2, custId);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//View List Feedback - Admin Part
	public List<ViewFeedback> getAllFeedbacks() {
	    List<ViewFeedback> feedbackList = new ArrayList<>();

	    String sql = """
	        SELECT f.FEEDBACKID, f.COMMENTS, f.RATING, c.CUSTNAME
	        FROM FEEDBACKS f
	        JOIN CUSTOMER c ON f.CUSTID = c.CUSTID
	        ORDER BY f.FEEDBACKID DESC
	    """;

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            ViewFeedback fb = new ViewFeedback();
	            fb.setFeedbackId(rs.getString("FEEDBACKID"));
	            fb.setCustomerName(rs.getString("CUSTNAME"));
	            fb.setComments(rs.getString("COMMENTS"));
	            fb.setRating(rs.getInt("RATING"));
	            feedbackList.add(fb);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return feedbackList;
	}



}
