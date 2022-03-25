package com.myapp.service;

import com.myapp.dto.PersonDto;
import com.myapp.entity.Person;
import com.myapp.exception.BadRequestException;
import com.myapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Person save(Person person) {
		return personRepository.save(person);
	}

	public Person updateOrSavePerson(Person newPerson, Long id) {
		return findById(id)
			.map(person -> {
				person.setName(newPerson.getName());
				person.setBirthDate(newPerson.getBirthDate());
				person.setParent1Id(newPerson.getParent1Id());
				person.setParent2Id(newPerson.getParent2Id());
				person.setPartnerId(newPerson.getPartnerId());
				return save(person);
			})
			.orElseGet(() -> save(newPerson));
	}

	public Optional<Person> findById(Long id) {
		return personRepository.findById(id);
	}

	public List<PersonDto> getAllPersonsDto() {
		return personRepository.findAll().stream()
			.map(per -> new PersonDto(
				per.getId(),
				per.getName(),
				per.getBirthDate(),
				per.getParent1Id() != null ? personRepository.findById(per.getParent1Id()).orElse(null) : null,
				per.getParent2Id() != null ? personRepository.findById(per.getParent2Id()).orElse(null) : null,
				per.getPartnerId() != null ? personRepository.findById(per.getPartnerId()).orElse(null) : null
			))
			.collect(Collectors.toList());

	}

	public List<PersonDto> getAllPersonsDto(String order, String orderBy) {
		Stream<PersonDto> personStream = getAllPersonsDto().stream();
		if (order != null) {
			if (orderBy != null && orderBy.equalsIgnoreCase("name"))
				personStream = personStream.sorted(order.equals("DESC") ? Comparator.comparing(PersonDto::getName).reversed() : Comparator.comparing(PersonDto::getName));
			else
				personStream = personStream.sorted(order.equals("DESC") ? Comparator.comparingLong(PersonDto::getId).reversed() : Comparator.comparingLong(PersonDto::getId));
		}

		return personStream.collect(Collectors.toList());
	}

	public void deleteById(Long id) {
		Optional<Person> opt = findById(id);
		if (opt.isPresent()) {
			Person person = opt.get();
			if (hasChildren(person)) {
				throw new BadRequestException();
			}
			personRepository.delete(person);
		}
	}

	public boolean hasChildren(Person person) {
		return !getChildren(person).isEmpty();
	}

	public List<Person> getChildren(Person person) {
		return personRepository.findAllByParent1IdOrParent2Id(person.getId());
	}

	public List<PersonDto> getFilteredPersons(int childrenCount, int maxChildAge) {
		return getAllPersonsDto()
			.stream()
			.filter(p -> {
				if (p.getPartner() != null) {
					List<Person> children = getChildrenForParents(p, p.getPartner());
					return children.size() >= childrenCount &&
						children.stream().anyMatch(per -> per.getBirthDate().isBefore(LocalDate.now().minusYears(maxChildAge)));
				} else return false;
			})
			.collect(Collectors.toList());
	}

	public ByteArrayInputStream getFilteredPersonsAsCsv(int childrenCount, int maxChildAge) throws IOException {
		return getResultAsCsv(getFilteredPersons(childrenCount, maxChildAge));
	}

	private List<Person> getChildrenForParents(PersonDto person1, PersonDto person2) {
		return personRepository.findAll().stream()
			.filter(per ->
				(Objects.equals(per.getParent1Id(), person1.getId()) || Objects.equals(per.getParent2Id(), person1.getId()))
				&&
				(Objects.equals(per.getParent1Id(), person2.getId()) || Objects.equals(per.getParent2Id(), person2.getId()))
			)
			.collect(Collectors.toList());
	}

	public ByteArrayInputStream getResultAsCsv(List<PersonDto> persons) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		for (PersonDto person : persons) {
			writer.write(person.toCsvString());
			writer.append("\n");
		}
		writer.flush();
		return new ByteArrayInputStream(out.toByteArray());
	}
}
