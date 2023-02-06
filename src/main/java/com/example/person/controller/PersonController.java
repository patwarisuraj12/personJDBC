package com.example.person.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.person.Dtos.PersonModelDto;
import com.example.person.model.PersonModel;
import com.example.person.service.PersonServiceInterf;

@RestController
public class PersonController {
	
	private static Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	PersonServiceInterf personServiceInterf;
	
	@GetMapping("/person/{id}")
	public PersonModel getPersonById(@PathVariable("id") int id) {
		return personServiceInterf.getPersonById(id);
	}
	
	@GetMapping("/persons")
	public List<PersonModel> getAllPerson() {
		return personServiceInterf.getAllPerson();
		
	}
	
	@PostMapping("/person")
	public ResponseEntity createPerson(@RequestBody PersonModelDto personModelDto ) {
		
		personServiceInterf.createPersonStatic(personModelDto);
		return new ResponseEntity(HttpStatus.CREATED);
		
	}
	
	@PostMapping("/person/preparedStatement")
	public ResponseEntity createNewPerson(@RequestBody PersonModelDto personModelDto ) {
		
		try {
			personServiceInterf.createPerson(personModelDto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/person/delete/{id}")
	public ResponseEntity deletePerson(@PathVariable("id") int id) {
		personServiceInterf.deletePerson(id);
		return new ResponseEntity(HttpStatus.OK);
		
	}

}
