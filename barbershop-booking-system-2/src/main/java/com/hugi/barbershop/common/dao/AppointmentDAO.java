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
        String sql = "SELECT * FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getString("APPOINTMENT_ID"));
                appointment.setCustId(rs.getString("CUST_ID"));
                appointment.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
                appointment.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
                appointment.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
                appointment.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                appointment.setServiceStatus(rs.getString("SERVICE_STATUS"));
                appointment.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
                appointment.setCustType(rs.getString("CUST_TYPE"));
                appointment.setBarberId(rs.getInt("BARBER_ID"));
                return appointment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert a new appointment (APPOINTMENT_ID is auto-incremented)
    public String insertAppointment(Appointment appointment) {
        String sql = "INSERT INTO APPOINTMENTS (CUST_BOOK_FOR, APPOINTMENT_DATE, APPOINTMENT_TIME, CUST_TYPE, PAYMENT_STATUS, SERVICE_STATUS, CUST_ID, BARBER_ID, VALUE_LOYALTY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"APPOINTMENT_ID"})) {
            stmt.setString(1, appointment.getCustBookFor());
            stmt.setDate(2, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            stmt.setString(3, appointment.getAppointmentTime());
            stmt.setString(4, appointment.getCustType());
            stmt.setString(5, appointment.getPaymentStatus());
            stmt.setString(6, appointment.getServiceStatus());
            stmt.setString(7, appointment.getCustId());
            stmt.setInt(8, appointment.getBarberId());
            stmt.setInt(9, appointment.getValueLoyalty());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all pending appointments for a customer
//    public List<Appointment> getAppointmentsByCustomerId(String custId) {
//        List<Appointment> appointments = new ArrayList<>();
//        String sql = "SELECT a.*, s.STAFF_NAME AS BARBERNAME " +
//                "FROM APPOINTMENTS a " +
//                "LEFT JOIN STAFFS s ON a.STAFF_ID = s.BARBER_ID " +
//                "WHERE a.CUST_ID = ? " +
//                "AND (a.SERVICE_STATUS = 'Pending' OR a.SERVICE_STATUS = 'pending') " +
//                "ORDER BY a.APPOINTMENT_DATE DESC, a.APPOINTMENT_TIME DESC";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, custId);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Appointment appointment = new Appointment();
//                appointment.setAppointmentId(rs.getString("APPOINTMENT_ID"));
//                appointment.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
//                appointment.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
//                appointment.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
//                appointment.setCustType(rs.getString("CUST_TYPE"));
//                appointment.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
//                appointment.setServiceStatus(rs.getString("SERVICE_STATUS"));
//                appointment.setCustId(rs.getString("CUST_ID"));
//                appointment.setBarberId(rs.getInt("BARBE_ID"));
//                appointment.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
//                appointment.setAppointmentBarber(rs.getString("BARBERNAME"));
//                appointments.add(appointment);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return appointments;
//    }

    // Get all appointments for a customer with status 'Done' or 'Cancelled'
//    public List<Appointment> getHistoryAppointmentsByCustomerId(String custId, int offset, int pageSize) {
//        List<Appointment> appointments = new ArrayList<>();
//        String sql = "SELECT a.*, s.STAFF_NAME AS BARBERNAME " +
//                "FROM APPOINTMENTS a " +
//                "LEFT JOIN STAFFS s ON s.STAFF_ID = a.BARBER_ID " +
//                "WHERE a.CUST_ID = ? AND (a.SERVICE_STATUS = 'Done' OR a.SERVICE_STATUS = 'Cancelled') " +
//                "ORDER BY a.APPOINTMENT_DATE DESC, a.APPOINTMENT_TIME DESC " +
//                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, custId);
//            stmt.setInt(2, offset);
//            stmt.setInt(3, pageSize);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Appointment appointment = new Appointment();
//                appointment.setAppointmentId(rs.getString("APPOINTMENT_ID"));
//                appointment.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
//                appointment.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
//                appointment.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
//                appointment.setCustType(rs.getString("CUST_TYPE"));
//                appointment.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
//                appointment.setServiceStatus(rs.getString("SERVICE_STATUS"));
//                appointment.setCustId(rs.getString("CUST_ID"));
//                appointment.setBarberId(rs.getInt("BARBER_ID"));
//                appointment.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
//                appointment.setAppointmentBarber(rs.getString("BARBERNAME"));
//                appointments.add(appointment);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return appointments;
//    }

    // Count total done appointments for a customer
//    public int countDoneAppointmentsByCustomerId(String custId) {
//        String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE CUST_ID = ? AND SERVICE_STATUS = 'Done'";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, custId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    // Update payment status for an appointment
    public boolean updatePaymentStatus(String custId, String appointmentDate, String appointmentTime, String paymentStatus) {
        String sql = "UPDATE APPOINTMENTS SET PAYMENT_STATUS = ? WHERE CUST_ID = ? AND APPOINTMENT_DATE = ? AND APPOINTMENT_TIME = ?";
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

    // Get the latest appointment for a customer
    public Appointment getLatestAppointment(String custId) {
        String sql = "SELECT * FROM APPOINTMENTS WHERE CUST_ID = ? ORDER BY APPOINTMENT_DATE DESC, APPOINTMENT_TIME DESC FETCH FIRST 1 ROWS ONLY";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, custId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getString("APPOINTMENT_ID"));
                appointment.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
                appointment.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
                appointment.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
                appointment.setCustType(rs.getString("CUST_TYPE"));
                appointment.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                appointment.setServiceStatus(rs.getString("SERVICE_STATUS"));
                appointment.setCustId(rs.getString("CUST_ID"));
                appointment.setBarberId(rs.getInt("BARBER_ID"));
                appointment.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
                return appointment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get barber name by staff ID
    public String getBarberNameById(int barberId) {
        String sql = "SELECT STAFF_NAME FROM STAFFS s JOIN APPOINTMENTS a ON s.STAFF_ID = a.BARBER_ID WHERE s.STAFF_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, barberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("STAFF_NAME");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mark an appointment as done
//    public boolean markAsDone(int appointmentId) {
//        String sql = "UPDATE APPOINTMENTS SET SERVICE_STATUS = 'Done' WHERE APPOINTMENT_ID = ?";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, appointmentId);
//            return stmt.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    // Mark an appointment as cancelled
    public boolean markAsCancelled(String appointmentId) {
        String sql = "UPDATE APPOINTMENTS SET SERVICE_STATUS = 'Cancelled' WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing appointment
    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE APPOINTMENTS SET APPOINTMENT_DATE = ?, APPOINTMENT_TIME = ?, BARBER_ID = ? WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            stmt.setString(2, appointment.getAppointmentTime());
            stmt.setInt(3, appointment.getBarberId());
            stmt.setString(4, appointment.getAppointmentId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get total loyalty points for a customer
//    public int getTotalLoyaltyPointsByCustomerId(String custId) {
//        String sql = "SELECT SUM(VALUE_LOYALTY) FROM APPOINTMENTS WHERE CUST_ID = ?";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, custId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
    
    // Count appointments with payment_status 'done' and service_status != 'Cancelled' - alip
    public int countLoyaltyAppointmentsByCustomerId(String custId) {
        String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE CUST_ID = ? AND PAYMENT_STATUS = 'completed' AND SERVICE_STATUS != 'Cancelled'";
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
    
    // ✅ Update service status to 'Done' by appointment ID - alip
    public boolean updateServiceStatusToDone(String appointmentId) {
        String sql = "UPDATE APPOINTMENTS SET SERVICE_STATUS = 'Done' WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //total appointments - alip
    public int getTotalAppointments() {
        String sql = "SELECT COUNT(*) AS TOTAL FROM APPOINTMENTS";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("TOTAL");
            }
        } catch (Exception e) {
            System.out.println("Error counting total appointments:");
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public String getPaymentIdByAppointmentId(String appointmentId) {
        String sql = "SELECT PAYMENT_ID FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("PAYMENT_ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // admin - alip
    public boolean updatePaymentStatus(String appointmentId, String paymentStatus) {
        String sql = "UPDATE APPOINTMENTS SET PAYMENT_STATUS = ? WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentStatus);
            stmt.setString(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


	/*
	 * public List<Appointment> getAllAppointments() { // TODO Auto-generated method
	 * stub return null; }
	 */
	
    // Get all appointments (used by admin/staff view) - alip
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
            SELECT a.*, s.STAFF_NAME AS BARBERNAME, c.CUST_NAME AS CUSTOMERNAME,
                   CASE 
                       WHEN cs.PAYMENT_ID IS NOT NULL THEN 'Cash'
                       WHEN op.PAYMENT_ID IS NOT NULL THEN 'Online Banking'
                       ELSE 'Unknown'
                   END AS PAYMENT_METHOD
            FROM APPOINTMENTS a
            LEFT JOIN STAFFS s ON a.STAFF_ID = s.STAFF_ID
            LEFT JOIN CUSTOMERS c ON a.CUST_ID = c.CUST_ID
            LEFT JOIN PAYMENTS p ON a.APPOINTMENT_ID = p.APPOINTMENT_ID
            LEFT JOIN CASHES cs ON p.PAYMENT_ID = cs.PAYMENT_ID
            LEFT JOIN ONLINE_PAYMENTS op ON p.PAYMENT_ID = op.PAYMENT_ID
            ORDER BY a.APPOINTMENT_ID DESC
			FETCH FIRST 15 ROWS ONLY
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentBarber(rs.getString("BARBERNAME"));
                a.setCustomerName(rs.getString("CUSTOMERNAME"));
                a.setAppointmentId(rs.getString("APPOINTMENT_ID"));
                a.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
                a.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
                a.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
                a.setCustType(rs.getString("CUST_TYPE"));
                a.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                a.setServiceStatus(rs.getString("SERVICE_STATUS"));
                a.setCustId(rs.getString("CUST_ID"));
                a.setStaffId(rs.getString("STAFF_ID"));
                a.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
                a.setPaymentMethod(rs.getString("PAYMENT_METHOD")); // ✅ PENTING
                appointments.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    // masukkan staff_id lepas update appointment
    public boolean updateAppointmentByAdmin(Appointment appointment, String staffId) {
        String sql = "UPDATE APPOINTMENTS SET APPOINTMENT_DATE = ?, APPOINTMENT_TIME = ?, BARBER_ID = ?, STAFF_ID = ? WHERE APPOINTMENT_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            stmt.setString(2, appointment.getAppointmentTime());
            stmt.setInt(3, appointment.getBarberId());
            stmt.setString(4, staffId); // simpan staff_id admin
            stmt.setString(5, appointment.getAppointmentId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Get all appointments by Barber ID
    public List<Appointment> getAllAppointmentByBarberId(int barberId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
            SELECT a.*, c.CUST_NAME, c.CUST_PICTURE
            FROM APPOINTMENTS a
            JOIN CUSTOMERS c ON a.CUST_ID = c.CUST_ID
            WHERE a.BARBER_ID = ?
            ORDER BY a.APPOINTMENT_DATE DESC
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, barberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getString("APPOINTMENT_ID"));
                appointment.setCustId(rs.getString("CUST_ID"));
                appointment.setCustomerName(rs.getString("CUST_NAME"));
                appointment.setCustPicture(rs.getString("CUST_PICTURE"));
                appointment.setAppointmentDate(rs.getDate("APPOINTMENT_DATE").toString());
                appointment.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
                appointment.setValueLoyalty(rs.getInt("VALUE_LOYALTY"));
                appointment.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                appointment.setServiceStatus(rs.getString("SERVICE_STATUS"));
                appointment.setCustBookFor(rs.getString("CUST_BOOK_FOR"));
                appointment.setCustType(rs.getString("CUST_TYPE"));
                appointment.setBarberId(rs.getInt("BARBER_ID"));
                appointments.add(appointment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    
}
