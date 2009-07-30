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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The result of a validation.

 * @see BeanValidator
 * 
 * @author Spiros Tzavellas
 */
public class ValidationResult {
	
	private MessageResolver messageResolver = new DefaultMessageResolver();
	private Collection<ValidationError> errors = new ArrayList<ValidationError>();
	
	
	/**
	 * Set the <code>MessageResolver</code> to be used for the messages of
	 * this <code>ValidationResult</code>.
	 * 
	 * <p>The default <code>MessageResolver</code> always returns the default message
	 * of the validation error, ignoring the error's code and the specified locale.</p>
	 * 
	 * @see MessageResolver
	 * @see ValidationError
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}
	
	
	/**
	 * Add a validation.
	 * 
	 * @param error the error to add
	 */
	public void addError(ValidationError error) {
		errors.add(error);
	}
	
	
	/**
	 * Get the collection of validation errors for this <code>ValidationResult</code>.
	 * 
	 * @return the collection of errors, or an empty collection if there are no
	 *         errors (the object under validation is valid).
	 */
	public Collection<ValidationError> getErrors() {
		return errors;
	}
	
	/**
	 * Get a map of [field, error-message] entries for this result, if this result has
	 * no errors an empty map is returned.
	 * 
	 * @param locale the locale to use for resolving the messages. May be null, see
	 *               {@link MessageResolver} for more info.
	 */
	public Map<String, String> getErrorMessages(Locale locale) {
		Map<String, String> msgs = new HashMap<String, String>();
		for (ValidationError error: errors)
			msgs.put(error.getField(), messageResolver.getMessage(error, locale));
		return msgs;
	}
	
	
	/**
	 * Returns true if this result does not contain errors.
	 */
	public boolean isValid() {
		return errors.isEmpty();
	}
	
	
	/**
	 * Returns true if this result contains errors.
	 */
	public boolean hasErrors() {
		return ! isValid();
	}

	
	// -----------------------------------------------------------------------

	/**
	 * A <code>MessageResolver</code> that always returns the default message
	 * of the <code>ValidationError</code>. 
	 */
	private static class DefaultMessageResolver implements MessageResolver {
		@Override
		public String getMessage(ValidationError error, Locale locale) {
			return error.getDefaultMessage();
		}
	}
}
