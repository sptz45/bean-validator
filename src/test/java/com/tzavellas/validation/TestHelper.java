package com.tzavellas.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Contains various method that assist the coding of utit tests.
 * 
 * @author Spiros Tzavellas
 */
public abstract class TestHelper {
	
	private TestHelper() { }

	
	public static <T> void assertValid(PropertyValidator<T> validator, T value) {
		ValidationError error  = validator.validate(value);
		assertNull(nullSafeToString(error), error);
	}
	
	public static <T> void assertInvalid(PropertyValidator<T> validator, T value) {
		assertFalse("This must be invalid! Value: " + value, validator.isValid(value));
	}
	
	public static void assertValid(ValidationResult result) {
		assertTrue("This must be an valid object!", result.isValid());
		assertFalse("This must be a valid object! \nErrors: " + result.getErrors(), result.hasErrors());
	}
	
	public static void assertNumberOfErrors(ValidationResult result, int numOfErrors) {
		if (numOfErrors > 0) {
			assertEquals("Expected validation errors!", numOfErrors, result.getErrors().size());
		}
		assertFalse("This must be an invalid object!", result.isValid());
		assertTrue("The validation result must have errors!", result.hasErrors());
	}
	
	public static String nullSafeToString(Object maybeNull) {
		if (maybeNull == null)
			return "null";
		else
			return maybeNull.toString();
	}
}
