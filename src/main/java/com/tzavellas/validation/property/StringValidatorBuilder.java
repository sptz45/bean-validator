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
package com.tzavellas.validation.property;

import java.util.regex.Pattern;

import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.validators.CreditCardValidator;
import com.tzavellas.validation.validators.EmailValidator;
import com.tzavellas.validation.validators.ISBNValidator;
import com.tzavellas.validation.validators.UrlValidator;
import com.tzavellas.validation.validators.simple.StringValidators;

/**
 * A property validator for {@link String} objects.
 * 
 * <p>This class is designed to be used in the DSL via the {@link Validators} class.</p>
 * 
 * @author Spiros Tzavellas
 * 
 * @see Validators
 */
public class StringValidatorBuilder extends LinkedValidatorBuilder<String, StringValidatorBuilder> {
	
	
	/**
	 * Create a <code>StringValidatorBuilder</code> for the specified
	 * property name.
	 * 
	 * <p>Usually instances of this class are created with the
	 * {@link Validators#string(String)} factory method.
	 * 
	 * @param property the name of the property that this validator will
	 *                 validate.
	 * 
	 * @see Validators
	 */
	public StringValidatorBuilder(String property) {
		super(property);
	}
	
	
	/**
	 * Ensure that empty strings (the String "") are rejected in the required
	 * validator.
	 * 
	 * @see LinkedValidatorBuilder#required()
	 */
	@Override
	protected boolean extraRequiredCheck(String value) {
		return !value.trim().equals("");
	}
	
	
	// -----------------------------------------------------------------------

	/**
	 * Validates that a {@link String} object has length less than or equal to the
	 * specified <code>max</code> prameter.
	 */
	public StringValidatorBuilder maxLength(final int max) {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.max.length")
			.withDefaultMessage("Must not have more than " + max + "characters")
			.withMessageArgs(max)
			.withValidator(new StringValidators.MaxLength(max)).build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link String} object has length greater than or equal to the
	 * specified <code>max</code> prameter.
	 */
	public StringValidatorBuilder minLength(final int min) {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.min.length")
			.withDefaultMessage("Must not have less than " + min + "characters")
			.withMessageArgs(min)
			.withValidator(new StringValidators.MinLength(min)).build());
		return this;
	}	
	
	
	/**
	 * Validates that a {@link String} object matches the specified regular expression.
	 * 
	 * @see Pattern
	 */
	public StringValidatorBuilder regex(final String regex) {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.regex")
			.withDefaultMessage("Does not match " + regex)
			.withMessageArgs(regex)
			.withValidator(new StringValidators.Regex(regex)).build());
		return this;
	}
	

	/**
	 * Validates that a {@link String} object is a valid credit card number.
	 */
	public PropertyValidator<String> creditCard() {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.credit.card")
			.withDefaultMessage("Must be a valid credit card number")
			.withValidator(new CreditCardValidator())
			.build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link String} object is a valid ISBN.
	 */
	public PropertyValidator<String> isbn() {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.isbn")
			.withDefaultMessage("Must be a valid ISBN")
			.withValidator(new ISBNValidator())
			.build());
		return this;
	}

	
	/**
	 * Validates that a {@link String} object is a valid email address.
	 */
	public PropertyValidator<String> email() {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.email")
			.withDefaultMessage("Must be a valid email address")
			.withValidator(new EmailValidator())
			.build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link String} object is a valid URL.
	 */
	public PropertyValidator<String> url() {
		addValidator(new PropertyValidator.Builder<String>(property)
			.withErrorCode("validator.string.url")
			.withDefaultMessage("Must be a valid URL")
			.withValidator(new UrlValidator())
			.build());
		return this;
	}
}
