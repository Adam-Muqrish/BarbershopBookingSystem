package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.common.util.DBUtil;
import java.time.format.DateTimeFormatter;
import com.hugi.barbershop.customer.model.Payment;
import java.util.ArrayList;
import java.util.List;
import com.hugi.barbershop.customer.model.Cash;
import com.hugi.barbershop.customer.model.OnlinePayment;
import java.util.Map;
import java.util.HashMap;

import java.sql.*;
import java.time.LocalDate;

public class PaymentDAO {

	// Insert payment and return Payment object (PAYMENT_ID is auto-incremented)
	public Payment insertPayment(double amount, String appointmentId) {
		if (!appointmentExists(appointmentId)) {
			System.out.println("Cannot insert payment: Appointment ID " + appointmentId + " does not exist");
			return null;
		}

		String sql = "INSERT INTO PAYMENTS (PAYMENT_DATE, PAYMENT_AMOUNT, APPOINTMENT_ID) VALUES (?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"PAYMENT_ID"})) {
			stmt.setDate(1, Date.valueOf(LocalDate.now()));
			stmt.setDouble(2, amount);
			stmt.setInt(3, Integer.parseInt(appointmentId));

			if (stmt.executeUpdate() > 0) {
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						Payment payment = new Payment();
						payment.setPaymentId(String.valueOf(rs.getInt(1)));
						payment.setPaymentDate(LocalDate.now());
						payment.setPaymentAmount(amount);
						payment.setAppointmentId(appointmentId);
						return payment;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error inserting payment:");
			e.printStackTrace();
		}
		return null;
	}

