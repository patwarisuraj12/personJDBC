package com.example.person.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonModel {
	
	private int id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String dob;

}
