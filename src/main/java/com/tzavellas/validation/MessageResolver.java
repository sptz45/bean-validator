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

import java.util.Locale;

/**
 * Resolve an error message for a <code>ValidationError</code>.
 * 
 * @see ValidationResult
 * @see ValidationError
 * 
 * @author Spiros Tzavellas
 */
public interface MessageResolver {
	
	/**
	 * Resolve the error message for the specified <code>ValidationError</code>.
	 * 
	 * @param error the <code>ValidationError</code>
	 * @param locale the locale to use. If null then the implementation is responsible
	 *               to determine which locale to use
	 * 
	 * @return an appropriate message for the specified Locale, else the default message.
	 */
	String getMessage(ValidationError error, Locale locale);
}
