package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.staff.model.Staff;

/**
 * Servlet implementation class viewAdminProfile
 */
@WebServlet("/viewAdminProfile")
public class viewAdminProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewAdminProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Staff staff = (Staff) request.getSession().getAttribute("staff");

        if (staff != null) {
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("/WEB-INF/views/admin/adminProfile.jsp").forward(request, response);
        } else {
            response.sendRedirect("adminLogin");  // Kalau tak login redirect
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
