package com.tzavellas.validation.spring;

import static com.tzavellas.validation.property.Validators.date;
import static com.tzavellas.validation.property.Validators.integer;
import static com.tzavellas.validation.property.Validators.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.tzavellas.validation.BeanValidatorTest.Address;
import com.tzavellas.validation.BeanValidatorTest.Person;

/**
 * Unit test for SpringValidator
 * 
 * @author Spiros Tzavellas
 */
public class SpringValidatorTest {
	
	SpringValidator validator = new SpringValidator(Person.class);
	private Person target = new Person();
	private BindingResult errors = new BeanPropertyBindingResult(target, "person");
	
	
	@Test(expected=IllegalArgumentException.class)
	public void validatorsMustSupportAClass() {
		new SpringValidator(null);
	}
	
	
	@Test
	public void validatorsSupportClassAndSubclasses() {
		assertTrue("The Person class must be supported", validator.supports(Person.class));
		assertTrue("The validator must support subclasses of the Person class", validator.supports(Customer.class));
		assertFalse("The Address class must NOT be supported", validator.supports(Address.class));
	}
	
	private static class Customer extends Person { }
	
	
	@Test
	public void emptyValidatorsAlwaysReturnNoErrors() {
		validator.validate(target, errors);
		assertFalse("We can't have errors with an empty validator!", errors.hasErrors());
		assertTrue("Object validated by an empty validator must always be valid!", validator.isValid(target));
	}
	
	@Test
	public void addingPropertyValdatorsAfterConstruction() {
		validator = new SpringValidator(Person.class);
		validator.addValidator(string("firstName").required());
		validator.addValidator(string("lastName").required());
		validator.addValidator(string("email").required().email());
		
		validator.validate(target, errors);
		assertEquals("Expected 3 errors", 3, errors.getErrorCount());
		assertFalse("The target object must be invalid!", validator.isValid(target));
	}
	
	
	@Test
	public void testValidateFail() {
		validator = new SpringValidator(Person.class,
				string("firstName").required(),
				string("lastName").required(),
				string("email").required().email(),
				integer("age").required().min(18),
				date("birthdate").past());
		
		validator.validate(target, errors);
		assertEquals("Expected 4 errors", 4, errors.getErrorCount());
		assertFalse("The target object must be invalid!", validator.isValid(target));
	}
	
	
	@Test
	public void testValidatePass() {
		validator = new SpringValidator(Person.class,
				string("firstName").required(),
				string("lastName").required(),
				string("email").required().email(),
				integer("age").required().min(18),
				date("birthdate").past());
		
		target.setFirstName("Spiros");
		target.setLastName("Tzavellas");
		target.setEmail("spiros@example.com");
		target.setAge(27);
		
		validator.validate(target, errors);
		assertFalse("This object must not have any errors", errors.hasErrors());
		assertTrue("The target object must be valid!", validator.isValid(target));
	}
	
	
	@Test
	public void doExtraValidationAddsErrorsToAnObjectThatIsValidForTheBeanValdator() {
		validator = new SpringValidator(Person.class) {
			protected void doExtraValidation(Object target, Errors errors) {
				ValidationUtils.rejectIfEmpty(errors, "firstName", "errorCode");
			}
		};
		
		validator.validate(target, errors);
		assertTrue("Expected doExtraValidation() to add a validation error", errors.hasErrors());
		assertFalse("The target object must be invalid!", validator.isValid(target));
	}
}
