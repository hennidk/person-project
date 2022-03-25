package com.myapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private LocalDate birthDate;
	private Long parent1Id;
	private Long parent2Id;
	private Long partnerId;

	public Person() {
	}

	public Person(Long id, String name, LocalDate birthDate, Long partnerId) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.partnerId = partnerId;
	}

	public Person(String name, LocalDate birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}

	public void setParents(Long parent1Id, Long parent2Id) {
		this.parent2Id = parent2Id;
		this.parent1Id = parent1Id;
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

	public Long getParent1Id() {
		return parent1Id;
	}

	public void setParent1Id(Long parent1Id) {
		this.parent1Id = parent1Id;
	}

	public Long getParent2Id() {
		return parent2Id;
	}

	public void setParent2Id(Long parent2Id) {
		this.parent2Id = parent2Id;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
}
