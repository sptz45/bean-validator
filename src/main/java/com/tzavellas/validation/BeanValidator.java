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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * A class used to validate the properties of JavaBean objects using a
 * collection of specified <code>PropertyValidator</code>s.
 * 
 * <p><b>Thread safety:</b> This class is thread safe.</p>
 * 
 * @author Spiros Tzavellas
 * 
 * @see PropertyValidator
 */
public class BeanValidator {
	
	private List<PropertyValidator<?>> validators;
	
	
	/**
	 * Construct a <code>BeanValidator<code> with the specified collection of
	 * PropertyValidators.
	 */
	public BeanValidator(PropertyValidator<?>...validators) {
		this.validators = new CopyOnWriteArrayList<PropertyValidator<?>>(validators);
	}
	
	
	/**
	 * Add a <code>PropertyValidator</code> to be used when validating objects.
	 * 
	 * @param v the PropertyValidator to add
	 */
	public void add(PropertyValidator<?> v) {
		validators.add(v);
	}
	
	
	/**
	 * Validate the specified object.
	 * 
	 * @param bean the JavaBean to validate
	 * 
	 * @return true if all validations pass else false
	 */
	public boolean isValid(Object bean) {
		return validate(bean).isValid();
	}
	
	
	/**
	 * Validate the specified object.
	 * 
	 * @param bean the JavaBean to validate
	 * 
	 * @return a Collection of errors if the validation fails. An empty Collection otherwise.
	 */
	@SuppressWarnings("unchecked")
	public ValidationResult validate(Object bean) {
		ValidationResult result = new ValidationResult();
		for (PropertyValidator validator: validators) {
			ValidationError error = validator.validate(ReflectionHelper.readProperty(bean, validator.getProperty()));
			if (error != null)
				result.addError(error);
		}
		return result;
	}
}
