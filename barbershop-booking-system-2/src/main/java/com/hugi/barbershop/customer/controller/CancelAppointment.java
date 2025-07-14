package com.hugi.barbershop.customer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.common.dao.AppointmentDAO;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.customer.model.Appointment;

@WebServlet("/cancel-appointment")
public class CancelAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppointmentDAO appointmentDAO;
	private CustomerDAO customerDAO;

	@Override
	public void init() {
		appointmentDAO = new AppointmentDAO();
		customerDAO = new CustomerDAO();
	}

	public CancelAppointment() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentId = request.getParameter("appointmentId");
		if (appointmentId != null) {
			// Mark appointment as cancelled
			appointmentDAO.markAsCancelled(appointmentId);

            // Get the appointment to retrieve the customer ID
            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            if (appointment != null) {
                String custId = appointment.getCustId();
                // Only update loyalty points if the cancelled appointment was previously completed
                if ("completed".equalsIgnoreCase(appointment.getPaymentStatus())) {
                	int loyaltyCount = appointmentDAO.countLoyaltyAppointmentsByCustomerId(custId);
                	customerDAO.updateLoyaltyPoints(custId, loyaltyCount);
                }
            }
		}
		response.sendRedirect("appointment-history");
	}
}
