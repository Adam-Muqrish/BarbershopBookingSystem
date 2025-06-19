package com.hugi.barbershop.common.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.common.dao.StaffDAO;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * private CustomerDAO customerDAO;
	 * 
	 * @Override public void init() { customerDAO = new CustomerDAO(); // no
	 * ServletContext needed }
	 */

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		// Display login page
		request.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userType = request.getParameter("userType");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if ("staff".equals(userType)) {
			StaffDAO staffDAO = new StaffDAO();
			Staff staff = staffDAO.getStaffByEmail(email);
			if (staff != null && staffDAO.verifyPassword(staff, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("staff", staff);
				session.setAttribute("staffId", staff.getStaffId());
				// Redirect based on staff role
				if ("Admin".equalsIgnoreCase(staff.getStaffRole())) {
					response.sendRedirect("view-list-customer");
				} else {
					response.sendRedirect("dashboard");
				}
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Invalid email or password");
				response.sendRedirect("login");
			}
		} else {
			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.getCustomerByEmail(email);
			if (customer != null && customerDAO.verifyPassword(customer, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("customer", customer);
				session.setAttribute("custId", customer.getCustId());
				response.sendRedirect("index");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Invalid email or password");
				response.sendRedirect("login");
			}
		}
	}
}