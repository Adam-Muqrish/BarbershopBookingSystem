package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.common.dao.AppointmentDAO;

/**
 * Servlet implementation class UpdatePaymentStatus
 */
@WebServlet("/UpdatePaymentStatus")
public class UpdatePaymentStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdatePaymentStatus() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        // Admin tekan button → terus update ke 'Completed'
        String paymentStatus = "Completed";

        boolean updated = appointmentDAO.updatePaymentStatus(appointmentId, paymentStatus);

        if (updated) {
        	response.sendRedirect("listAppointment?success=paymentUpdated");
        } else {
            response.sendRedirect("listAppointment.jsp?error=paymentFailed");
        }
    }
}
