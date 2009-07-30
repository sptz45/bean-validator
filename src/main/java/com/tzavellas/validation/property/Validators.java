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

import com.tzavellas.validation.BeanValidator;

/**
 * A class with short factory methods to create the initial <code>PropertyValidator</code>
 * objects in the validation DSL.
 * 
 * <p>All the <code>PropertyValidator</code>s created by the methods of this class are of type
 * {@link LinkedValidatorBuilder} and contain methods that can be chained to create
 * the exact constraint to validate the property values against.</p>
 *  
 * <p>An example of the validation DSL:</p>
 * <pre>
 * BeanValidator validator = new BeanValidator(
 *          string("firstName").required(),
 *          string("lastName").required(),
 *          string("email").required().email(),
 *          integer("age").min(18),
 *          date("birthdate").past());
 * </pre>
 * 
 * @see BeanValidator
 * 
 * @author Spiros Tzavellas
 */
public abstract class Validators {
	
	private Validators() { }
	
	
	/**
	 * Validate a property of type Object.
	 * 
	 * @param name the name of the property
	 */
	@SuppressWarnings("unchecked")
	public static LinkedValidatorBuilder<Object, LinkedValidatorBuilder> property(String name) {
		return new LinkedValidatorBuilder<Object, LinkedValidatorBuilder>(name);
	}
	
	
	/**
	 * Validate a property of type String.
	 * 
	 * @param name the name of the property
	 */
	public static StringValidatorBuilder string(String name) {
		return new StringValidatorBuilder(name);
	}
	
	
	/**
	 * Validate a property of type java.util.Date.
	 * 
	 * @param name the name of the property
	 */
	public static DateValidatorBuilder date(String name) {
		return new DateValidatorBuilder(name);
	}
	
	/**
	 * Validate a property of type java.util.Calendar.
	 * 
	 * @param name the name of the property
	 */
	public static CalendarValidatorBuilder calendar(String name) {
		return new CalendarValidatorBuilder(name);
	}
	
	/**
	 * Validate an integer property (int, Integer, long, and Long). 
	 * 
	 * @param name the name of the property
	 */
	public static IntegerValidatorBuilder integer(String name) {
		return new IntegerValidatorBuilder(name);
	}
	
	
	/**
	 * Validate a floating point property (float, Float, double, and Double).
	 * 
	 * @param name the name of the property
	 */
	public static FloatValidatorBuilder floatingPoint(String name) {
		return new FloatValidatorBuilder(name);
	}
}
