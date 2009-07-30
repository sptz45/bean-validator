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

import java.util.Date;

import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.validators.simple.DateValidators;


/**
 * A property validator for {@link Date} objects.
 * 
 * <p>This class is designed to be used in the DSL via the {@link Validators} class.</p>
 * 
 * @author Spiros Tzavellas
 * 
 * @see Validators
 */
public class DateValidatorBuilder extends LinkedValidatorBuilder<Date, DateValidatorBuilder> {

	
	/**
	 * Create a <code>DateValidatorBuilder</code> for the specified
	 * property name.
	 * 
	 * <p>Usually instances of this class are created with the
	 * {@link Validators#date(String)} factory method.
	 * 
	 * @param property the name of the property that this validator will
	 *                 validate.
	 * 
	 * @see Validators
	 */
	public DateValidatorBuilder(String property) {
		super(property);
	}
	
	
	/**
	 * Validates that a {@link Date} is in the past.
	 */
	public DateValidatorBuilder past() {
		addValidator(new PropertyValidator.Builder<Date>(property)
			.withErrorCode("validator.date.past")
			.withDefaultMessage("Must be a date in the past")
			.withValidator(new DateValidators.Past()).build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link Date} is in the future.
	 */
	public DateValidatorBuilder future() {
		addValidator(new PropertyValidator.Builder<Date>(property)
			.withErrorCode("validator.date.future")
			.withDefaultMessage("Must be a future date")
			.withValidator(new DateValidators.Future()).build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link Date} is before the specified {@link Date}.
	 */
	public DateValidatorBuilder before(final Date date) {
		addValidator(new PropertyValidator.Builder<Date>(property)
			.withErrorCode("validator.date.before")
			.withDefaultMessage("Must be a date before {1}")
			.withMessageArgs(date)
			.withValidator(new DateValidators.Before(date)).build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link Date} is after the specified {@link Date}.
	 */
	public DateValidatorBuilder after(final Date date) {
		addValidator(new PropertyValidator.Builder<Date>(property)
			.withErrorCode("validator.date.after")
			.withDefaultMessage("Must be a date after {1}")
			.withMessageArgs(date)
			.withValidator(new DateValidators.After(date)).build());
		return this;
	}
}
