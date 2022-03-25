package com.myapp.controller;


import com.myapp.dto.PersonDto;
import com.myapp.entity.Person;
import com.myapp.exception.ResourceNotFoundException;
import com.myapp.service.PersonService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {


	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@PostMapping
	public Person postPerson(@RequestBody Person person) {
		return personService.save(person);
	}

	@PutMapping("{id}")
	public Person putPerson(@RequestBody Person newPerson, @PathVariable Long id) {
		return personService.updateOrSavePerson(newPerson, id);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deletePerson(@PathVariable Long id) {
		personService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("{id}")
	public Person getPerson(@PathVariable Long id) {
		return personService.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	@GetMapping
	public List<PersonDto> getAllPersons(
		@RequestParam(defaultValue = "id", required = false) String orderBy,
		@RequestParam(defaultValue = "ASC", required = false) String order
	) {
		return personService.getAllPersonsDto(order, orderBy);
	}

	@GetMapping("filtered")
	public List<PersonDto> getFiltered(
		@RequestParam int childrenCount,
		@RequestParam int maxChildAge
	){
		return personService.getFilteredPersons(childrenCount, maxChildAge);
	}

	@GetMapping("csv")
	public ResponseEntity<Resource> getCsv(
		@RequestParam int childrenCount,
		@RequestParam int maxChildAge
	) throws IOException {
		String filename = "results.csv";
		InputStreamResource resource = new InputStreamResource(personService.getFilteredPersonsAsCsv(childrenCount, maxChildAge));

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
			.contentType(MediaType.parseMediaType("application/csv"))
			.body(resource);
	}

}
