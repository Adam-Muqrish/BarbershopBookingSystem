package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.hugi.barbershop.common.dao.StaffDAO;
import com.hugi.barbershop.staff.model.Staff;

@WebServlet("/registerStaff")
@MultipartConfig
public class registerStaff extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public registerStaff() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Get form input
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");

        // ✅ Picture upload handling
		/*
		 * Part picturePart = request.getPart("picture"); String fileName =
		 * picturePart.getSubmittedFileName();
		 * 
		 * // ✅ Path untuk simpan gambar dalam resources/uploads/avatar String
		 * uploadPath = getServletContext().getRealPath("/") +
		 * "resources/uploads/avatar"; File uploadDir = new File(uploadPath); if
		 * (!uploadDir.exists()) uploadDir.mkdirs();
		 * 
		 * if (fileName != null && !fileName.isEmpty()) { picturePart.write(uploadPath +
		 * File.separator + fileName); } else { fileName = "default.jpg"; }
		 */

        // ✅ Validation
        if (email == null || password == null || confirmPassword == null || role == null ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty()) {
            request.getSession().setAttribute("registerFailed", true);
            request.getSession().setAttribute("errorMessage", "Please fill in all required fields.");
            response.sendRedirect("listBarber");
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.getSession().setAttribute("registerFailed", true);
            request.getSession().setAttribute("errorMessage", "Passwords do not match.");
            response.sendRedirect("listBarber");
            return;
        }

        StaffDAO staffDAO = new StaffDAO();
        if (staffDAO.emailExists(email)) {
            request.getSession().setAttribute("registerFailed", true);
            request.getSession().setAttribute("errorMessage", "Email already registered.");
            response.sendRedirect("listBarber");
            return;
        }
        

        //  Create staff object
        Staff staff = new Staff();
        staff.setStaffEmail(email);
        staff.setStaffPassword(password);
        staff.setStaffRole(role);
        
        

        //  Admin ID setup
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

        String generatedStaffId = staffDAO.insertStaff(staff);
        if (generatedStaffId != null) {
            request.getSession().setAttribute("registerSuccess", "New Staff Registered!");
        } else {
            request.getSession().setAttribute("registerFailed", true);
            request.getSession().setAttribute("errorMessage", "Failed to register staff.");
        }

        response.sendRedirect("listBarber");
        
        
    }
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
}
