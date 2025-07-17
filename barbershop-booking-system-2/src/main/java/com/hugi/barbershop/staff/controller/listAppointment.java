package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.hugi.barbershop.common.dao.AppointmentDAO;
import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.customer.model.Payment;

/**
 * Servlet implementation class listAppointment
 */
@WebServlet("/listAppointment")
public class listAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listAppointment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AppointmentDAO dao = new AppointmentDAO();
		PaymentDAO paymentDAO = new PaymentDAO();
		
		HttpSession session = request.getSession();
		String role = (String) request.getSession().getAttribute("staffRole");
        List<Appointment> appointments = dao.getAllAppointments();
        List<Payment> transactions = paymentDAO.getAllPayments();
        
        if ("barber".equalsIgnoreCase(role)) {
        	int barberId = Integer.parseInt((String) session.getAttribute("staffId"));
			appointments = dao.getAllAppointmentByBarberId(barberId);
		} else {
			appointments = dao.getAllAppointments();
		}
        
     		for (Appointment a : appointments) {
     		    for (Payment p : transactions) {
     		        if (p.getAppointmentId().equals(a.getAppointmentId())) {
     		            a.setPaymentMethod(p.getPaymentMethod());
     		            a.setPaymentStatus(p.getPaymentStatus());
     		            break;
     		        }
     		    }
     		}
        
        request.setAttribute("transactions", transactions);
        request.setAttribute("appointmentList", appointments);
        request.getRequestDispatcher("/WEB-INF/views/admin/listAppointment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
