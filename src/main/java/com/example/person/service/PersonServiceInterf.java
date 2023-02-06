package com.example.person.service;

import java.sql.SQLException;
import java.util.List;

import com.example.person.Dtos.PersonModelDto;
import com.example.person.model.PersonModel;

public interface PersonServiceInterf {
	
		void createPersonStatic(PersonModelDto personModelDto);
		
		void createPerson(PersonModelDto personModelDto) throws SQLException;
	 
		PersonModel getPersonById(int id);
		
		boolean deletePerson(int id);
		
		List<PersonModel> getAllPerson();
	 

}
