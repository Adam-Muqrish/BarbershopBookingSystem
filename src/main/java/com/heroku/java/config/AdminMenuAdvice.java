package com.heroku.java.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Adds an {@code activeMenu} model attribute so the admin sidebar
 * (fragments/adminFragments.html) can highlight the menu item matching the
 * current page. Uses the server-provided request URI so it works regardless of
 * client-side JS.
 */
@ControllerAdvice
public class AdminMenuAdvice {

    @ModelAttribute("activeMenu")
    public String activeMenu(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.startsWith("/listCustomer") || uri.startsWith("/barber/customers")) {
            return "customer";
        }
        if (uri.startsWith("/listBarber") || uri.startsWith("/admin/register-staff")
                || uri.startsWith("/admin/edit-staff")) {
            return "staff";
        }
        if (uri.startsWith("/listAppointment") || uri.startsWith("/barber/appointments")
                || uri.startsWith("/admin/edit-appointment")) {
            return "appointment";
        }
        if (uri.startsWith("/admin/list-transactions") || uri.startsWith("/barber/transactions")) {
            return "transaction";
        }
        if (uri.startsWith("/admin/list-feedback") || uri.startsWith("/barber/feedbacks")) {
            return "feedback";
        }
        return "";
    }
}