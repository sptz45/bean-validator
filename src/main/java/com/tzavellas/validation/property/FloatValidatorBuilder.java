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

import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.validators.simple.FloatValidators;

/**
 * A property validator for {@link Float} and {@link Double} objects.
 * 
 * <p>This class is designed to be used in the DSL via the {@link Validators} class.</p>
 * 
 * @author Spiros Tzavellas
 * 
 * @see Validators
 */
public class FloatValidatorBuilder extends LinkedValidatorBuilder<Number, FloatValidatorBuilder> {

	/**
	 * Create a <code>FloatValidatorBuilder</code> for the specified
	 * property name.
	 * 
	 * <p>Usually instances of this class are created with the
	 * {@link Validators#floatingPoint(String)} factory method.
	 * 
	 * @param property the name of the property that this validator will
	 *                 validate.
	 * 
	 * @see Validators
	 */
	public FloatValidatorBuilder(String property) {
		super(property);
	}
	
	/**
	 * Validates that a {@link Number} object is greater than or equal to the
	 * specified <code>double</code>.
	 */
	public FloatValidatorBuilder min(final double min) {
		addValidator(new PropertyValidator.Builder<Number>(property)
			.withErrorCode("validator.float.min")
			.withMessageArgs(min)
			.withDefaultMessage("Must be greater than " + min)
			.withValidator(new FloatValidators.Min(min)).build());
		return this;
	}
	
	/**
	 * Validates that a {@link Number} object is less than or equal to the
	 * specified <code>double</code>.
	 */
	public FloatValidatorBuilder max(final double max) {
		addValidator(new PropertyValidator.Builder<Number>(property)
			.withErrorCode("validator.float.max")
			.withMessageArgs(max)
			.withDefaultMessage("Must be less than " + max)
			.withValidator(new FloatValidators.Max(max)).build());
		return this;
	}
	
	/**
	 * Validates that a {@link Number} object belongs to the specified range.
	 * 
	 * <p>The parameters <code>min</code> and <code>max</code> are inclusive.</p>
	 */
	public FloatValidatorBuilder range(final double min, final double max) {
		addValidator(new PropertyValidator.Builder<Number>(property)
			.withErrorCode("validator.float.range")
			.withMessageArgs(min, max)
			.withDefaultMessage("Must be greater than or " + min + " and less than " + max)
			.withValidator(new FloatValidators.Range(max, min)).build());
		return this;
	}
}