	// Insert online payment and return OnlinePayment object
	public OnlinePayment insertOnlinePayment(String paymentId, String bankName, String bankHolderName) {
		String sql = "INSERT INTO ONLINE_PAYMENTS (PAYMENT_ID, BANK_NAME, BANK_HOLDER_NAME) VALUES (?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(paymentId));
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
		String sql = "INSERT INTO CASHES (PAYMENT_ID, CASH_RECEIVE) VALUES (?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(paymentId));
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
		String sql = "SELECT * FROM PAYMENTS WHERE PAYMENT_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(paymentId));
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Payment payment = new Payment();
					payment.setPaymentId(String.valueOf(rs.getInt("PAYMENT_ID")));
					payment.setPaymentDate(rs.getDate("PAYMENT_DATE").toLocalDate());
					payment.setPaymentAmount(rs.getDouble("PAYMENT_AMOUNT"));
					payment.setAppointmentId(String.valueOf(rs.getInt("APPOINTMENT_ID")));
					payment.setPaymentMethod(getPaymentMethod(paymentId));
					return payment;
				}
			}
		} catch (Exception e) {
			System.out.println("Error fetching payment by ID:");
			e.printStackTrace();
		}
		return null;
	}

	// Fetch payment by appointment ID
	public Payment getPaymentByAppointmentId(String appointmentId) {
		String sql = "SELECT * FROM PAYMENTS WHERE APPOINTMENT_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(appointmentId));
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Payment payment = new Payment();
					payment.setPaymentId(String.valueOf(rs.getInt("PAYMENT_ID")));
					payment.setPaymentDate(rs.getDate("PAYMENT_DATE").toLocalDate());
					payment.setPaymentAmount(rs.getDouble("PAYMENT_AMOUNT"));
					payment.setAppointmentId(String.valueOf(rs.getInt("APPOINTMENT_ID")));
					payment.setPaymentMethod(getPaymentMethod(payment.getPaymentId()));
					return payment;
				}
			}
		} catch (Exception e) {
			System.out.println("Error fetching payment by appointment ID:");
			e.printStackTrace();
		}
		return null;
	}

	// Helper: check if appointment exists
	private boolean appointmentExists(String appointmentId) {
		String sql = "SELECT 1 FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(appointmentId));
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			System.out.println("Error checking appointment existence:");
			e.printStackTrace();
			return false;
		}
	}

	// Helper: get payment method
	public String getPaymentMethod(String paymentId) {
		if (isCashPayment(paymentId)) {
			return "Cash";
		} else if (isOnlinePayment(paymentId)) {
			return "Online Banking";
		} else {
			return "unknown";
		}
	}

	private boolean isCashPayment(String paymentId) {
		String sql = "SELECT 1 FROM CASHES WHERE PAYMENT_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(paymentId));
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			System.out.println("Error checking cash payment:");
			e.printStackTrace();
			return false;
		}
	}

	private boolean isOnlinePayment(String paymentId) {
		String sql = "SELECT 1 FROM ONLINE_PAYMENTS WHERE PAYMENT_ID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Integer.parseInt(paymentId));
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			System.out.println("Error checking online payment:");
			e.printStackTrace();
			return false;
		}
	}
	
	//View Transaction - Admin Part - alip
	  public List<Payment> getAllPayments() {
	      List<Payment> transactions = new ArrayList<>();

	      String sql = """
	          SELECT c.CUST_PICTURE, c.CUST_NAME, c.CUST_EMAIL, 
			       p.PAYMENT_ID, p.PAYMENT_DATE, p.PAYMENT_AMOUNT,
			       CASE 
			           WHEN op.PAYMENT_ID IS NOT NULL THEN 'Online Payment'
			           WHEN cs.PAYMENT_ID IS NOT NULL THEN 'Cash'
			           ELSE 'Unknown'
			       END AS PAYMENT_TYPE
			FROM PAYMENTS p
			JOIN APPOINTMENTS a ON p.APPOINTMENT_ID = a.APPOINTMENT_ID
			JOIN CUSTOMERS c ON a.CUST_ID = c.CUST_ID
			LEFT JOIN ONLINE_PAYMENTS op ON p.PAYMENT_ID = op.PAYMENT_ID
			LEFT JOIN CASHES cs ON p.PAYMENT_ID = cs.PAYMENT_ID
			ORDER BY p.PAYMENT_ID DESC
			FETCH FIRST 10 ROWS ONLY
	      """;

	      try (Connection conn = DBUtil.getConnection();
	           PreparedStatement stmt = conn.prepareStatement(sql);
	           ResultSet rs = stmt.executeQuery()) {

	          while (rs.next()) {
	            Payment tx = new Payment();
		            tx.setCustomerPicture(rs.getString("CUST_PICTURE"));
		            tx.setCustomerName(rs.getString("CUST_NAME"));
		            tx.setCustomerEmail(rs.getString("CUST_EMAIL"));
		            tx.setPaymentId(rs.getString("PAYMENT_ID"));
		            tx.setFormattedDate(rs.getDate("PAYMENT_DATE").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		            tx.setPaymentAmount(rs.getDouble("PAYMENT_AMOUNT"));
		            tx.setPaymentMethod(rs.getString("PAYMENT_TYPE"));
		            transactions.add(tx);
	          }

	      } catch (Exception e) {
	          System.out.println("Error fetching transaction list:");
	          e.printStackTrace();
	      }

	      return transactions;
	  }
	  
	  //total sales admin  - alip
	  public double getTotalSales() {
		    String sql = "SELECT SUM(PAYMENT_AMOUNT) AS TOTAL_SALES FROM PAYMENTS";
		    try (Connection conn = DBUtil.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {

		        if (rs.next()) {
		            return rs.getDouble("TOTAL_SALES");
		        }
		    } catch (Exception e) {
		        System.out.println("Error getting total sales:");
		        e.printStackTrace();
		    }
		    return 0.0;
		}
	  
	  // graf admin part alip
	  public Map<String, Double> getSalesByDayOfWeek() {
		    Map<String, Double> salesByDay = new HashMap<>();
		    String sql = "SELECT TO_CHAR(PAYMENT_DATE, 'DAY') AS DAY_OF_WEEK, SUM(PAYMENT_AMOUNT) AS TOTAL_SALES " +
		                 "FROM PAYMENTS GROUP BY TO_CHAR(PAYMENT_DATE, 'DAY')";

		    try (Connection conn = DBUtil.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {

		        while (rs.next()) {
		            String dayOfWeek = rs.getString("DAY_OF_WEEK").trim();
		            double totalSales = rs.getDouble("TOTAL_SALES");
		            salesByDay.put(dayOfWeek, totalSales);
		        }
		    } catch (Exception e) {
		        System.out.println("Error getting sales by day of week:");
		        e.printStackTrace();
		    }
		    return salesByDay;
		}


}