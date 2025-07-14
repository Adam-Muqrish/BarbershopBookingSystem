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
 * Servlet implementation class adminProfile
 */
@WebServlet("/adminProfile")
public class adminProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StaffDAO staffDAO = new StaffDAO();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String staffId = (String) request.getSession().getAttribute("staffId");
        Staff staff = staffDAO.getStaffById(staffId);
        request.setAttribute("staff", staff);
        request.getRequestDispatcher("/WEB-INF/views/admin/adminProfile.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
