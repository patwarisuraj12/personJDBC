package com.example.person.repository;

import java.sql.SQLException;
import java.util.List;

import com.example.person.model.PersonModel;

public interface PersonRepositoryIntf {
	
	void createPerson(PersonModel personModel) throws SQLException;
	
	void createPersonStatic(PersonModel personModel);
	
	PersonModel getPersonById(int id);
	
	boolean deletePerson(int id);
	
	List<PersonModel> getAllPerson();

}
