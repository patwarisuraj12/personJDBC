package com.example.person.serviceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.person.Dtos.PersonModelDto;
import com.example.person.model.PersonModel;
import com.example.person.repository.PersonRepositoryIntf;
import com.example.person.repositoryImpl.PersonRepositoryImpl;
import com.example.person.service.PersonServiceInterf;

@Service
public class PersonServiceImpl implements PersonServiceInterf {
	
	@Autowired
	PersonRepositoryIntf personRepositoryIntf;

	@Override
	public PersonModel getPersonById(int id) {
		return personRepositoryIntf.getPersonById(id);
	}
	
	@Override
	public void createPerson(PersonModelDto personModelDto) throws SQLException {
		
		PersonModel personModel = personModelDto.to();
		
		if(personModel.getAge() == null) {
			personModel.setAge(calAgeFromDob(personModel.getDob()));
		}
		
		personRepositoryIntf.createPerson(personModel);
	}

	@Override
	public void createPersonStatic(PersonModelDto personModelDto) {
		
		PersonModel personModel = personModelDto.to();
		
		if(personModel.getAge() == null) {
			personModel.setAge(calAgeFromDob(personModel.getDob()));
		}
		
		personRepositoryIntf.createPersonStatic(personModel);
		
	}
	
	private Integer calAgeFromDob(String dob) {
		
		if(dob == null) return null;
		
		else {
			
			LocalDate dobDate = LocalDate.parse(dob);
			LocalDate sysDate = LocalDate.now();
		
			return Period.between(dobDate,sysDate).getYears();
			
		}
		
	}
	
	@Override
	public List<PersonModel> getAllPerson() {
		return personRepositoryIntf.getAllPerson();
	}

	@Override
	public boolean deletePerson(int id) {
		return personRepositoryIntf.deletePerson(id);
	}


}
