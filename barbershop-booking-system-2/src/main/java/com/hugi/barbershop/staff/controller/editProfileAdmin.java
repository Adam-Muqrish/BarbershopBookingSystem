package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.dao.StaffDAO;

@WebServlet("/editProfileAdmin")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
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

        // ✅ Handle File Upload
        Part filePart = request.getPart("staffPicture");
        String fileName = getFileName(filePart);
        String uploadPath = getServletContext().getRealPath("/") + "resources/uploads/avatar";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        if (fileName != null && !fileName.isEmpty()) {
            filePart.write(uploadPath + File.separator + fileName);
        } else {
            fileName = null; // kalau tak upload gambar
        }

        Staff staff = new Staff();
        staff.setStaffId(staffId);
        staff.setStaffName(staffName);
        staff.setStaffEmail(staffEmail);
        staff.setStaffPhoneNumber(staffPhone);
        staff.setDescription(description);
        staff.setStaffPicture(fileName); // simpan nama gambar je

        staffDAO.updateProfileStaff(staff);
        response.sendRedirect("adminProfile?success=true");
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return null;
    }
}
