package com.spring.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Doctor {
	@Id
	private String id;

	private String name;
	private String image;
	private String specialization;
	private int experience;
	private long phone;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointment> appointments;

	private LocalDate joinDate;

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

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
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

	public LocalDate getJoinDate() {
		return joinDate;
	}
	
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", image=" + image + ", specialization=" + specialization
				+ ", experience=" + experience + ", phone=" + phone + ", appointments=" + appointments + ", joinDate="
				+ joinDate + "]";
	}

	@PrePersist
	public void prePersist() {
		this.joinDate = LocalDate.now();
	}

}
