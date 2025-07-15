package com.hugi.barbershop.staff.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

import com.hugi.barbershop.common.dao.AppointmentDAO;
import com.hugi.barbershop.customer.model.Appointment;
import com.hugi.barbershop.staff.model.Staff;
import com.hugi.barbershop.common.dao.StaffDAO;
import java.util.List;

@WebServlet("/editAppointment")
public class editAppointment extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public editAppointment() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");

        if (appointmentId != null && !appointmentId.isEmpty()) {
            AppointmentDAO dao = new AppointmentDAO();
            Appointment appointment = dao.getAppointmentById(appointmentId);

            if (appointment != null) {
                request.setAttribute("appointment", appointment);

                StaffDAO staffDAO = new StaffDAO();
                List<Staff> barberList = staffDAO.getAllBarbers();
                request.setAttribute("barberList", barberList);

                request.getRequestDispatcher("/WEB-INF/views/admin/editAppointment.jsp").forward(request, response);
            } else {
                response.sendRedirect("listAppointment?error=notfound");
            }
        } else {
            response.sendRedirect("listAppointment?error=noid");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String adminId = (String) session.getAttribute("staffId"); // admin login punya staff id
    	String barberId = request.getParameter("staffId"); // barber yang dipilih oleh customer
    	
    	String appointmentId = request.getParameter("appointmentId");
        String date = request.getParameter("appointmentDate");
        String time = request.getParameter("appointmentTime");
        String staffId = request.getParameter("staffId");
        String custType = request.getParameter("custType");
        String custBookFor = request.getParameter("custBookFor");
        

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setStaffId(staffId);
        appointment.setCustType(custType);
        appointment.setCustBookFor(custBookFor);
        appointment.setBarberId(Integer.parseInt(barberId));
        
        AppointmentDAO dao = new AppointmentDAO();
        boolean updated = dao.updateAppointmentByAdmin(appointment, adminId);// ✅ guna method byAdmin()

        if (updated) {
            response.sendRedirect("listAppointment?success=updated");
        } else {
            response.sendRedirect("editAppointment?appointmentId=" + appointmentId + "&error=updatefail");
        }
    }
}
