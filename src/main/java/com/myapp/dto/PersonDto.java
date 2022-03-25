package com.myapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myapp.entity.Person;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto {

	private Long id;
	private String name;
	private LocalDate birthDate;
	private PersonDto parent1;
	private PersonDto parent2;
	private PersonDto partner;


	public PersonDto() {

	}

	public PersonDto(Long id, String name, LocalDate birthDate, Person parent1, Person parent2, Person partner) {
		this(id, name, birthDate);
		if (parent1 != null) this.parent1 = new PersonDto(parent1.getId(), parent1.getName(), parent1.getBirthDate());
		if (parent2 != null) this.parent2 = new PersonDto(parent2.getId(), parent2.getName(), parent2.getBirthDate());
		if (partner != null) this.partner = new PersonDto(partner.getId(), partner.getName(), partner.getBirthDate());
	}

	public PersonDto(Long id, String name, LocalDate birthDate){
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public String toCsvString() {
		return
			id + "," + name + "," + birthDate + "," +
				(parent1 != null ? parent1.getName() : "") + "," +
				(parent2 != null ? parent2.getName() : "") + "," +
				(partner != null ? partner.getName() : "");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public PersonDto getParent1() {
		return parent1;
	}

	public void setParent1(PersonDto parent1) {
		this.parent1 = parent1;
	}

	public PersonDto getParent2() {
		return parent2;
	}

	public void setParent2(PersonDto parent2) {
		this.parent2 = parent2;
	}

	public PersonDto getPartner() {
		return partner;
	}

	public void setPartner(PersonDto partner) {
		this.partner = partner;
	}
}
