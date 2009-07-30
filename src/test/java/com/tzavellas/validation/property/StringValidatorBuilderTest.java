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

import static com.tzavellas.validation.TestHelper.assertInvalid;
import static com.tzavellas.validation.TestHelper.assertValid;
import static com.tzavellas.validation.property.Validators.string;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Unit test for StringValidatorBuilder
 * 
 * @author Spiros Tzavellas
 */
public class StringValidatorBuilderTest extends LinkedValidatorBuilderTest {
	
	@Override
	protected StringValidatorBuilder validator() {
		return string("");
	}
	
	@Test
	public void validators() {
		assertValid(validator().minLength(5), "12345");
		assertInvalid(validator().minLength(5), "1234");
		
		assertValid(validator().maxLength(5), "12345");
		assertInvalid(validator().maxLength(5), "123456");
		
		assertValid(validator().regex(".*ab.*"), "abcd");
		assertInvalid(validator().regex(".*ab.*"), "1234");
		
		assertValid(validator().creditCard(), "4417123456789113"); // number from the CreditCardValidatorTest
		assertInvalid(validator().creditCard(), "not a credit number");
		
		assertValid(validator().isbn(), "0-262-51087-1"); //SICP
		assertInvalid(validator().isbn(), "not an isbn");
		
		assertValid(validator().email(), "someone@example.org");
		assertInvalid(validator().email(), "not a valid email");
		
		assertValid(validator().url(), "http://www.tzavellas.com");
		assertInvalid(validator().url(), "not a valid url");
	}
	
	@Test
	public void testForEmptyString() {
		assertNull("Since this is not required the empty string is a valid value.",
				validator().url().validate(""));
		assertNotNull("Since this is required the empty string is not a valid value.",
				validator().required().url().validate(""));
	}
}
