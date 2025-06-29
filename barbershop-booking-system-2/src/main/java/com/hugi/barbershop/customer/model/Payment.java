package com.hugi.barbershop.customer.model;

import java.time.LocalDate;

public class Payment {
	private String paymentId;
	private LocalDate paymentDate;
	private double paymentAmount;
	private String appointmentId;
	private String paymentMethod;
	private String customerName;
	private String customerPicture;
	private String customerEmail;

	// Getters and setters
	public String getPaymentId() { return paymentId; }
	public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

	public LocalDate getPaymentDate() { return paymentDate; }
	public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

	public double getPaymentAmount() { return paymentAmount; }
	public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

	public String getAppointmentId() { return appointmentId; }
	public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
	
	public String getPaymentMethod() {
	    return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
	    this.paymentMethod = paymentMethod;
	}
	public void setCustomerName(String customerName) {
	    this.customerName = customerName;
	}
	public String getCustomerName() {
	    return customerName;
	}
	public void setCustomerPicture(String CustomerPicture) {
	    this.customerPicture = CustomerPicture;
	}
	public String getCustomerPicture() {
	    return customerPicture;
	}
	public void setCustomerEmail(String CustomerEmail) {
	    this.customerEmail = CustomerEmail;
	}
	public String getCustomerEmail() {
	    return customerEmail;
	}

}
