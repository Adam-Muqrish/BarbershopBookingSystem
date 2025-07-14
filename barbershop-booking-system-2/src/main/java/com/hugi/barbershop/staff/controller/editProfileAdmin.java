package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.dao.StaffDAO;

/**
 * Servlet implementation class editProfileAdmin
 */
@WebServlet("/editProfileAdmin")
public class editProfileAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO = new StaffDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffId = (String) request.getSession().getAttribute("staffId");

        Staff staff = staffDAO.getStaffById(staffId);
        request.setAttribute("staff", staff);
        request.getRequestDispatcher("/WEB-INF/views/admin/editAdminProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffId = request.getParameter("staffId");
        String staffName = request.getParameter("staffName");
        String staffEmail = request.getParameter("staffEmail");
        String staffPhone = request.getParameter("staffPhoneNumber");
        String description = request.getParameter("description");

        Staff staff = new Staff();
        staff.setStaffId(staffId);
        staff.setStaffName(staffName);
        staff.setStaffEmail(staffEmail);
        staff.setStaffPhoneNumber(staffPhone);
        staff.setDescription(description);

        staffDAO.updateProfileStaff(staff);

        response.sendRedirect("adminProfile?success=true");
    }
}


