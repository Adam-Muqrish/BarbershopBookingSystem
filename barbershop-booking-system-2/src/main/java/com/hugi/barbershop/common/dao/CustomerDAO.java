package com.hugi.barbershop.common.dao;

import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.common.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Insert a new customer (CUST_ID is auto-incremented)
    public String insertCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS (CUST_NAME, CUST_EMAIL, CUST_PASSWORD, CUST_PHONE_NUMBER, CUST_PICTURE, CUST_LOYALTY_POINTS) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"CUST_ID"})) {

            stmt.setString(1, customer.getCustName());
            stmt.setString(2, customer.getCustEmail());
            stmt.setString(3, customer.getCustPassword());
            stmt.setString(4, customer.getCustPhoneNumber());
            stmt.setString(5, customer.getCustPicture());
            stmt.setInt(6, customer.getCustLoyaltyPoints());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getString(1); // Return generated CUST_ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get customer by ID
    public Customer getCustomerById(String custId) {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUST_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, custId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getString("CUST_ID"));
                customer.setCustName(rs.getString("CUST_NAME"));
                customer.setCustEmail(rs.getString("CUST_EMAIL"));
                customer.setCustPhoneNumber(rs.getString("CUST_PHONE_NUMBER"));
                customer.setCustLoyaltyPoints(rs.getInt("CUST_LOYALTY_POINTS"));
                customer.setCustPassword(rs.getString("CUST_PASSWORD"));
                customer.setCustPicture(rs.getString("CUST_PICTURE"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get customer by email
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUST_EMAIL = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getString("CUST_ID"));
                customer.setCustName(rs.getString("CUST_NAME"));
                customer.setCustEmail(rs.getString("CUST_EMAIL"));
                customer.setCustPhoneNumber(rs.getString("CUST_PHONE_NUMBER"));
                customer.setCustLoyaltyPoints(rs.getInt("CUST_LOYALTY_POINTS"));
                customer.setCustPassword(rs.getString("CUST_PASSWORD"));
                customer.setCustPicture(rs.getString("CUST_PICTURE"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Verify password
    public boolean verifyPassword(Customer customer, String inputPassword) {
        return customer.getCustPassword() != null &&
               customer.getCustPassword().equals(inputPassword);
    }

    // Update customer
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMERS SET CUST_NAME = ?, CUST_EMAIL = ?, CUST_PHONE_NUMBER = ?, CUST_PASSWORD = ?, CUST_PICTURE = ? WHERE CUST_ID = ?";
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

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getString("CUST_ID"));
                customer.setCustName(rs.getString("CUST_NAME"));
                customer.setCustEmail(rs.getString("CUST_EMAIL"));
                customer.setCustPhoneNumber(rs.getString("CUST_PHONE_NUMBER"));
                customer.setCustLoyaltyPoints(rs.getInt("CUST_LOYALTY_POINTS"));
                customer.setCustPassword(rs.getString("CUST_PASSWORD"));
                customer.setCustPicture(rs.getString("CUST_PICTURE"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Delete customer by ID
    public boolean deleteCustomerById(String custId) {
        String sql = "DELETE FROM CUSTOMERS WHERE CUST_ID = ?";
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

    // Check if email exists
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM CUSTOMERS WHERE CUST_EMAIL = ?";
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
    
    // Update loyalty points, capped at 10
    public boolean updateLoyaltyPoints(String custId, int points) {
        int cappedPoints = Math.min(points, 10);
        String sql = "UPDATE CUSTOMERS SET CUST_LOYALTY_POINTS = ? WHERE CUST_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cappedPoints);
            stmt.setString(2, custId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getCustomerCount() {
        String sql = "SELECT COUNT(*) AS TOTAL FROM CUSTOMERS";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("TOTAL");
            }

        } catch (Exception e) {
            System.out.println("Error counting customers:");
            e.printStackTrace();
        }

        return 0;
    }

}
