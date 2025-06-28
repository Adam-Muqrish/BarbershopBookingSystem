package com.hugi.barbershop.staff.model;

import java.time.LocalDate;

public class ViewAppointment {
    private String appointmentId;
    private String customerName;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String custType;
    private String serviceStatus;
    private int loyaltyPoint;

    // Getters & Setters
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getCustType() { return custType; }
    public void setCustType(String custType) { this.custType = custType; }

    public String getServiceStatus() { return serviceStatus; }
    public void setServiceStatus(String serviceStatus) { this.serviceStatus = serviceStatus; }

    public int getLoyaltyPoint() { return loyaltyPoint; }
    public void setLoyaltyPoint(int loyaltyPoint) { this.loyaltyPoint = loyaltyPoint; }
}

