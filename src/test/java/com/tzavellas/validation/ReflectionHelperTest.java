package com.tzavellas.validation;

import static com.tzavellas.validation.ReflectionHelper.readProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.tzavellas.validation.BeanValidatorTest.Address;
import com.tzavellas.validation.BeanValidatorTest.Person;


public class ReflectionHelperTest {
	
	private Person p = new Person();
	
	@Before
	public void setUp() {
		p.setFirstName("Spiros");
	}
	
	@Test
	public void testSimpleValue() throws Exception {
		assertEquals("Spiros", readProperty(p, "firstName"));
		assertEquals("Spiros", readProperty(p, "firstName")); // for caching
		assertNull(readProperty(p, "lastName"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readPropertyDoesNotAcceptNullProperty() throws Exception {
		readProperty(p, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readPropertyDoesNotAcceptNullTarget() throws Exception {
		readProperty(null, "firstName");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void errorIfPropertyDoesNotExistInTarget() throws Exception {
		readProperty(p, "doesNotExist");
	}
	
	@Test
	public void associationFetch() {
		p.setAddress(new Address("MyStreet", "MyCity"));
		assertEquals("MyStreet", readProperty(p, "address.street"));
	}
	
	@Test
	public void associationFetchNull() {
		p.setAddress(new Address(null, "MyCity"));
		assertNull(readProperty(p, "address.street"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void errorWhenTheAssociationIsNull() {
		p.setAddress(null);
		readProperty(p, "address.street");
	}
}
