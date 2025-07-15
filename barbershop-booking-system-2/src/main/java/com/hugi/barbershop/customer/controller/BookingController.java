package com.hugi.barbershop.customer.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hugi.barbershop.customer.model.Customer;
import com.hugi.barbershop.staff.model.Staff;
import com.google.gson.Gson;
import com.hugi.barbershop.common.dao.CustomerDAO;
import com.hugi.barbershop.common.dao.StaffDAO;

@WebServlet("/booking")
public class BookingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    public BookingController() {
        super();
    }

    // Helper method for creating safe JSON
    private String createSafeJson(Map<String, List<String>> unavailableBarbersBySlot) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        boolean firstSlot = true;
        for (Map.Entry<String, List<String>> entry : unavailableBarbersBySlot.entrySet()) {
            if (!firstSlot) {
                jsonBuilder.append(",");
            }
            firstSlot = false;
            String escapedKey = entry.getKey()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            jsonBuilder.append("\"").append(escapedKey).append("\":[");
            List<String> barbers = entry.getValue();
            for (int i = 0; i < barbers.size(); i++) {
                if (i > 0) {
                    jsonBuilder.append(",");
                }
                String escapedName = barbers.get(i)
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"");
                jsonBuilder.append("\"").append(escapedName).append("\"");
            }
            jsonBuilder.append("]");
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String custId = (String) request.getSession().getAttribute("custId");
        Customer customer = customerDAO.getCustomerById(custId);

        if (customer == null) {
            request.setAttribute("error", "Customer data not found");
            request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
            return;
        }

        LocalDate today = LocalDate.now();
        String selectedDate = request.getParameter("date");
        if (selectedDate == null || selectedDate.isEmpty()) {
            selectedDate = today.format(DateTimeFormatter.ISO_DATE);
        }
        LocalDate parsedSelectedDate = LocalDate.parse(selectedDate);
        if (parsedSelectedDate.isBefore(today)) {
            selectedDate = today.format(DateTimeFormatter.ISO_DATE);
        }
        String selectedSlot = request.getParameter("slot");

        StaffDAO staffDAO = new StaffDAO();
        List<Staff> barbers = staffDAO.getAllBarbers();

        String[] slots = {
                "10:00 am", "10:30 am", "11:00 am", "11:30 am",
                "12:00 pm", "12:30 pm", "1:00 pm", "1:30 pm",
                "2:00 pm", "2:30 pm", "3:00 pm", "3:30 pm",
                "4:00 pm", "4:30 pm", "5:00 pm", "5:30 pm",
                "6:00 pm", "6:30 pm", "7:00 pm", "7:30 pm",
                "8:00 pm", "8:30 pm", "9:00 pm", "9:30 pm"
        };

        Map<String, Boolean> slotAvailability = new HashMap<>();
        Map<String, List<String>> unavailableBarbersBySlot = new HashMap<>();

        for (String slot : slots) {
            List<String> unavailableBarbers = staffDAO.getUnavailableBarbersForSlot(slot, selectedDate);
            unavailableBarbersBySlot.put(slot, unavailableBarbers);
            boolean available = unavailableBarbers.size() < barbers.size();
            slotAvailability.put(slot, available);
        }

        boolean isAjax = "true".equals(request.getParameter("ajax"));
        if (isAjax) {
            response.setContentType("application/json");
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("unavailableBarbersBySlot", unavailableBarbersBySlot);
            jsonResponse.put("totalBarbers", barbers.size());
            String jsonString = new Gson().toJson(jsonResponse);
            response.getWriter().write(jsonString);
            return;
        }

        String unavailableBarbersBySlotJson = createSafeJson(unavailableBarbersBySlot);

        request.setAttribute("customer", customer);
        request.setAttribute("barbers", barbers);
        request.setAttribute("slotAvailability", slotAvailability);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("selectedSlot", selectedSlot);
        request.setAttribute("unavailableBarbersBySlot", unavailableBarbersBySlot);
        request.setAttribute("unavailableBarbersBySlotJson", unavailableBarbersBySlotJson);
        request.setAttribute("today", today.format(DateTimeFormatter.ISO_DATE));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/customer/booking-appointment.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String custId = (String) session.getAttribute("custId");
        String bookingFor = request.getParameter("booking-for");
        String bookingDate = request.getParameter("date");
        String selectedTime = request.getParameter("slot");
        String category = request.getParameter("category");
        String barberId = request.getParameter("barber"); // renamed for clarity

        String bookingKey = UUID.randomUUID().toString();
        session.setAttribute("bookingKey", bookingKey);

        StaffDAO staffDAO = new StaffDAO();
        // Use barberId for assigned barber
        String selectedBarberName = staffDAO.getBarberNameById(barberId);

        double price = 0.0;
        switch (category.toLowerCase()) {
            case "child": price = 10.0; break;
            case "teen": price = 15.0; break;
            case "adult": price = 30.0; break;
            case "senior": price = 20.0; break;
        }

        LocalDate selectedDateObj = LocalDate.parse(bookingDate);
        if (selectedDateObj.isBefore(LocalDate.now())) {
            request.setAttribute("error", "You cannot book an appointment for a past date.");
            request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
            return;
        }

        if (!staffDAO.isBarberAvailable(barberId, bookingDate, selectedTime)) {
            request.setAttribute("error", "Selected barber is already booked for this slot.");
            request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
            return;
        }

        // Call Appointment microservice
        try {
            // Replace with your actual Appointment microservice URL
            URL url = new URL("http://192.168.0.66:8081/barbershop-customer-service/api/appointment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Build JSON payload (use barberId instead of staffId)
            String jsonInputString = String.format(
                "{" +
                "\"custId\":\"%s\"," +
                "\"custBookFor\":\"%s\"," +
                "\"appointmentDate\":\"%s\"," +
                "\"appointmentTime\":\"%s\"," +
                "\"custType\":\"%s\"," +
                "\"barberId\":\"%s\"" +
                "}",
                custId, bookingFor, bookingDate, selectedTime, category, barberId
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            if (code == 201) {
                // Success: redirect to payment
                session.setAttribute("bookingFor", bookingFor);
                session.setAttribute("bookingDate", bookingDate);
                session.setAttribute("selectedTime", selectedTime);
                session.setAttribute("category", category);
                session.setAttribute("selectedBarber", selectedBarberName); // Save barber name
                session.setAttribute("barberId", barberId);
                session.setAttribute("price", price);
                session.removeAttribute("appointmentId");
                response.sendRedirect("payment?bookingKey=" + bookingKey);
            } else {
                request.setAttribute("error", "Failed to book appointment. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error connecting to appointment service.");
            request.getRequestDispatcher("/WEB-INF/views/customer/error.jsp").forward(request, response);
        }
    }
}
