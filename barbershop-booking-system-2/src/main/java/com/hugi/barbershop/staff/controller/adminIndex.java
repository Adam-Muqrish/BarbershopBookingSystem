package com.hugi.barbershop.staff.controller;

import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.common.dao.AppointmentDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.Map;
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
		AppointmentDAO appointmentDAO = new AppointmentDAO();
		
        List<Customer> customerList = customerDAO.getAllCustomers(); // Dapat semua customer dari database
        int customerCount = customerDAO.getCustomerCount();
        int totalAppointments = appointmentDAO.getTotalAppointments();

        request.setAttribute("customerList", customerList); // Hantar ke JSP
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("totalAppointments", totalAppointments);
        
     // Dapatkan total sales
        PaymentDAO paymentDAO = new PaymentDAO();
        double totalSales = paymentDAO.getTotalSales();
        request.setAttribute("totalSales", totalSales); // Hantar ke JSP
        
        Map<String, Double> salesByDay = paymentDAO.getSalesByDayOfWeek();
        request.setAttribute("salesByDay", salesByDay);

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
