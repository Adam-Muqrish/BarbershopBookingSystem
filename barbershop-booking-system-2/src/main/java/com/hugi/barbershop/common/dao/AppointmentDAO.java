package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.common.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
	// Get appointment by ID
	public Appointment getAppointmentById(String appointmentId) {
		String sql = "SELECT * FROM APPOINTMENTS WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, appointmentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getString("APPOINTMENTID"));
				appointment.setCustBookFor(rs.getString("CUSTBOOKFOR"));
				appointment.setAppointmentDate(rs.getDate("APPOINTMENTDATE").toString());
				appointment.setAppointmentTime(rs.getString("APPOINTMENTTIME"));
				appointment.setCustType(rs.getString("CUSTTYPE"));
				appointment.setPaymentStatus(rs.getString("PAYMENTSTATUS"));
				appointment.setServiceStatus(rs.getString("SERVICESTATUS"));
				appointment.setCustId(rs.getString("CUSTID"));
				appointment.setStaffId(rs.getString("STAFFID"));
				appointment.setValueLoyalty(rs.getInt("VALUELOYALTY"));
				return appointment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Insert a new appointment
	public String insertAppointment(Appointment appointment) {
		String sql = "INSERT INTO APPOINTMENTS (CUSTBOOKFOR, APPOINTMENTDATE, APPOINTMENTTIME, CUSTTYPE, PAYMENTSTATUS, SERVICESTATUS, CUSTID, STAFFID, VALUELOYALTY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"APPOINTMENTID"})) {
			stmt.setString(1, appointment.getCustBookFor());
			stmt.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setString(3, appointment.getAppointmentTime());
			stmt.setString(4, appointment.getCustType());
			stmt.setString(5, appointment.getPaymentStatus());
			stmt.setString(6, appointment.getServiceStatus());
			stmt.setString(7, appointment.getCustId());
			stmt.setString(8, appointment.getStaffId());
			stmt.setInt(9, appointment.getValueLoyalty());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0) {
				// Retrieve the generated appointment ID
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getString(1); // Return generated APPOINTMENTID
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get all appointments for a customer
	public List<Appointment> getAppointmentsByCustomerId(String custId) {
		List<Appointment> appointments = new ArrayList<>();
		String sql = "SELECT a.*, s.NAME AS BARBERNAME\r\n"
				+ "FROM APPOINTMENTS a\r\n"
				+ "LEFT JOIN STAFF s ON a.STAFFID = s.STAFFID\r\n"
				+ "WHERE a.CUSTID = ?\r\n"
				+ "  AND (a.SERVICESTATUS = 'Pending' OR a.SERVICESTATUS = 'pending')\r\n"
				+ "ORDER BY a.APPOINTMENTDATE DESC, a.APPOINTMENTTIME DESC\r\n"
				+ "";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, custId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getString("APPOINTMENTID"));
				appointment.setCustBookFor(rs.getString("CUSTBOOKFOR"));
				appointment.setAppointmentDate(rs.getDate("APPOINTMENTDATE").toString());
				appointment.setAppointmentTime(rs.getString("APPOINTMENTTIME"));
				appointment.setCustType(rs.getString("CUSTTYPE"));
				appointment.setPaymentStatus(rs.getString("PAYMENTSTATUS"));
				appointment.setServiceStatus(rs.getString("SERVICESTATUS"));
				appointment.setCustId(rs.getString("CUSTID"));
				appointment.setStaffId(rs.getString("STAFFID"));
				appointment.setValueLoyalty(rs.getInt("VALUELOYALTY"));
				appointment.setAppointmentBarber(rs.getString("BARBERNAME"));
				appointments.add(appointment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointments;
	}

	// Get all appointments for a customer with status 'Done' and 'Cancelled'
	public List<Appointment> getHistoryAppointmentsByCustomerId(String custId, int offset, int pageSize) {
		List<Appointment> appointments = new ArrayList<>();
		String sql = "SELECT a.*, s.NAME AS BARBERNAME " +
				"FROM APPOINTMENTS a " +
				"LEFT JOIN STAFF s ON a.STAFFID = s.STAFFID " +
				"WHERE a.CUSTID = ? AND (a.SERVICESTATUS = 'Done' OR a.SERVICESTATUS = 'Cancelled') " +
				"ORDER BY a.APPOINTMENTDATE DESC, a.APPOINTMENTTIME DESC " +
				"OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, custId);
			stmt.setInt(2, offset);
			stmt.setInt(3, pageSize);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getString("APPOINTMENTID"));
				appointment.setCustBookFor(rs.getString("CUSTBOOKFOR"));
				appointment.setAppointmentDate(rs.getDate("APPOINTMENTDATE").toString());
				appointment.setAppointmentTime(rs.getString("APPOINTMENTTIME"));
				appointment.setCustType(rs.getString("CUSTTYPE"));
				appointment.setPaymentStatus(rs.getString("PAYMENTSTATUS"));
				appointment.setServiceStatus(rs.getString("SERVICESTATUS"));
				appointment.setCustId(rs.getString("CUSTID"));
				appointment.setStaffId(rs.getString("STAFFID"));
				appointment.setValueLoyalty(rs.getInt("VALUELOYALTY"));
				appointment.setAppointmentBarber(rs.getString("BARBERNAME"));
				appointments.add(appointment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointments;
	}

	// Method to count total done appointments
	public int countDoneAppointmentsByCustomerId(String custId) {
		String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE CUSTID = ? AND SERVICESTATUS = 'Done'";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, custId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Update payment status for an appointment
	public boolean updatePaymentStatus(String custId, String appointmentDate, String appointmentTime, String paymentStatus) {
		String sql = "UPDATE APPOINTMENTS SET PAYMENTSTATUS = ? WHERE CUSTID = ? AND APPOINTMENTDATE = ? AND APPOINTMENTTIME = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentStatus);
			stmt.setString(2, custId);
			stmt.setDate(3, java.sql.Date.valueOf(appointmentDate));
			stmt.setString(4, appointmentTime);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to get the latest appointment for a customer
	public Appointment getLatestAppointment(String custId) {
		String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTID = ? ORDER BY APPOINTMENTDATE DESC, APPOINTMENTTIME DESC FETCH FIRST 1 ROWS ONLY";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, custId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getString("APPOINTMENTID"));
				appointment.setCustBookFor(rs.getString("CUSTBOOKFOR"));
				appointment.setAppointmentDate(rs.getDate("APPOINTMENTDATE").toString());
				appointment.setAppointmentTime(rs.getString("APPOINTMENTTIME"));
				appointment.setCustType(rs.getString("CUSTTYPE"));
				appointment.setPaymentStatus(rs.getString("PAYMENTSTATUS"));
				appointment.setServiceStatus(rs.getString("SERVICESTATUS"));
				appointment.setCustId(rs.getString("CUSTID"));
				appointment.setStaffId(rs.getString("STAFFID"));
				appointment.setValueLoyalty(rs.getInt("VALUELOYALTY"));
				return appointment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to get barber name by staff ID
	public String getBarberNameById(String staffId) {
		String sql = "SELECT NAME FROM STAFF WHERE STAFFID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, staffId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("NAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to mark an appointment as done
	public boolean markAsDone(int appointmentId) {
		String sql = "UPDATE APPOINTMENTS SET SERVICESTATUS = 'Done' WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, appointmentId);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to mark an appointment as cancelled
	public boolean markAsCancelled(String appointmentId) {
		String sql = "UPDATE APPOINTMENTS SET SERVICESTATUS = 'Cancelled' WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, appointmentId);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to update an existing appointment
	public boolean updateAppointment(Appointment appointment) {
		String sql = "UPDATE APPOINTMENTS SET APPOINTMENTDATE = ?, APPOINTMENTTIME = ?, STAFFID = ? WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setString(2, appointment.getAppointmentTime());
			stmt.setString(3, appointment.getStaffId());
			stmt.setString(4, appointment.getAppointmentId());
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to get total loyalty points by customer ID that not cancelled
	public int getTotalLoyaltyPointsByCustomerId(String custId) {
	    String sql = "SELECT SUM(VALUELOYALTY) FROM APPOINTMENTS WHERE CUSTID = ? AND SERVICESTATUS <> 'Cancelled'";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, custId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
}
