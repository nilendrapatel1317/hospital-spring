package com.spring.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Bill {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    private double totalAmount;
    private double paidAmount;
    private double remainingAmount;
    private boolean status;
    private LocalDate billDate;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDate getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", patient=" + patient.getId() + ", totalAmount=" + totalAmount + ", paidAmount=" + paidAmount
				+ ", remainingAmount=" + remainingAmount + ", status=" + status + ", billDate=" + billDate + "]";
	}
    
	@PrePersist
	public void prePersist() {
		this.billDate = LocalDate.now();
	}
    
}
