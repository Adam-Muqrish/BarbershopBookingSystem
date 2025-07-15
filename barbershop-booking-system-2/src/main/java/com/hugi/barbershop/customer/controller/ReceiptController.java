package com.hugi.barbershop.customer.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.common.dao.AppointmentDAO;
import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.customer.model.Payment;
import com.hugi.barbershop.customer.model.Appointment;

@WebServlet("/receipt")
public class ReceiptController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO;
	private AppointmentDAO appointmentDAO;
	private PaymentDAO paymentDAO;

	@Override
	public void init() {
		customerDAO = new CustomerDAO();
		appointmentDAO = new AppointmentDAO();
		paymentDAO = new PaymentDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Only validate bookingKey if present in the request (i.e., user just finished booking)
		String bookingKey = request.getParameter("bookingKey");
		if (bookingKey != null) {
			String sessionBookingKey = (String) request.getSession().getAttribute("bookingKey");
			if (!bookingKey.equals(sessionBookingKey)) {
				response.sendRedirect("view-appointment");
				return;
			}
			// Optionally remove bookingKey after use
			request.getSession().removeAttribute("bookingKey");
		}

		String appointmentId = request.getParameter("appointmentId");
		if (appointmentId == null) {
			request.setAttribute("error", "Appointment ID is missing.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}

		Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
		if (appointment == null) {
			request.setAttribute("error", "Appointment not found.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}

		Customer customer = customerDAO.getCustomerById(appointment.getCustId());
		request.setAttribute("appointment", appointment);
		request.setAttribute("customer", customer);

		Payment payment = paymentDAO.getPaymentByAppointmentId(appointmentId);
		if (payment == null) {
			request.setAttribute("error", "Payment not found.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}
		request.setAttribute("payment", payment);

		// Use barberId instead of staffId for the assigned barber
		String barberName = appointmentDAO.getBarberNameById(appointment.getBarberId());
		request.setAttribute("barberName", barberName);

		if (barberName == null) {
			request.setAttribute("error", "Barber name not found.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customer/receipt.jsp");
		dispatcher.forward(request, response);
	}
}
