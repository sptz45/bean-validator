package com.tzavellas.validation.spring;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import com.tzavellas.validation.ValidationError;


public class MessageSourceResolverTest {
	
	private MockMessageSource mock = new MockMessageSource();
	private ValidationError error = new ValidationError("field", "errorCode", "default message", 5);
	
	@Test
	public void theAdapterDelegatesToTheMessageSource() {
		MessageSourceResolver resolver = new MessageSourceResolver(mock);
		
		String resolved = resolver.getMessage(error, Locale.US);
		assertEquals(MockMessageSource.RESOLVED_MESSAGE, resolved);
		mock.assertLocale(Locale.US);
		mock.assertValidationError(error);
	}
	

	
	// -----------------------------------------------------------------------
	
	private static class MockMessageSource implements MessageSource {
		
		public static String RESOLVED_MESSAGE = "a resolved message";
		
		private ValidationError error;
		private Locale locale;
		
		public String getMessage(MessageSourceResolvable mr, Locale locale) throws NoSuchMessageException {
			throw new UnsupportedOperationException("Mock object method not implemented.");
		}
		public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
			throw new UnsupportedOperationException("Mock object method not implemented.");
		}
		public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
			error = new ValidationError("ignore", code, defaultMessage, args);
			this.locale = locale;
			return RESOLVED_MESSAGE;
		}
		
		public void assertValidationError(ValidationError expected) {
			assertEquals(expected.getErrorCode(), error.getErrorCode());
			assertEquals(expected.getDefaultMessage(), error.getDefaultMessage());
			assertArrayEquals(expected.getArguments(), error.getArguments());
		}
		
		public void assertLocale(Locale expected) {
			assertEquals(expected, locale);
		}
	}
}
