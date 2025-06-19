package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StaffDAO {

	// Get staff by STAFFID
	public Staff getStaffById(String staffId) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM STAFF WHERE STAFFID = ?")) {

			stmt.setString(1, staffId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("Staff found: " + rs.getString("EMAIL"));
				System.out.println("DB password: " + rs.getString("PASSWORD")); // DEBUG

				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFFID"));
				staff.setStaffName(rs.getString("NAME"));
				staff.setStaffEmail(rs.getString("EMAIL"));
				staff.setStaffPhoneNumber(rs.getString("PHONENUMBER"));
				staff.setStaffPassword(rs.getString("PASSWORD"));
				staff.setStaffPicture(rs.getString("PICTURE"));
				staff.setDescription(rs.getString("DESCRIPTION"));
				staff.setStaffRole(rs.getString("ROLE"));
				staff.setAdminId(rs.getString("ADMINID"));
				return staff;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Get staff by EMAIL
	public Staff getStaffByEmail(String email) {
		String sql = "SELECT * FROM STAFF WHERE EMAIL = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			System.out.println("Looking up staff email: " + email); // Debug line

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("Staff found: " + rs.getString("EMAIL"));
				String dbPassword = rs.getString("PASSWORD");
				System.out.println("DB password: " + dbPassword);

				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFFID"));
				staff.setStaffName(rs.getString("NAME"));
				staff.setStaffEmail(rs.getString("EMAIL"));
				staff.setStaffPhoneNumber(rs.getString("PHONENUMBER"));
				staff.setStaffPassword(rs.getString("PASSWORD"));
				staff.setStaffPicture(rs.getString("PICTURE"));
				staff.setDescription(rs.getString("DESCRIPTION"));
				staff.setStaffRole(rs.getString("ROLE"));
				staff.setAdminId(rs.getString("ADMINID"));
				return staff;
			} else {
				// No result was found
				System.out.println("No record found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Verify password (plain-text, NOT RECOMMENDED in production)
	public boolean verifyPassword(Staff staff, String inputPassword) {
		return staff.getStaffPassword() != null &&
				staff.getStaffPassword().equals(inputPassword);
	}

	// Get staff by name
	public Staff getStaffByName(String name) {
		String sql = "SELECT * FROM STAFF WHERE NAME = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFFID"));
				staff.setStaffName(rs.getString("NAME"));
				// set other fields if needed
				return staff;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	// Update staff info
	public boolean updateStaff(Staff staff) {
		String sql = "UPDATE STAFF SET NAME = ?, EMAIL = ?, PHONENUMBER = ?, PASSWORD = ?, PICTURE = ?, DESCRIPTION = ?, ROLE = ?, ADMINID = ? WHERE STAFFID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, staff.getStaffName());
			stmt.setString(2, staff.getStaffEmail());
			stmt.setString(3, staff.getStaffPhoneNumber());
			stmt.setString(4, staff.getStaffPassword());
			stmt.setString(5, staff.getStaffPicture());
			stmt.setString(6, staff.getDescription());
			stmt.setString(7, staff.getStaffRole());
			stmt.setString(8, staff.getAdminId());
			stmt.setString(9, staff.getStaffId());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Staff> getAllBarbers() {
		// TODO Auto-generated method stub
		List<Staff> barbers = new ArrayList<>();
		String sql = "SELECT * FROM STAFF WHERE ROLE = 'Barber'";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFFID"));
				staff.setStaffName(rs.getString("NAME"));
				// set other fields if needed
				barbers.add(staff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return barbers;
	}

	public boolean isAnyBarberAvailable(String slot, String date) {
		String sql = "SELECT COUNT(*) AS available " +
				"FROM STAFF s " +
				"WHERE s.ROLE = 'Barber' AND s.STAFFID NOT IN (" +
				"  SELECT a.STAFFID FROM APPOINTMENTS a " +
				"  WHERE a.APPOINTMENTDATE = ? AND a.APPOINTMENTTIME = ?" +
				")";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			// Convert ISO date to Oracle format before setting parameter
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter oracleFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
			LocalDate localDate = LocalDate.parse(date, inputFormatter);
			String oracleDate = localDate.format(oracleFormatter).toUpperCase();
			stmt.setString(1, oracleDate);
			stmt.setString(2, slot);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int available = rs.getInt("available");
				System.out.println("Available barbers for " + date + " " + slot + ": " + available);
				return available > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Get unavailable barbers for a specific slot and date
	public List<String> getUnavailableBarbersForSlot(String slot, String date, String excludeAppointmentId) {
	    List<String> unavailable = new ArrayList<>();
	    String sql = "SELECT s.STAFFID FROM STAFF s " +
	            "JOIN APPOINTMENTS a ON s.STAFFID = a.STAFFID " +
	            "WHERE s.ROLE = 'Barber' AND a.APPOINTMENTDATE = ? AND a.APPOINTMENTTIME = ? ";
	    if (excludeAppointmentId != null) {
	        sql += "AND a.APPOINTMENTID <> ? ";
	    }
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        DateTimeFormatter oracleFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
	        LocalDate localDate = LocalDate.parse(date, inputFormatter);
	        String oracleDate = localDate.format(oracleFormatter).toUpperCase();
	        stmt.setString(1, oracleDate);
	        stmt.setString(2, slot);
	        if (excludeAppointmentId != null) {
	            stmt.setString(3, excludeAppointmentId);
	        }
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                unavailable.add(rs.getString("STAFFID"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return unavailable;
	}

	// Update the original method to call the new one with null
	public List<String> getUnavailableBarbersForSlot(String slot, String date) {
	    return getUnavailableBarbersForSlot(slot, date, null);
	}

	public boolean isBarberAvailable(String staffId, String date, String slot) {
		String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE STAFFID = ? AND APPOINTMENTDATE = ? AND APPOINTMENTTIME = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, staffId);
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter oracleFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
			LocalDate localDate = LocalDate.parse(date, inputFormatter);
			String oracleDate = localDate.format(oracleFormatter).toUpperCase();
			stmt.setString(2, oracleDate); // Use the same date format as stored in DB
			stmt.setString(3, slot);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) == 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}