package com.tzavellas.validation.property;

import static com.tzavellas.validation.property.Validators.property;
import static org.junit.Assert.*;

import org.junit.Test;

import com.tzavellas.validation.ValidationError;

/**
 * Unit test for LinkedValidatorBuilder
 * 
 * @author Spiros Tzavellas
 */
public class LinkedValidatorBuilderTest {
	
	@Test
	@SuppressWarnings("unchecked")
	public void required() {
		assertFalse("Null is not valid when the value is required",
				validator().required().isValid(null));
		assertTrue("Null is valid when the value is not required",
				validator().in("Hello").isValid(null));

		assertTrue(validator().required().isValid("Hello"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void in() {
		assertTrue(validator().in("Hello", "World").isValid("Hello"));
		assertFalse(validator().in("Hello", "World").isValid("not valid"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void withErrorCodeRequiresValidator() {
		validator().withErrorCode("custom.code");
	}
	
	@Test(expected=IllegalStateException.class)
	public void withDefaultMessageRequiresValidator() {
		validator().withDefaultMessage("custom.message");
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void withErrorCodeChangesTheValidationError() {
		ValidationError error = validator().required().withErrorCode("custom.code").validate(null);
		assertNotNull(error);
		assertEquals("custom.code", error.getErrorCode());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void withDefaultChangesThValidationError() {
		ValidationError error = validator().required().withDefaultMessage("custom.message").validate(null);
		assertNotNull(error);
		assertEquals("custom.message", error.getDefaultMessage());
	}
	
	
	protected LinkedValidatorBuilder<?,?> validator() {
		return property("");
	}
}
