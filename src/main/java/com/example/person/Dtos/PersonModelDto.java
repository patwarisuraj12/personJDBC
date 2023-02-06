package com.example.person.Dtos;

import com.example.person.model.PersonModel;

import lombok.Data;

@Data
public class PersonModelDto {

	private String firstName;
	private String lastName;
	private String dob;
	
	public PersonModel to() {
		return PersonModel.builder()
				.firstName(firstName)
				.lastName(lastName)
				.dob(dob).build();
		
	}
	
}
