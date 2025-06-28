package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.hugi.barbershop.staff.model.ViewBarber;
import com.hugi.barbershop.common.dao.StaffDAO;

/**
 * Servlet implementation class listBarber
 */
@WebServlet("/listBarber")
public class listBarber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listBarber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StaffDAO staffDAO = new StaffDAO();
        List<ViewBarber> barberList = staffDAO.getAllBarbers();
        request.setAttribute("barberList", barberList);
        request.getRequestDispatcher("/WEB-INF/views/admin/listBarber.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
