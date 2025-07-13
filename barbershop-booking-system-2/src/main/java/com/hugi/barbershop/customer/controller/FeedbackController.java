package com.hugi.barbershop.customer.controller;

import com.hugi.barbershop.customer.model.Feedback;
import com.hugi.barbershop.common.dao.FeedbackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FeedbackController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        HttpSession session = request.getSession();
        if (appointmentId != null) {
            session.setAttribute("appointmentId", appointmentId);
        } else {
            appointmentId = (String) session.getAttribute("appointmentId");
        }

        Feedback existingFeedback = null;
        if (appointmentId != null) {
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            existingFeedback = feedbackDAO.getFeedbackByAppointment(Integer.parseInt(appointmentId));
        }

        request.setAttribute("existingFeedback", existingFeedback);

        String feedbackSuccess = (String) session.getAttribute("feedbackSuccess");
        if (feedbackSuccess != null) {
            request.setAttribute("feedbackSuccess", feedbackSuccess);
            session.removeAttribute("feedbackSuccess");
        }

        request.getRequestDispatcher("/WEB-INF/views/customer/feedback.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String comments = request.getParameter("feedback");
        String ratingStr = request.getParameter("rating");
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired.");
            return;
        }
        String appointmentIdStr = (String) session.getAttribute("appointmentId");

        if (appointmentIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(appointmentIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID.");
            return;
        }

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if ("delete".equalsIgnoreCase(action)) {
            feedbackDAO.deleteFeedback(appointmentId);
            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendRedirect("feedback?appointmentId=" + appointmentId);
            }
            return;
        }

        if (ratingStr == null || ratingStr.trim().isEmpty() || comments == null || comments.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rating and comments cannot be empty.");
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid rating.");
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setComments(comments);
        feedback.setRating(rating);
        feedback.setAppointmentId(appointmentId);

        Feedback existingFeedback = feedbackDAO.getFeedbackByAppointment(appointmentId);
        if (existingFeedback != null) {
            feedbackDAO.updateFeedback(feedback);
        } else {
            feedbackDAO.insertFeedback(feedback);
        }

        session.setAttribute("feedbackSuccess", "Your feedback has been submitted successfully.");

        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendRedirect("feedback?appointmentId=" + appointmentId);
        }
    }
}
