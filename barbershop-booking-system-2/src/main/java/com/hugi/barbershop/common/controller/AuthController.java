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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);

        if (path.equals("/register")) {
            if (session != null && session.getAttribute("customer") != null) {
                response.sendRedirect("index");
                return;
            }
            request.setAttribute("pageTitle", "Customer Registration");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);

        } else if (path.equals("/adminLogin")) {
            if (session != null && session.getAttribute("loggedInStaff") != null) {
                response.sendRedirect("adminIndex");
                return;
            }
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp").forward(request, response);

        } else if (path.equals("/auth")) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);

        } else {
            response.sendRedirect("register");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");

        if (path.equals("/auth")) {
            if ("login".equals(action)) {
                handleCustomerLogin(request, response);
            } else if ("register".equals(action)) {
                handleCustomerRegister(request, response);
            } else {
                response.sendRedirect("register");
            }
        } else if (path.equals("/adminLogin")) {
            if ("staffLogin".equals(action)) {
                handleStaffLogin(request, response);
            } else {
                request.setAttribute("loginError", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("register");
        }
    }

    private void handleCustomerLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CustomerDAO customerDAO = new CustomerDAO();
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	Customer customer = customerDAO.getCustomerByEmail(email);

    	if (customer != null && customerDAO.verifyPassword(customer, password)) {
    		HttpSession session = request.getSession();
    		session.setAttribute("customer", customer);
    		session.setAttribute("custId", customer.getCustId());
    		request.setAttribute("justLoggedIn", true);
    		request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
    	}
    	else {
    		request.setAttribute("error", "Invalid email or password");
    		request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
    	}
    }

    private void handleCustomerRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        String custName = request.getParameter("name");
        String custEmail = request.getParameter("email");
        String custPassword = request.getParameter("password");
        String custPhoneNumber = request.getParameter("phone");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!custPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
            return;
        }

        if (customerDAO.emailExists(custEmail)) {
            request.setAttribute("error", "Email already registered.");
        } else {
            Customer customer = new Customer();
            customer.setCustName(custName);
            customer.setCustEmail(custEmail);
            customer.setCustPassword(custPassword);
            customer.setCustPhoneNumber(custPhoneNumber);
            customer.setCustLoyaltyPoints(0);

            String newCustId = customerDAO.insertCustomer(customer);
            if (newCustId != null) {
                customer.setCustId(newCustId);
                request.setAttribute("successMessage", "Registration successful! Redirecting to login...");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/customer/register.jsp").forward(request, response);
    }

    private void handleStaffLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByEmail(email);

        if (staff != null && staffDAO.verifyPassword(staff, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInStaff", staff);
            session.setAttribute("staff", staff);
            session.setAttribute("staffId", staff.getStaffId());
            session.setAttribute("staffRole", staff.getStaffRole());
            session.setAttribute("loginSuccess", "Login Successfully!");

            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            if ("Admin".equalsIgnoreCase(staff.getStaffRole()) || "Barber".equalsIgnoreCase(staff.getStaffRole())) {
                response.sendRedirect("adminIndex");
            } else {
                session.setAttribute("loginError", "Login Failed!");
                response.sendRedirect("adminLogin");
            }
        } else {
            request.setAttribute("loginError", "Invalid email or password");
            request.getRequestDispatcher("/WEB-INF/views/admin/adminLogin.jsp").forward(request, response);
        }
    }
}
