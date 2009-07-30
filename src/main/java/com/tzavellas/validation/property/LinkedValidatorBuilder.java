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

import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.ValidationError;
import com.tzavellas.validation.validators.Validator;
import com.tzavellas.validation.validators.simple.ObjectValidators;

/**
 * The root of the hierarchy of builder classes, that are used in the 
 * validation DSL.
 * 
 * <p>Users of this class do not invoke a <code>build()</code> method, as
 * is normally done in the Builder design pattern. This is because, in this
 * implementation, the builder class (<code>LinkedValidatorBuilder</code>)
 * also extends the product class (<code>PropertyValidator</code>).
 *
 * @param <T> the type of the property this validator can validate
 * @param <V> the type of this class. This type is returned by the various
 *            methods that build the linked validator to allow the chaining
 *            of method calls.
 *            
 * @see Validators
 * @see PropertyValidator
 * 
 * @author Spiros Tzavellas
 */
@SuppressWarnings("unchecked")
public class LinkedValidatorBuilder<T, V extends LinkedValidatorBuilder>
extends PropertyValidator<T> {
	
	private boolean required = false;
	private LinkedList<PropertyValidator<T>> validators = new LinkedList<PropertyValidator<T>>();
	
	
	// ---------------------------------------------------------- Constructors
	
	/**
	 * Create a <code>LinkedValidatorBuilder</code> for the specified
	 * property name.
	 * 
	 * <p>Usually instances of this class are created with the
	 * {@link Validators#property(String)} factory method.
	 * 
	 * @param property the name of the property that this validator will
	 *                 validate.
	 * 
	 * @see Validators
	 */
	public LinkedValidatorBuilder(String property) {
		super(property);
	}
	
	
	// ------------------------------ overriden methods from PropertyValidator
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isValid(T value) {
		return validate(value) == null;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ValidationError validate(T value) {
		// If it is not required and the value is null (no value) then we are ok
		if (!required && (value == null || ! extraRequiredCheck(value)))
			return null;
		
		// ... else consult all the validators
		for (PropertyValidator<T> validator: validators) {
			ValidationError error = validator.validate(value);
			if (error != null)
				return error;
		}
		
		// all validations passed, return null (success)	
		return null;
	}
	
	
    // ---------------------------- methods specific to LinkedValidatorBuilder
	
	/**
	 * Add the specified validator to the list of validators.
	 * 
	 * @param v the validator to add.
	 */
	protected void addValidator(PropertyValidator<T> v) {
		validators.addLast(v);
	}
	
	
	/**
	 * Subclasses can override this method to add custom behaviour to the
	 * required validator.
	 * 
	 * <p>For example the {@link StringValidatorBuilder} class, overrides this
	 * method so that the required validator (specified with the <code>required()</code>
	 * method of this class) will reject empty Strings.
	 * 
	 * @param value the value of the property. This parameter is always not null.
	 * 
	 * @return false to indicate, to the required validator, that a value is not valid
	 *         and must be rejected. Otherwise returns true.  
	 */
	protected boolean extraRequiredCheck(T value) {
		return true;
	}
	
	
	// ------------------------------ methods that add or customize validators
	
	/**
	 * Mark the property of this <code>PropertyValidator</code> as required,
	 * by adding the required validator to the validators list.
	 * 
	 * <p>The default behaviour of the required validator is to reject null
	 * values. To customize the required validator override the
	 * <code>extraRequiredCheck()</code> method.
	 * 
	 * @return the <code>this</code> object to allow the chaining of method
	 *         invocations.
	 * 
	 * @see LinkedValidatorBuilder#extraRequiredCheck(Object)
	 */
	public final V required() {
		required = true;
		// the required validator must be the first in the chain
		validators.addFirst(new PropertyValidator.Builder<T>(property)
			.withErrorCode("validator.required")
			.withDefaultMessage("This is a required property")
			.withValidator(new Validator<T>() {
				public boolean isValid(T value) {
					return value != null ? extraRequiredCheck(value) : false;
				}
			}).build());
		
		return (V) this;
	}
	
	
	/**
	 * Override the error code of the last specified validator.
	 * 
	 * @param code the new error code
	 * 
	 * @return the <code>this</code> object to allow the chaining of method
	 *         invocations.
	 * 
	 * @throws IllegalStateException if no validator was previously specified.
	 */
	public V withErrorCode(String code) throws IllegalStateException {
		try {
			PropertyValidator<T> validator = validators.getLast();
			validator.setErrorCode(code);

			return (V) this;
		
		} catch (NoSuchElementException e) {
			throw new IllegalStateException("You can't specify custom error code without first specifying a PropertyValidator!");
		}
	}
	
	
	/**
	 * Override the default message of the last specified validator.
	 * 
	 * @param code the new default message
	 * 
	 * @return the <code>this</code> object to allow the chaining of method
	 *         invocations.
	 * 
	 * @throws IllegalStateException if no validator was previously specified.
	 */
	public V withDefaultMessage(String message) throws IllegalStateException {
		try {
			PropertyValidator<T> validator = validators.getLast();
			validator.setDefaultErrorMessage(message);

			return (V) this;
		
		} catch (NoSuchElementException e) {
			throw new IllegalStateException("You can't specify custom default message without first specifying a PropertyValidator!");
		}
	}
	
	/**
	 * Validate that the value of the property is equal with one of the specified
	 * objects.

	 * @return the <code>this</code> object to allow the chaining of method
	 *         invocations.
	 */
	public V in(final Object...objects) {
		addValidator(new PropertyValidator.Builder<T>(property)
			.withErrorCode("validator.in")
			.withMessageArgs(objects)
			.withDefaultMessage("This must be one of: " + Utils.arrayToString(objects))
			.withValidator(new ObjectValidators.In<T>(objects)).build());
		return (V) this;
	}
}
