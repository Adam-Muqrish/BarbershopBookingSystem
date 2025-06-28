package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.common.util.DBUtil;
import com.hugi.barbershop.customer.model.Payment;
import com.hugi.barbershop.customer.model.Cash;
import com.hugi.barbershop.customer.model.OnlinePayment;

import java.sql.*;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
import com.hugi.barbershop.staff.model.ViewTransaction;


public class PaymentDAO {

	// Insert payment and return Payment object
	public Payment insertPayment(double amount, String appointmentId) {
		String paymentId = generatePaymentId();

		if (!appointmentExists(appointmentId)) {
			System.out.println("Cannot insert payment: Appointment ID " + appointmentId + " does not exist");
			return null;
		}

		String sql = "INSERT INTO PAYMENT (PAYMENTID, PAYMENTDATE, PAYMENTAMOUNT, APPOINTMENTID) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			stmt.setDate(2, Date.valueOf(LocalDate.now()));
			stmt.setDouble(3, amount);
			stmt.setString(4, appointmentId);

			if (stmt.executeUpdate() > 0) {
				Payment payment = new Payment();
				payment.setPaymentId(paymentId);
				payment.setPaymentDate(LocalDate.now());
				payment.setPaymentAmount(amount);
				payment.setAppointmentId(appointmentId);
				return payment;
			}
		} catch (Exception e) {
			System.out.println("Error inserting payment:");
			e.printStackTrace();
		}
		return null;
	}

	// Insert online payment and return OnlinePayment object
	public OnlinePayment insertOnlinePayment(String paymentId, String bankName, String bankHolderName) {
		String sql = "INSERT INTO ONLINEPAYMENT (PAYMENTID, BANKNAME, BANKHOLDERNAME) VALUES (?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			stmt.setString(2, bankName);
			stmt.setString(3, bankHolderName);

			if (stmt.executeUpdate() > 0) {
				OnlinePayment onlinePayment = new OnlinePayment();
				onlinePayment.setPaymentId(paymentId);
				onlinePayment.setBankName(bankName);
				onlinePayment.setBankHolderName(bankHolderName);
				return onlinePayment;
			}
		} catch (Exception e) {
			System.out.println("Error inserting online payment:");
			e.printStackTrace();
		}
		return null;
	}

	// Insert cash payment and return Cash object
	public Cash insertCashPayment(String paymentId, double cashReceived) {
		String sql = "INSERT INTO CASH (PAYMENTID, CASHRECEIVE) VALUES (?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			stmt.setDouble(2, cashReceived);

			if (stmt.executeUpdate() > 0) {
				Cash cash = new Cash();
				cash.setPaymentId(paymentId);
				cash.setCashReceive(cashReceived);
				return cash;
			}
		} catch (Exception e) {
			System.out.println("Error inserting cash payment:");
			e.printStackTrace();
		}
		return null;
	}

	// Fetch payment by ID
	public Payment getPaymentById(String paymentId) {
		String sql = "SELECT * FROM PAYMENT WHERE PAYMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Payment payment = new Payment();
				payment.setPaymentId(rs.getString("PAYMENTID"));
				payment.setPaymentDate(rs.getDate("PAYMENTDATE").toLocalDate());
				payment.setPaymentAmount(rs.getDouble("PAYMENTAMOUNT"));
				payment.setAppointmentId(rs.getString("APPOINTMENTID"));
				payment.setPaymentMethod(rs.getString("PAYMENTMETHOD"));
				return payment;
			}
		} catch (Exception e) {
			System.out.println("Error fetching payment by ID:");
			e.printStackTrace();
		}
		return null;
	}

	// Helper: check if appointment exists
	private boolean appointmentExists(String appointmentId) {
		String sql = "SELECT 1 FROM APPOINTMENTS WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, appointmentId);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			System.out.println("Error checking appointment existence:");
			e.printStackTrace();
			return false;
		}
	}

	// Helper: generate unique payment ID
	private String generatePaymentId() {
		String sql = "SELECT 'PAY' || LPAD(NVL(MAX(TO_NUMBER(SUBSTR(PAYMENTID, 4))), 0) + 1, 3, '0') AS NEWID FROM PAYMENT";
		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getString("NEWID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PAY" + System.currentTimeMillis();
	}

	public Payment getPaymentByAppointmentId(String appointmentId) {
		String sql = "SELECT * FROM PAYMENT WHERE APPOINTMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, appointmentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Payment payment = new Payment();
				payment.setPaymentId(rs.getString("PAYMENTID"));
				payment.setPaymentDate(rs.getDate("PAYMENTDATE").toLocalDate());
				payment.setPaymentAmount(rs.getDouble("PAYMENTAMOUNT"));
				payment.setAppointmentId(rs.getString("APPOINTMENTID"));
				// Set payment method as in getPaymentById
				if (isCashPayment(payment.getPaymentId())) {
					payment.setPaymentMethod("cash");
				} else if (isOnlinePayment(payment.getPaymentId())) {
					payment.setPaymentMethod("online-banking");
				} else {
					payment.setPaymentMethod("unknown");
				}
				return payment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private boolean isCashPayment(String paymentId) {
		String sql = "SELECT 1 FROM CASH WHERE PAYMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean isOnlinePayment(String paymentId) {
		String sql = "SELECT 1 FROM ONLINEPAYMENT WHERE PAYMENTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, paymentId);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//View Transaction - Admin Part
	public List<ViewTransaction> getAllTransactions() {
	    List<ViewTransaction> transactions = new ArrayList<>();

	    String sql = """
	        SELECT c.CUSTPICTURE, c.CUSTNAME, c.CUSTEMAIL, 
	               p.PAYMENTID, p.PAYMENTDATE, p.PAYMENTAMOUNT,
	               CASE 
	                   WHEN op.PAYMENTID IS NOT NULL THEN 'Online Payment'
	                   WHEN cs.PAYMENTID IS NOT NULL THEN 'Cash'
	                   ELSE 'Unknown'
	               END AS PAYMENTTYPE
	        FROM PAYMENT p
	        JOIN APPOINTMENTS a ON p.APPOINTMENTID = a.APPOINTMENTID
	        JOIN CUSTOMER c ON a.CUSTID = c.CUSTID
	        LEFT JOIN ONLINEPAYMENT op ON p.PAYMENTID = op.PAYMENTID
	        LEFT JOIN CASH cs ON p.PAYMENTID = cs.PAYMENTID
	        ORDER BY p.PAYMENTDATE DESC
	        FETCH FIRST 10 ROWS ONLY
	    """;

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	        	ViewTransaction tx = new ViewTransaction();
	            tx.setCustomerPicture(rs.getString("CUSTPICTURE"));
	            tx.setCustomerName(rs.getString("CUSTNAME"));
	            tx.setCustomerEmail(rs.getString("CUSTEMAIL"));
	            tx.setPaymentId(rs.getString("PAYMENTID"));
	            tx.setPaymentDate(rs.getDate("PAYMENTDATE").toLocalDate());
	            tx.setPaymentAmount(rs.getDouble("PAYMENTAMOUNT"));
	            tx.setPaymentType(rs.getString("PAYMENTTYPE"));
	            transactions.add(tx);
	        }

	    } catch (Exception e) {
	        System.out.println("Error fetching transaction list:");
	        e.printStackTrace();
	    }

	    return transactions;
	}


}
