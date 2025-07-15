package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StaffDAO {

	// Get staff by STAFF_ID
	public Staff getStaffById(String staffId) {
		String sql = "SELECT * FROM STAFFS WHERE STAFF_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, staffId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFF_ID"));
				staff.setStaffName(rs.getString("STAFF_NAME"));
				staff.setStaffEmail(rs.getString("STAFF_EMAIL"));
				staff.setStaffPhoneNumber(rs.getString("STAFF_PHONE_NUMBER"));
				staff.setStaffPassword(rs.getString("STAFF_PASSWORD"));
				staff.setStaffPicture(rs.getString("STAFF_PICTURE"));
				staff.setDescription(rs.getString("STAFF_DESCRIPTION"));
				staff.setStaffRole(rs.getString("ROLE"));
				staff.setAdminId(rs.getString("ADMIN_ID"));
				return staff;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get staff by EMAIL
	public Staff getStaffByEmail(String email) {
		String sql = "SELECT * FROM STAFFS WHERE STAFF_EMAIL = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Staff staff = new Staff();
				staff.setStaffId(rs.getString("STAFF_ID"));
				staff.setStaffName(rs.getString("STAFF_NAME"));
				staff.setStaffEmail(rs.getString("STAFF_EMAIL"));
				staff.setStaffPhoneNumber(rs.getString("STAFF_PHONE_NUMBER"));
				staff.setStaffPassword(rs.getString("STAFF_PASSWORD"));
				staff.setStaffPicture(rs.getString("STAFF_PICTURE"));
				staff.setDescription(rs.getString("STAFF_DESCRIPTION"));
				staff.setStaffRole(rs.getString("ROLE"));
				staff.setAdminId(rs.getString("ADMIN_ID"));
				return staff;
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
//	public Staff getStaffByName(String name) {
//		String sql = "SELECT * FROM STAFFS WHERE STAFF_NAME = ?";
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, name);
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				Staff staff = new Staff();
//				staff.setStaffId(rs.getString("STAFF_ID"));
//				staff.setStaffName(rs.getString("STAFF_NAME"));
//				staff.setStaffEmail(rs.getString("STAFF_EMAIL"));
//				staff.setStaffPhoneNumber(rs.getString("STAFF_PHONE_NUMBER"));
//				staff.setStaffPassword(rs.getString("STAFF_PASSWORD"));
//				staff.setStaffPicture(rs.getString("STAFF_PICTURE"));
//				staff.setDescription(rs.getString("STAFF_DESCRIPTION"));
//				staff.setStaffRole(rs.getString("ROLE"));
//				staff.setAdminId(rs.getString("ADMIN_ID"));
//				return staff;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	// Update staff info
	public boolean updateStaff(Staff staff) {
		String sql = "UPDATE STAFFS SET STAFF_NAME = ?, STAFF_EMAIL = ?, STAFF_PHONE_NUMBER = ?, STAFF_PASSWORD = ?, STAFF_PICTURE = ?, STAFF_DESCRIPTION = ?, ROLE = ?, ADMIN_ID = ? WHERE STAFF_ID = ?";
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
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Get all barbers
	public List<Staff> getAllBarbers() {
	    List<Staff> barbers = new ArrayList<>();
	    String sql = """
	        SELECT s.*, a.STAFF_NAME AS ADMIN_NAME
	        FROM STAFFS s
	        LEFT JOIN STAFFS a ON s.ADMIN_ID = a.STAFF_ID
	        WHERE s.ROLE = 'Barber'
	    """;

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Staff staff = new Staff();
	            staff.setStaffId(rs.getString("STAFF_ID"));
	            staff.setStaffName(rs.getString("STAFF_NAME"));
	            staff.setStaffEmail(rs.getString("STAFF_EMAIL"));
	            staff.setStaffPhoneNumber(rs.getString("STAFF_PHONE_NUMBER"));
	            staff.setStaffPassword(rs.getString("STAFF_PASSWORD"));
	            staff.setStaffPicture(rs.getString("STAFF_PICTURE"));
	            staff.setDescription(rs.getString("STAFF_DESCRIPTION"));
	            staff.setStaffRole(rs.getString("ROLE"));
	            staff.setAdminId(rs.getString("ADMIN_ID"));

	            // SET NAMA ADMIN
	            staff.setAdminName(rs.getString("ADMIN_NAME"));

	            barbers.add(staff);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return barbers;
	}


	// Check if any barber is available for a slot and date
	public boolean isAnyBarberAvailable(String slot, String date) {
		String sql = "SELECT COUNT(*) AS available " +
				"FROM STAFFS s " +
				"WHERE s.ROLE = 'Barber' AND s.STAFF_ID NOT IN (" +
				"  SELECT a.BARBER_ID FROM APPOINTMENTS a " +
				"  WHERE a.APPOINTMENT_DATE = ? AND a.APPOINTMENT_TIME = ?" +
				")";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(date, inputFormatter);
			stmt.setDate(1, java.sql.Date.valueOf(localDate));
			stmt.setString(2, slot);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int available = rs.getInt("available");
				return available > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Get unavailable barbers for a specific slot and date
	// excludeappointmentId is for not include this appointment in the check
	public List<String> getUnavailableBarbersForSlot(String slot, String date, String excludeAppointmentId) {
		List<String> unavailable = new ArrayList<>();
		String sql = "SELECT s.STAFF_ID FROM STAFFS s " +
				"JOIN APPOINTMENTS a ON s.STAFF_ID = a.BARBER_ID " +
				"WHERE s.ROLE = 'Barber' AND a.APPOINTMENT_DATE = ? AND a.APPOINTMENT_TIME = ? ";
		if (excludeAppointmentId != null) {
			sql += "AND a.APPOINTMENT_ID <> ? ";
		}
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(date, inputFormatter);
			stmt.setDate(1, java.sql.Date.valueOf(localDate));
			stmt.setString(2, slot);
			if (excludeAppointmentId != null) {
				stmt.setString(3, excludeAppointmentId);
			}
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					unavailable.add(rs.getString("STAFF_ID"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unavailable;
	}

	// Overload for convenience
	public List<String> getUnavailableBarbersForSlot(String slot, String date) {
		return getUnavailableBarbersForSlot(slot, date, null);
	}

	// Check if a specific barber is available
	public boolean isBarberAvailable(String staffId, String date, String slot) {
		String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE BARBER_ID = ? AND APPOINTMENT_DATE = ? AND APPOINTMENT_TIME = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, staffId);
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(date, inputFormatter);
			stmt.setDate(2, java.sql.Date.valueOf(localDate));
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
	
	// Get barber name by staff ID 
	public String getBarberNameById(String staffId) {
		Staff staff = getStaffById(staffId);
		return (staff != null) ? staff.getStaffName() : null;
	}
	
	// ✅ Check if email exists in STAFFS table - alip
	public boolean emailExists(String email) {
		String sql = "SELECT 1 FROM STAFFS WHERE STAFF_EMAIL = ?";
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			return rs.next(); // true kalau email wujud
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// ✅ Insert new staff into STAFFS table (auto-increment STAFF_ID) - alip
	public String insertStaff(Staff staff) {
	    String sql = "INSERT INTO STAFFS (STAFF_NAME, STAFF_EMAIL, STAFF_PHONE_NUMBER, STAFF_PASSWORD, STAFF_PICTURE, STAFF_DESCRIPTION, ROLE, ADMIN_ID) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"STAFF_ID"})) {

	        stmt.setString(1, staff.getStaffName());
	        stmt.setString(2, staff.getStaffEmail());
	        stmt.setString(3, staff.getStaffPhoneNumber());
	        stmt.setString(4, staff.getStaffPassword());
	        stmt.setString(5, staff.getStaffPicture());
	        stmt.setString(6, staff.getDescription());
	        stmt.setString(7, staff.getStaffRole());

	        // ADMIN_ID
	        if (staff.getAdminId() != null && !staff.getAdminId().isEmpty()) {
	            stmt.setLong(8, Long.parseLong(staff.getAdminId()));
	        } else {
	            stmt.setNull(8, java.sql.Types.NUMERIC);
	        }

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            ResultSet rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getString(1); // ✅ return STAFF_ID yang baru
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	//update profile staff - alip
	public boolean updateProfileStaff(Staff staff) {
	    String sql = "UPDATE STAFFS SET STAFF_NAME = ?, STAFF_EMAIL = ?, STAFF_PHONE_NUMBER = ?, STAFF_DESCRIPTION = ?, STAFF_PICTURE = ? WHERE STAFF_ID = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, staff.getStaffName());
	        stmt.setString(2, staff.getStaffEmail());
	        stmt.setString(3, staff.getStaffPhoneNumber());
	        stmt.setString(4, staff.getDescription());
	        stmt.setString(5, staff.getStaffPicture());
	        stmt.setString(6, staff.getStaffId());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
