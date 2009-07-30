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

import java.util.Calendar;

import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.validators.simple.CalendarValidators;

/**
 * A property validator for {@link Calendar} objects.
 * 
 * <p>This class is designed to be used in the DSL via the {@link Validators} class.</p>
 * 
 * @author Spiros Tzavellas
 * 
 * @see Validators
 */
public class CalendarValidatorBuilder extends LinkedValidatorBuilder<Calendar, CalendarValidatorBuilder> {

	/**
	 * Create a <code>CalendarValidatorBuilder</code> for the specified
	 * property name.
	 * 
	 * <p>Usually instances of this class are created with the
	 * {@link Validators#calendar(String)} factory method.
	 * 
	 * @param property the name of the property that this validator will
	 *                 validate.
	 * 
	 * @see Validators
	 */
	public CalendarValidatorBuilder(String property) {
		super(property);
	}
	
	
	/**
	 * Validates that a {@link Calendar} is in the past.
	 */
	public CalendarValidatorBuilder past() {
		addValidator(new PropertyValidator.Builder<Calendar>(property)
			.withErrorCode("validator.date.past")
			.withDefaultMessage("Must be a date in the past")
			.withValidator(new CalendarValidators.Past()).build());
		return this;
	}
	
	
	/**
	 * Validates that a {@link Calendar} is in the future.
	 */
	public CalendarValidatorBuilder future() {
		addValidator(new PropertyValidator.Builder<Calendar>(property)
			.withErrorCode("validator.date.future")
			.withDefaultMessage("Must be a future date")
			.withValidator(new CalendarValidators.Future()).build());
		return this;
	}
	
	/**
	 * Validates that a {@link Calendar} is before the specified Calendar.
	 */
	public CalendarValidatorBuilder before(final Calendar date) {
		addValidator(new PropertyValidator.Builder<Calendar>(property)
			.withErrorCode("validator.date.before")
			.withDefaultMessage("Must be a date before {1}")
			.withMessageArgs(date)
			.withValidator(new CalendarValidators.Before(date)).build());
		return this;
	}
	
	/**
	 * Validates that a {@link Calendar} is after the specified Calendar.
	 */
	public CalendarValidatorBuilder after(final Calendar date) {
		addValidator(new PropertyValidator.Builder<Calendar>(property)
			.withErrorCode("validator.date.after")
			.withDefaultMessage("Must be a date after {1}")
			.withMessageArgs(date)
			.withValidator(new CalendarValidators.After(date)).build());
		return this;
	}
}
