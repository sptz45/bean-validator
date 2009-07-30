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

import com.tzavellas.validation.property.LinkedValidatorBuilder;
import com.tzavellas.validation.validators.Validator;

/**
 * A class used to validate a JavaBean property. 
 * 
 * @author Spiros Tzavellas
 *
 * @param <T> the type (or a parent type) of the property
 */
public class PropertyValidator<T> {
	
	protected String property;
	private Validator<T> validator;
	private ValidationError validationError;
	
	
	/**
	 * This is for the {@link LinkedValidatorBuilder} and subclasses. If you want to
	 * construct a {@link PropertyValidator} use the other constructor or use/subclass
	 * {@link LinkedValidatorBuilder}.
	 */
	public PropertyValidator(String property) {
		this.property = property;
	}
	
	
	/**
	 * Construct a <code>PropertyValidator</code>
	 * 
	 * @param property the name of the property
	 * @param validator the validator that will validate the property's value
	 * @param errorMessage the error that will be returned if the validation fails
	 */
	public PropertyValidator(String property, Validator<T> validator, ValidationError errorMessage) {
		this.property = property;
		this.validator = validator;
		this.validationError = errorMessage;
	}
	
		
	/**
	 * Get the name of the property this validator will validate.
	 */
	public String getProperty() {
		return property;
	}
	
	
	/**
	 * Test if the specified property value is valid
	 * 
	 * @param value the value of the property.
	 * 
	 * @return true if valid else false.
	 */
	public boolean isValid(T value) {
		return validator.isValid(value);
	}
	
	
	/**
	 * Test if the specified property value is valid
	 * 
	 * @param value the value of the property.
	 * 
	 * @return null if valid else a <code>ValdationError</code>.
	 */
	public ValidationError validate(T value) {
		if (validator.isValid(value))
			return null;
		return validationError;
	}
	
	
	/**
	 * Modify the error code of the ValidationError this validator returns.
	 * 
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		validationError.setErrorCode(errorCode);
	}
	
	
	/**
	 * Modify the default message of the ValidationError this validator returns.
	 * 
	 * @param message the new default message
	 */
	public void setDefaultErrorMessage(String message) {
		validationError.setDefaultMessage(message);
	}
	
	
	// -----------------------------------------------------------------------
	
	/**
	 * A builder used to create a <code>PropertyValidator</code>
	 * 
	 * @author Spiros Tzavellas
	 *
	 * @param <T> the type (or a parent type) of the PropertyValidator's property
	 */
	public static class Builder<T> {
		
		private String property;
		private String errorCode;
		private String message;
		private Object[] args = new Object[0];
		private Validator<T> validator;
		
		public Builder(String property) {
			this.property = property;
		}
		
		public Builder<T> withErrorCode(String code) {
			this.errorCode = code;
			return this;
		}
		public Builder<T> withDefaultMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder<T> withMessageArgs(Object... args) {
			this.args = args;
			return this;
		}
		public Builder<T> withValidator(Validator<T> v) {
			this.validator = v;
			return this;
		}

		public PropertyValidator<T> build() {
			if (property == null || errorCode == null || message == null || validator == null) {
				throw new RuntimeException("Error while creating PropertyValidator!" +
						" You *must* specify: property, errorCode, message and validator.");
			}
			return new PropertyValidator<T>(property, validator,
					new ValidationError(property, errorCode, message, args));
		}
	}
}
