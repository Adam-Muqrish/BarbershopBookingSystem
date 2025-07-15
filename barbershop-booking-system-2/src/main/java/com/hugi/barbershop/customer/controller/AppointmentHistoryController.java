package com.hugi.barbershop.customer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.common.dao.AppointmentDAO;

/**
 * Servlet implementation class AppointmentHistoryController
 */
@WebServlet("/appointment-history")
public class AppointmentHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public void init() {
		// appointmentDAO = new AppointmentDAO(); // No longer needed
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppointmentHistoryController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String custId = (String) request.getSession().getAttribute("custId");
		int page = 1;
		int pageSize = 4; // or any default size
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// Call the API to get all appointments for this customer
		String apiUrl = "http://localhost:8081/barbershop-customer-service/RetrieveCustomerAPIController?customerId=" + custId;
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		int status = conn.getResponseCode();
		List<Map<String, Object>> allAppointments = new ArrayList<>();
		if (status == 200) {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
				allAppointments = gson.fromJson(in, listType);
			}
		}
		conn.disconnect();

		// Pagination logic
		int totalAppointments = allAppointments.size();
		int totalPages = (int) Math.ceil((double) totalAppointments / pageSize);
		int offset = (page - 1) * pageSize;
		List<Map<String, Object>> pagedAppointments = new ArrayList<>();
		for (int i = offset; i < Math.min(offset + pageSize, totalAppointments); i++) {
			pagedAppointments.add(allAppointments.get(i));
		}

		// Convert to List<Appointment>
		List<Appointment> doneAppointments = new ArrayList<>();
		for (Map<String, Object> map : pagedAppointments) {
			Appointment appt = new Appointment();
			appt.setAppointmentId((String) map.get("appointmentId"));
			appt.setCustBookFor((String) map.get("custBookFor"));
			appt.setAppointmentDate((String) map.get("appointmentDate"));
			appt.setAppointmentTime((String) map.get("appointmentTime"));
			appt.setCustType((String) map.get("custType"));
			appt.setCustId((String) map.get("customerId"));
			appt.setBarberId((Integer) map.get("barberId"));
			appt.setAppointmentBarber((String) map.get("appointmentBarber"));
			appt.setServiceStatus((String) map.get("serviceStatus"));
			// Optionally set other fields if available in the map
			doneAppointments.add(appt);
		}

		request.setAttribute("doneAppointments", doneAppointments);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		if ("true".equals(request.getParameter("ajax"))) {
		    request.getRequestDispatcher("/WEB-INF/views/customer/appointment-history-list-partial.jsp").forward(request, response);
		} else {
		    request.getRequestDispatcher("/WEB-INF/views/customer/appointment-history.jsp").forward(request, response);
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
