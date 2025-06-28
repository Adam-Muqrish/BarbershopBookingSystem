package com.hugi.barbershop.staff.model;

import java.time.LocalDate;

public class ViewTransaction {
	
	private String customerPicture;
    private String customerName;
    private String customerEmail;
    private String paymentId;
    private LocalDate paymentDate;
    private double paymentAmount;
    private String paymentType;
    
    
	public String getCustomerPicture() {
		return customerPicture;
	}
	public void setCustomerPicture(String customerPicture) {
		this.customerPicture = customerPicture;
	}
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
    
    
}
