package com.myapp;

import com.myapp.entity.Person;
import com.myapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.myapp"})
public class PersonProjectApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(PersonProjectApplication.class, args);
	}

	@Override
	public void run(String... arg) {
		List<Person> persons = new ArrayList<>();
		Person parent1 = new Person("Frank Smith", LocalDate.of(1973, 1,15));
		parent1 = personRepository.save(parent1);
		Person parent2 = new Person("Jane Smith", LocalDate.of(1975, 7, 7));
		parent2.setPartnerId(parent1.getId());
		parent2 = personRepository.save(parent2);
		parent1.setPartnerId(parent2.getId());
		parent1 = personRepository.save(parent1);
		Person child1 = new Person("Andy Smith", LocalDate.of(1990, 4,15));
		child1.setParents(parent1.getId(), parent2.getId());
		personRepository.save(child1);
		Person child2 = new Person("Sally Smith", LocalDate.of(1991, 11, 12));
		child2.setParents(parent1.getId(), parent2.getId());
		personRepository.save(child2);
	}

}
