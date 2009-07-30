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
package com.tzavellas.validation.spring;

import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tzavellas.validation.BeanValidator;
import com.tzavellas.validation.PropertyValidator;
import com.tzavellas.validation.ValidationError;

/**
 * An adapter of the {@link BeanValidator} to the Spring's {@link Validator} interface.
 * 
 * <p>This class is used to integrate the {@link BeanValidator} with the validation
 * facilities offered by the Spring Framework and the Spring MVC.</p>
 * 
 * <p>Example code:<br>
 * <pre>
 * import static com.tzavellas.validation.Validators.*;
 * import com.tzavellas.validation.spring.SpringValidator;
 * import org.springframework.validation.Validator;
 * 
 * Validator validator = new SpringValidator(domain.Person.class,
 *          string("firstName").required(),
 *          string("lastName").required(),
 *          string("email").required().email(),
 *          integer("age").min(18),
 *          date("birthdate").past());
 * </pre></p>
 * 
 * <p><b>Thread safety:</b> This class is thread safe.</p>
 * 
 * @author Spiros Tzavellas
 *
 * @see BeanValidator
 * @see Validator
 */
public class SpringValidator implements Validator {
	
	private Class<?> supportedClass;
	private BeanValidator validator;
	
	/**
	 * Create a <code>SpringValidator</code> that supports the specified class and
	 * an empty <code>BeanValidator</code>.
	 * 
	 * <p>For an empty <code>BeanValidator</code> all objects are valid.</p>
	 */
	public SpringValidator(Class<?> supportedClass) {
		this(supportedClass, new BeanValidator());
	}
	
	/**
	 * Create a <code>SpringValidator</code>.
	 * 
	 * @param supportedClass the Class this validator supports
	 * @param propertyValidators the <code>PropertyValidator</code>s to by used by the wrapped
	 *                           <code>BeanValidator</code>
	 */
	public SpringValidator(Class<?> supportedClass, PropertyValidator<?>...propertyValidators) {
		this(supportedClass, new BeanValidator(propertyValidators));
	}
	
	/**
	 * Create a <code>SpringValidator</code> that supports the specified class and
	 * delegates to the specified <code>BeanValidator</code>. 
	 */
	public SpringValidator(Class<?> supportedClass, BeanValidator validator) {
		Assert.notNull(supportedClass, "You must provide a class!");
		Assert.notNull(validator, "You must provide a BeanValidator!");
		this.supportedClass = supportedClass;
		this.validator = validator;
	}
	
	/**
	 * Provide a {@link PropertyValidator} to be used by the wrapped {@link BeanValidator}.
	 * 
	 * @param v the {@link PropertyValidator}
	 */
	public void addValidator(PropertyValidator<?> v) {
		validator.add(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return supportedClass.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void validate(Object target, Errors errors) {
		for (ValidationError msg: validator.validate(target).getErrors())
			errors.rejectValue(msg.getField(), msg.getErrorCode(), msg.getArguments(), msg.getDefaultMessage());
		doExtraValidation(target, errors);
	}
	
	/**
	 * A method to test if the specified object is valid.
	 * 
	 * <p>Use this method if you don't care about the errors and the error messages
	 *  and you just want to see if an object is valid.
	 * 
	 * @param target the object to validate
	 * @return true if the specified object is valid, else false.
	 */
	public boolean isValid(Object target) {
		Errors errors = new BeanPropertyBindingResult(target, "target");
		validate(target, errors);
		return ! errors.hasErrors();
	}
	
	/**
	 * This method can be overridden to provide custom validation logic
	 * in addition to the {@link BeanValidator} that this {@link Validator}
	 * delegates to.
	 * 
	 * <p>The default implementation is empty.</p>
	 * 
	 * @param target the object to validate
	 * @param errors can be used to report any validation errors
	 */
	protected void doExtraValidation(Object target, Errors errors) {
		// empty, for subclasses to override.
	}
}
