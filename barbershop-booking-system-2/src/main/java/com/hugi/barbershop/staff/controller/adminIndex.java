package com.hugi.barbershop.staff.controller;

import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.customer.model.Customer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class adminIndex
 */
@WebServlet("/adminIndex")
public class adminIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.getAllCustomers(); // Dapat semua customer dari database

        request.setAttribute("customerList", customerList); // Hantar ke JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminIndex.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
