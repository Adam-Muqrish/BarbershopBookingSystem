package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

import com.hugi.barbershop.common.dao.StaffDAO;
import com.hugi.barbershop.staff.model.Staff;

/**
 * Servlet implementation class registerStaff
 */
@WebServlet("/registerStaff")
public class registerStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String confirmPassword = request.getParameter("confirmPassword");
	    String role = request.getParameter("role");

	    if (email == null || password == null || confirmPassword == null || role == null ||
	        email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty()) {
	        request.setAttribute("error", "Please fill in all fields.");
	        request.getRequestDispatcher("/WEB-INF/views/admin/listBarber.jsp").forward(request, response);
	        return;
	    }
	    
	    if (!password.equals(confirmPassword)) {
	        request.getSession().setAttribute("registerFailed", true); // ✅ Guna session bukan request
	        request.getSession().setAttribute("errorMessage", "Passwords do not match."); // Tambah mesej
	        response.sendRedirect("listBarber"); // ✅ Redirect supaya session attribute lekat
	        return;
	    }
	    
	    // ✅ Check email exists
	    StaffDAO staffDAO = new StaffDAO();
	    if (staffDAO.emailExists(email)) {
	        request.getSession().setAttribute("registerFailed", true);
	        request.getSession().setAttribute("errorMessage", "Email already registered.");
	        response.sendRedirect("listBarber");
	        return;
	    }

	    Staff staff = new Staff();
	    staff.setStaffId("SF" + UUID.randomUUID().toString().substring(0, 6));
	    staff.setStaffEmail(email);
	    staff.setStaffPassword(password);
	    staff.setStaffRole(role);

	    // Set other fields as default/empty
	    staff.setStaffName("");
	    staff.setStaffPhoneNumber("");
	    staff.setDescription("");
	    staff.setStaffPicture(null);

	    // Set adminId from session (if admin is logged in)
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        Staff currentAdmin = (Staff) session.getAttribute("loggedInStaff");
	        if (currentAdmin != null && "Admin".equalsIgnoreCase(currentAdmin.getStaffRole())) {
	            staff.setAdminId(currentAdmin.getStaffId());
	        } else {
	            staff.setAdminId(null);
	        }
	    } else {
	        staff.setAdminId(null);
	    }

	    
	    boolean success = staffDAO.insertStaff(staff);

	    if (success) {
	        request.getSession().setAttribute("registerSuccess", true);
	        response.sendRedirect("listBarber");
	    } else {
	        request.getSession().setAttribute("registerFailed", true);
	        response.sendRedirect("listBarber");
	    }
	}
}
