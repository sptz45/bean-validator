/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tzavellas.validation;

import static com.tzavellas.validation.TestHelper.assertNumberOfErrors;
import static com.tzavellas.validation.TestHelper.assertValid;
import static com.tzavellas.validation.property.Validators.date;
import static com.tzavellas.validation.property.Validators.integer;
import static com.tzavellas.validation.property.Validators.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for BeanValidator
 * 
 * @author Spiros Tzavellas
 */
public class BeanValidatorTest {
	
	private BeanValidator validator;
	
	@Before
	public void setUp() {
		validator = new BeanValidator(
				string("firstName").required(),
				string("lastName").required(),
				string("email").required().email(),
				integer("age").required().min(18),
				date("birthdate").past()
		);
	}
	
	
	@Test
	public void emptyObject() throws Exception {
		ValidationResult result = validator.validate(new Person());
		
		assertNumberOfErrors(result, 4);
		assertFalse(validator.isValid(new Person()));
	}
	
	
	@Test
	public void addingValidatorsAfterConstruction() {
		Person p = new Person();
		
		assertNumberOfErrors(validator.validate(p), 4);
		
		// adding a validator
		validator.add(string("address.city").required());
		
		assertNumberOfErrors(validator.validate(p), 5);
	}
	
	
	@Test
	public void validObject() throws Exception {
		Person p = new Person();
		p.setFirstName("Spiros");
		p.setLastName("Tzavellas");
		p.setEmail("spiros@tzavellas.com");
		p.setAge(19);
		p.setBirthdate(new Date(1000000));
		
		assertValid(validator.validate(p));
		assertTrue(validator.isValid(p));
	}
	
	
	@Test
	public void association() {
		validator = new BeanValidator(
				string("firstName").required(),
				string("address.city").required()
		);
		Person p = new Person();
		p.setFirstName("spiros");
		assertNumberOfErrors(validator.validate(p), 1);
		
		p.setAddress(new Address("street", "city"));
		assertValid(validator.validate(p));
	}
	
	
	@Test
	public void defaultMessageSerolverReturnsDefaultMessagesFromValidationErrors() {
		ValidationResult result = validator.validate(new Person()); 
		
		assertNumberOfErrors(result, 4);
		Map<String, String> msgs = result.getErrorMessages(null);
		
		for (ValidationError error: result.getErrors()) {
			assertTrue("Found error for field '" + error.getField() + "' but no message in getErrorMessages()!",
					msgs.containsKey(error.getField()));
			assertEquals("The default MesasgeResolver of ValidationResult did not resolve the default message of the error!",
					error.getDefaultMessage(), msgs.get(error.getField()));
		}
	}
	
	
	
	// --------------------------------------------- Classes used for testing 
	
	public static class Person {
		
		private String firstName, lastName, email;
		private Integer age;
		private Date birthdate;
		private Address address = new Address();
		
		public Integer getAge() { return age; }
		public void setAge(Integer age) {
			this.age = age;
		}
		public Date getBirthdate() {
			return birthdate;
		}
		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
	}

	public static class Address {
		
		private String street, city;
		
		public Address() { }
		
		public Address(String street, String city) {
			this.street = street;
			this.city = city;
		}
		
		public String getStreet() { return street; }
		public void setStreet(String s) { this.street = s; }
		
		public String getCity() {return city; }
		public void setCity(String c) { this.city = c; }
	}
}


