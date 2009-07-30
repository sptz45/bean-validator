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

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.tzavellas.validation.MessageResolver;
import com.tzavellas.validation.ValidationError;

/**
 * An adapter class from Spring's <code>MessageSource</code> to 
 * <code>MessageResolver</code>.
 * 
 * @author Spiros Tzavellas
 */
public class MessageSourceResolver implements MessageResolver {
	
	private final MessageSource messageSource;

	/**
	 * Construct a MessageSourceResolver.
	 * 
	 * @param source the MessageSource to delegate for resolving the
	 *               messages.
	 */
	public MessageSourceResolver(MessageSource source) {
		messageSource = source;
	}
	
	@Override
	public String getMessage(ValidationError error, Locale locale) {
		return messageSource.getMessage(
				error.getErrorCode(), error.getArguments(), error.getDefaultMessage(),
				locale);
	}
}
