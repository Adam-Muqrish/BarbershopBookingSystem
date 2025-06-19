package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.common.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

	public Customer getCustomerById(String custId) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE CUSTID = ?")) {

			stmt.setString(1, custId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Customer customer = new Customer();
				customer.setCustId(rs.getString("CUSTID"));
				customer.setCustName(rs.getString("CUSTNAME"));
				customer.setCustEmail(rs.getString("CUSTEMAIL"));
				customer.setCustPhoneNumber(rs.getString("CUSTPHONENUMBER"));
				customer.setLoyaltyPoints(rs.getInt("LOYALTYPOINTS"));
				customer.setCustPassword(rs.getString("CUSTPASSWORD"));
				customer.setCustPicture(rs.getString("CUSTPICTURE"));
				return customer;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Customer getCustomerByEmail(String email) {
		String sql = "SELECT * FROM CUSTOMER WHERE CUSTEMAIL = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			System.out.println("Looking up email: " + email); // Debug line

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
			    Customer customer = new Customer();
			    customer.setCustId(rs.getString("CUSTID"));
			    customer.setCustName(rs.getString("CUSTNAME"));
			    customer.setCustEmail(rs.getString("CUSTEMAIL"));
			    customer.setCustPhoneNumber(rs.getString("CUSTPHONENUMBER"));
			    customer.setLoyaltyPoints(rs.getInt("LOYALTYPOINTS"));
			    customer.setCustPassword(rs.getString("CUSTPASSWORD"));
			    customer.setCustPicture(rs.getString("CUSTPICTURE"));
			    return customer;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


	public boolean verifyPassword(Customer customer, String inputPassword) {
		return customer.getCustPassword() != null &&
				customer.getCustPassword().equals(inputPassword);

	}

	public boolean updateCustomer(Customer customer) {
		String sql = "UPDATE CUSTOMER SET CUSTNAME = ?, CUSTEMAIL = ?, CUSTPHONENUMBER = ?, CUSTPASSWORD = ?, CUSTPICTURE = ? WHERE CUSTID = ?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, customer.getCustName());
			stmt.setString(2, customer.getCustEmail());
			stmt.setString(3, customer.getCustPhoneNumber());
			stmt.setString(4, customer.getCustPassword());
			stmt.setString(5, customer.getCustPicture());
			stmt.setString(6, customer.getCustId());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
    // Get all customers (for admin/staff view)
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getString("CUSTID"));
                customer.setCustName(rs.getString("CUSTNAME"));
                customer.setCustEmail(rs.getString("CUSTEMAIL"));
                customer.setCustPhoneNumber(rs.getString("CUSTPHONENUMBER"));
                customer.setLoyaltyPoints(rs.getInt("LOYALTYPOINTS"));
                customer.setCustPassword(rs.getString("CUSTPASSWORD"));
                customer.setCustPicture(rs.getString("CUSTPICTURE"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Delete customer by ID (optional)
    public boolean deleteCustomerById(String custId) {
        String sql = "DELETE FROM CUSTOMER WHERE CUSTID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, custId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if email exists (for registration)
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM CUSTOMER WHERE CUSTEMAIL = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}