package com.example.person.repositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.person.model.PersonModel;
import com.example.person.repository.PersonRepositoryIntf;

@Repository
public class PersonRepositoryImpl implements PersonRepositoryIntf {
	
	private Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
	
	private Connection connection;
	
	private PreparedStatement ps;
	
	public PersonRepositoryImpl(Connection conn) {
		this.connection = conn;
		createTable();
		
	}

	private void createTable() {
		
		try {
			
			Statement stmt = connection.createStatement();
			
			stmt.execute("create table if not exists person(id int primary key auto_increment,first_name varchar(10),last_name varchar(20), "
															+ "age int, "
															+ "dob varchar(20))");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void createPersonStatic(PersonModel personModel) {
		try {
			Statement stmt = connection.createStatement();
			int res = stmt.executeUpdate("insert into person(first_name,last_name, age, dob) values('patrick', 'bateman',27, 1996-10-10)");
			logger.info("Result is "+res);

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	@Override
	public void createPerson(PersonModel personModel) throws SQLException {
		this.ps = connection.prepareStatement("insert into person(first_name,last_name,age,dob) values (?,?,?,?)");//This protects from SQL INJECTION
		
		//below feature is available on preparedStatement, provides type safety
		this.ps.setString(1,personModel.getFirstName());
		this.ps.setString(2,personModel.getLastName());
		this.ps.setInt(3,personModel.getAge());
		this.ps.setString(4,personModel.getDob());
		
		int res = this.ps.executeUpdate();// In case of DML we used res as INT, for SELECT we declare as ResultSet
		
		logger.info("Result is {}", res);
		
	}

	@Override
	public PersonModel getPersonById(int id) {
		try {
			this.ps = connection.prepareStatement("select * from person where id = ?");
			this.ps.setInt(1, id);
			
			ResultSet rs = this.ps.executeQuery();
			
			while(rs.next()) {
				return getPersonFromResultSet(rs);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private PersonModel getPersonFromResultSet(ResultSet rs) throws SQLException {
		
		return PersonModel.builder()
				.firstName(rs.getString(2))
				.lastName(rs.getString("last_name"))
				.dob(rs.getString(5))
				.age(rs.getInt(4))
				.id(rs.getInt("id"))
				.build();
	}

	@Override
	public boolean deletePerson(int id) {
		try {
			this.ps = connection.prepareStatement("delete from person where id = ?");
			this.ps.setInt(1, id);
			int res = ps.executeUpdate();
			logger.info("Result is {}", res);
			if(res == 1) {
				return true;
			}
			else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PersonModel> getAllPerson() {
		
		List<PersonModel> personList = new ArrayList<PersonModel>();
		
		try {
			this.ps = connection.prepareStatement("select * from person");
			ResultSet rs = this.ps.executeQuery();
			
			while(rs.next()) {
				PersonModel personModel = getPersonFromResultSet(rs);
				 personList.add(personModel);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personList;
	}

}
