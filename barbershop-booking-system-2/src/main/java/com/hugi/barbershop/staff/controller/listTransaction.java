package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.staff.model.ViewTransaction;

/**
 * Servlet implementation class listTransaction
 */
@WebServlet("/listTransaction")
public class listTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listTransaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PaymentDAO paymentDAO = new PaymentDAO();
		List<ViewTransaction> transactionList = paymentDAO.getAllTransactions();
		request.setAttribute("transactionList", transactionList);
        request.getRequestDispatcher("/WEB-INF/views/admin/listTransaction.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
