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

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * A class that represents a validation error.
 * 
 * @author Spiros Tzavellas
 */
public class ValidationError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String errorCode;
	private String defaultMessage;
	private Object[] messageArguments = new Object[0];
	
	/**
	 * Default constructor for <code>ValidationError</code>.
	 * 
	 * @param field the property that failed the validation
	 * @param errorCode the error code to be used to resolve the error message
	 * @param defaultMessage the fallback default message
	 * @param messageArguments any message arguments for the error message
	 */
	public ValidationError(String field, String errorCode, String defaultMessage, Object...messageArguments) {
		this.field = field;
		this.errorCode = errorCode;
		this.defaultMessage = defaultMessage;
		this.messageArguments = messageArguments;
	}
	
	/**
	 * Get the property name that failed the validation.
	 */
	public String getField() { return field; }
	
	/**
	 * Get an error code to be used as a lookup key in a dictionary to resolve
	 * the error message. 
	 */
	public String getErrorCode() { return errorCode; }
	
	/**
	 * Set the error code to be used as a lookup key in a dictionary to resolve
	 * the error message.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * Get an array of objects to be used in conjunction with the error message
	 * using {@link MessageFormat}
	 * 
	 * @return an Object array or an empty Object array if there are no message arguments.
	 */
	public Object[] getArguments() { return messageArguments; }
	
	/**
	 * Get a default error message to display if no error message can
	 * be resolved using the <code>errorCode</code>.
	 */
	public String getDefaultMessage() { return defaultMessage; }
	
	/**
	 * Set the default error message to be displayed if no error message can
	 * be resolved using the <code>errorCode</code>.
	 */
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	
	/**
	 * Returns the default message.
	 */
	@Override
	public String toString() { return "'" + defaultMessage + "'"; }
}
