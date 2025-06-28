package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.customer.model.Customer;

/**
 * Servlet implementation class viewCustomerDetails
 */
@WebServlet("/viewCustomerDetails")
public class viewCustomerDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewCustomerDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String custId = request.getParameter("custId");

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerById(custId);

        if (customer != null) {
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/WEB-INF/views/admin/custProfile-Admin.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
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
