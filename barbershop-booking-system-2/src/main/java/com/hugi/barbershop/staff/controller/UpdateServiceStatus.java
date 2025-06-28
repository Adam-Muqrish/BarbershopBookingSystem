package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hugi.barbershop.common.dao.AppointmentDAO;

/**
 * Servlet implementation class UpdateServiceStatus
 */
@WebServlet("/UpdateServiceStatus")
public class UpdateServiceStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServiceStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String appointmentId = request.getParameter("appointmentId");
		System.out.println("Received appointmentId: " + appointmentId);

        AppointmentDAO dao = new AppointmentDAO();
        boolean updated = dao.updateServiceStatusToDone(appointmentId);

        if (updated) {
            response.sendRedirect("listAppointment"); // ganti dengan JSP kau
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update status");
        }
	}

}
