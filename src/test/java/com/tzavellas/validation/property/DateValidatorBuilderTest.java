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
import static com.tzavellas.validation.property.Validators.date;

import java.util.Date;

import org.junit.Test;

/**
 * Unit test for DateValidatorBuilder
 * 
 * @author Spiros Tzavellas
 */
public class DateValidatorBuilderTest extends LinkedValidatorBuilderTest {
	
	// now + 1h
	private Date FUTURE_DATE = new Date(new Date().getTime() + 216000000L);
	
	// 1000 sec after 1970
	private Date PAST_DATE = new Date(1000);
	
	@Override
	protected DateValidatorBuilder validator() {
		return date("");
	}
	
	@Test
	public void futureAndPast() {
		assertValid(validator().future(), FUTURE_DATE);
		assertInvalid(validator().future(), PAST_DATE);
		
		assertValid(validator().past(), PAST_DATE);
		assertInvalid(validator().past(), FUTURE_DATE);
	}
	
	@Test
	public void beforeAndAfter() {
		assertValid(validator().before(FUTURE_DATE), PAST_DATE);
		assertInvalid(validator().before(PAST_DATE), FUTURE_DATE);
		assertInvalid(validator().before(FUTURE_DATE), FUTURE_DATE);
		
		assertValid(validator().after(PAST_DATE), FUTURE_DATE);
		assertInvalid(validator().after(FUTURE_DATE), PAST_DATE);
		assertInvalid(validator().after(FUTURE_DATE), FUTURE_DATE);
		
		// multiple validators
		assertValid(validator().future().after(PAST_DATE), FUTURE_DATE);
	}
}
