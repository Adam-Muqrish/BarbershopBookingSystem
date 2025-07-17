package com.hugi.barbershop.customer.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.hugi.barbershop.common.dao.AppointmentDAO;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.customer.model.Payment;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppointmentDAO appointmentDAO;
	private PaymentDAO paymentDAO;
	private CustomerDAO customerDAO;

	@Override
	public void init() {
		appointmentDAO = new AppointmentDAO();
		paymentDAO = new PaymentDAO();
		customerDAO = new CustomerDAO();
	}

	public PaymentController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String bookingKey = request.getParameter("bookingKey");
		String sessionBookingKey = (String) session.getAttribute("bookingKey");
		if (bookingKey == null || !bookingKey.equals(sessionBookingKey)) {
			response.sendRedirect("view-appointment");
			return;
		}

		String custId = (String) session.getAttribute("custId");
		boolean isFreeBooking = false;
		if (custId != null) {
			int paidCount = appointmentDAO.countLoyaltyAppointmentsByCustomerId(custId);
			if ((paidCount + 1) % 3 == 0) {
				isFreeBooking = true;
			}
		}
		session.setAttribute("isFreeBooking", isFreeBooking);
		request.setAttribute("isFreeBooking", isFreeBooking);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customer/payment.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String bookingKey = request.getParameter("bookingKey");
		String sessionBookingKey = (String) session.getAttribute("bookingKey");
		if (bookingKey == null || !bookingKey.equals(sessionBookingKey)) {
			response.sendRedirect("view-appointment");
			return;
		}

		String custId = (String) session.getAttribute("custId");
		String paymentMethod = request.getParameter("payment-method");
		String bankName = request.getParameter("bankName");
		String customerName = null;
		Object customerObj = session.getAttribute("customer");
		if (customerObj instanceof com.hugi.barbershop.customer.model.Customer customer) {
			customerName = customer.getCustName();
		}

		String bookingFor = (String) session.getAttribute("bookingFor");
		String bookingDate = (String) session.getAttribute("bookingDate");
		String selectedTime = (String) session.getAttribute("selectedTime");
		String category = (String) session.getAttribute("category");
		String selectedBarber = (String) session.getAttribute("selectedBarber");
		Object barberIdObj = session.getAttribute("barberId");
		String barberId = (barberIdObj != null) ? barberIdObj.toString() : null;

		Double price = (Double) session.getAttribute("price");
		if (price == null) price = 0.0;

		boolean isFreeBooking = false;
		if (custId != null) {
			int paidCount = appointmentDAO.countLoyaltyAppointmentsByCustomerId(custId);
			if ((paidCount + 1) % 3 == 0) {
				isFreeBooking = true;
			}
		}
		if (isFreeBooking) {
			price = 0.0;
			session.setAttribute("isFreeBooking", true);
		} else {
			session.setAttribute("isFreeBooking", false);
		}

		Appointment appointment = new Appointment();
		appointment.setCustBookFor(bookingFor);
		appointment.setAppointmentDate(bookingDate);
		appointment.setAppointmentTime(selectedTime);
		appointment.setCustType(category);
		appointment.setPaymentStatus(isFreeBooking ? "completed" : "pending");
		appointment.setServiceStatus("pending");
		appointment.setCustId(custId);
		try {
			if (barberId != null && !barberId.isEmpty()) {
				appointment.setBarberId(Integer.parseInt(barberId));
			}
		} catch (Exception e) {
			request.setAttribute("error", "Invalid barber ID. Please try again.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}
		appointment.setAppointmentBarber(selectedBarber);
		appointment.setValueLoyalty(1);

		String appointmentId = appointmentDAO.insertAppointment(appointment);
		if (appointmentId == null) {
			request.setAttribute("error", "Failed to create appointment.");
			request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
			return;
		}

		Payment payment;
		boolean shouldUpdateLoyalty = false;
		if (isFreeBooking) {
			payment = paymentDAO.insertPayment(0.0, appointmentId);
			if (payment == null) {
				request.setAttribute("error", "Failed to process payment.");
				request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
				return;
			}
			String paymentId = payment.getPaymentId();
			paymentDAO.insertCashPayment(paymentId, 0.0);
			appointmentDAO.updatePaymentStatus(appointmentId, "completed");
			shouldUpdateLoyalty = true;
			session.setAttribute("paymentId", paymentId);
			session.setAttribute("paymentMethod", "free");
			session.setAttribute("paymentStatus", "completed");
		} else {
			payment = paymentDAO.insertPayment(price, appointmentId);
			if (payment == null) {
				request.setAttribute("error", "Failed to process payment.");
				request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
				return;
			}
			String paymentId = payment.getPaymentId();
			if ("cash".equals(paymentMethod)) {
				paymentDAO.insertCashPayment(paymentId, price);
				appointmentDAO.updatePaymentStatus(appointmentId, "pending");
				shouldUpdateLoyalty = true;
			} else if ("online-banking".equals(paymentMethod)) {
				paymentDAO.insertOnlinePayment(paymentId, bankName, customerName != null ? customerName : "Unknown");
				appointmentDAO.updatePaymentStatus(appointmentId, "completed");
				shouldUpdateLoyalty = true;
			}
			session.setAttribute("paymentId", paymentId);
			session.setAttribute("paymentMethod", paymentMethod);
			session.setAttribute("paymentStatus", "online-banking".equals(paymentMethod) ? "completed" : "pending");
		}

		if (shouldUpdateLoyalty) {
			int loyaltyCount = appointmentDAO.countLoyaltyAppointmentsByCustomerId(custId);
			customerDAO.updateLoyaltyPoints(custId, loyaltyCount);
		}

		session.setAttribute("appointmentId", appointmentId);
		response.sendRedirect("receipt?appointmentId=" + appointmentId + "&bookingKey=" + bookingKey);
	}
}
