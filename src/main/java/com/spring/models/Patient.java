package com.spring.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Patient {
	@Id
	private String id;

	private String name;
	private String image;
	private String gender;
	private int age;
	private String address;
	private long phone;
	
	private LocalDate admissionDate;

	@OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Bill bill;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointment> appointments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", image=" + image + ", gender=" + gender + ", age=" + age
				+ ", address=" + address + ", phone=" + phone + ", admissionDate=" + admissionDate + ", bill=" + bill
				+ ", appointments=" + appointments + "]";
	}
	
	@PrePersist
	public void prePersist() {
		this.admissionDate = LocalDate.now();
	}
}
