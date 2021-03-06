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
import static com.tzavellas.validation.property.Validators.floatingPoint;

import org.junit.Test;

/**
 * Unit test for FloatValidatorBuilder
 * 
 * @author Spiros Tzavellas
 */
public class FloatValidatorBuilderTest extends LinkedValidatorBuilderTest {
	
	@Override
	protected FloatValidatorBuilder validator() {
		return floatingPoint("");
	}
	
	@Test
	public void minMaxRange() {
		assertValid(validator().min(10.5), 100.5);
		assertInvalid(validator().min(100.5), 10.5);
		
		assertValid(validator().max(100.5), 10.5);
		assertInvalid(validator().max(10.5), 100.5);
		
		assertValid(validator().range(10, 100), 50);
		assertInvalid(validator().range(10, 100), 500);
	}
}
