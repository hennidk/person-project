package com.myapp.repository;

import com.myapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query("from Person p where p.parent1Id = ?1 or p.parent2Id = ?1")
	List<Person> findAllByParent1IdOrParent2Id(Long id);

}
