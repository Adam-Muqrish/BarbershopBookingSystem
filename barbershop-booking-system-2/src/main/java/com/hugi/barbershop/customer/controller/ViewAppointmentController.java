package com.hugi.barbershop.customer.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.common.dao.PaymentDAO;
import com.hugi.barbershop.common.dao.AppointmentDAO;

@WebServlet("/view-appointment")
public class ViewAppointmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    private PaymentDAO paymentDAO;
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
        paymentDAO = new PaymentDAO();
        appointmentDAO = new AppointmentDAO();
    }

    public ViewAppointmentController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String custId = (String) request.getSession().getAttribute("custId");
        Customer customer = customerDAO.getCustomerById(custId);

        if (customer == null) {
            request.setAttribute("error", "Customer data not found");
            request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
            return;
        }

        // Pagination logic
        int page = 1;
        int pageSize = 4;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Call the API to get appointments
        List<Appointment> allAppointments = new ArrayList<>();
        try {
            // Change the URL to your actual customer service host/port if needed
            String apiUrl = "http://localhost:8081/barbershop-customer-service/RetrieveCustomerAPIController?customerId=" + custId;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseStr = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    responseStr.append(line);
                }
                in.close();
                // Parse JSON to List<Appointment>
                Gson gson = new Gson();
                allAppointments = gson.fromJson(responseStr.toString(), new TypeToken<List<Appointment>>(){}.getType());
            }
            conn.disconnect();
        } catch (Exception e) {
            // Optionally log error
            allAppointments = new ArrayList<>();
        }

        // Filter out appointments: paymentStatus == "completed" and serviceStatus != "Cancelled" and serviceStatus != "Done"
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appt : allAppointments) {
            boolean paymentCompletedOrPending = "completed".equalsIgnoreCase(appt.getPaymentStatus()) || "pending".equalsIgnoreCase(appt.getPaymentStatus());
            boolean serviceNotCancelled = !"Cancelled".equalsIgnoreCase(appt.getServiceStatus());
            boolean serviceNotDone = !"Done".equalsIgnoreCase(appt.getServiceStatus());
            if (paymentCompletedOrPending && serviceNotCancelled && serviceNotDone) {
                // Set payment method for each appointment
                String paymentMethod = null;
                try {
                    com.hugi.barbershop.customer.model.Payment payment = paymentDAO.getPaymentByAppointmentId(appt.getAppointmentId());
                    if (payment != null) {
                        paymentMethod = payment.getPaymentMethod();
                    }
                } catch (Exception ex) {
                    paymentMethod = null;
                }
                if (paymentMethod == null || paymentMethod.equalsIgnoreCase("unknown")) {
                    paymentMethod = "Unknown";
                }
                appt.setPaymentMethod(paymentMethod);
                // Set barber name for each appointment
                String barberName = null;
                try {
                    barberName = appointmentDAO.getBarberNameById(appt.getBarberId());
                } catch (Exception ex) {
                    barberName = null;
                }
                if (barberName == null || barberName.isEmpty()) {
                    barberName = "Unknown";
                }
                appt.setAppointmentBarber(barberName);
                filteredAppointments.add(appt);
            }
        }

        int totalAppointments = filteredAppointments.size();
        int totalPages = (int) Math.ceil((double) totalAppointments / pageSize);

        // Clamp page to valid range
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalAppointments);
        List<Appointment> appointments = (fromIndex < toIndex) ? filteredAppointments.subList(fromIndex, toIndex) : new ArrayList<>();

        request.setAttribute("customer", customer);
        request.setAttribute("appointments", appointments);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getSession().removeAttribute("bookingKey");

        String isAjaxRequest = request.getParameter("ajax");
        if (isAjaxRequest != null && isAjaxRequest.equals("true")) {
            request.getRequestDispatcher("/WEB-INF/views/customer/appointment-list-partial.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/customer/view-appointment.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
