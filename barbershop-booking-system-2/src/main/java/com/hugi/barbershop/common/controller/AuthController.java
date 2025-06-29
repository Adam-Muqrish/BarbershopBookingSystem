package com.hugi.barbershop.common.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.dao.StaffDAO;

@WebServlet(urlPatterns = {"/register", "/adminLogin", "/auth"})
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/register")) {
            if (request.getSession().getAttribute("customer") != null) {
                response.sendRedirect("index");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);

        } else if (path.equals("/adminLogin")) {
            request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp").forward(request, response);
        } else if (path.equals("/auth")) {
            // Show login page or redirect to register
            request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
        } else {
            // Optionally, redirect to a default page or show 404
            response.sendRedirect("register");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");

        if (path.equals("/auth")) {
            if ("login".equals(action)) {
                // === Login as Customer ===
                CustomerDAO customerDAO = new CustomerDAO();
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                Customer customer = customerDAO.getCustomerByEmail(email);

                if (customer != null && customerDAO.verifyPassword(customer, password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("customer", customer);
                    session.setAttribute("custId", customer.getCustId());
                    request.setAttribute("justLoggedIn", true);
                } else {
                    request.setAttribute("error", "Invalid email or password");
                }
                request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);

            } else if ("register".equals(action)) {
                // === Register as Customer ===
                CustomerDAO customerDAO = new CustomerDAO();
                String custName = request.getParameter("name");
                String custEmail = request.getParameter("email");
                String custPassword = request.getParameter("password");
                String custPhoneNumber = request.getParameter("phone");

                if (customerDAO.emailExists(custEmail)) {
                    request.setAttribute("error", "Email already registered.");
                } else {
                    String newCustId = customerDAO.getNextCustomerId();
                    Customer customer = new Customer();
                    customer.setCustId(newCustId);
                    customer.setCustName(custName);
                    customer.setCustEmail(custEmail);
                    customer.setCustPassword(custPassword);
                    customer.setCustPhoneNumber(custPhoneNumber);
                    customer.setLoyaltyPoints(0);

                    boolean success = customerDAO.insertCustomer(customer);
                    if (success) {
                        request.setAttribute("successMessage", "Registration successful! Redirecting to login...");
                    } else {
                        request.setAttribute("error", "Registration failed. Please try again.");
                    }
                }
                request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
            }
        } else if (path.equals("/adminLogin")) {
            // === Login as Staff ===
            if ("staffLogin".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                StaffDAO staffDAO = new StaffDAO();
                Staff staff = staffDAO.getStaffByEmail(email);

                if (staff != null && staffDAO.verifyPassword(staff, password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("staff", staff);
                    session.setAttribute("staffId", staff.getStaffId());
                    session.setAttribute("staffRole", staff.getStaffRole());

                    if ("Admin".equalsIgnoreCase(staff.getStaffRole())) {
                        session.setAttribute("loginSuccess", "Login Successfully!");
                        response.sendRedirect("adminIndex");
                    } else if ("Barber".equalsIgnoreCase(staff.getStaffRole())) {
                        response.sendRedirect("barberDashboard");
                    } else {
                        session.setAttribute("loginError", "Login Failed!");
                        response.sendRedirect("adminLogin");
                    }
                } else {
                    request.setAttribute("loginError", "Invalid email or password");
                    request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("adminLogin");
            }
        }
    }
}
