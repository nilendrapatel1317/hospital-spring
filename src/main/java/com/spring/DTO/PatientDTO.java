package com.spring.DTO;

public class PatientDTO {
	private String id;
	private String name;
	private String gender;

	public PatientDTO() {
	}

	public PatientDTO(String id, String name, String gender) {
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

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

}
