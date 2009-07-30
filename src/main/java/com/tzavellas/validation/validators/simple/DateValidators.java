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
package com.tzavellas.validation.validators.simple;

import java.util.Date;

import com.tzavellas.validation.validators.Validator;

public abstract class DateValidators {
	
	public static final class After implements Validator<Date> {
		private final Date date;
	
		public After(Date date) {
			this.date = date;
		}
	
		public boolean isValid(Date value) {
			return value.after(date);
		}
	}

	public static final class Before implements Validator<Date> {
		private final Date date;
	
		public Before(Date date) {
			this.date = date;
		}
	
		public boolean isValid(Date value) {
			return value.before(date);
		}
	}

	public static final class Future implements Validator<Date> {
		public boolean isValid(Date value) {
			return new Date().before(value);
		}
	}

	public static final class Past implements Validator<Date> {
		public boolean isValid(Date value) {
			return new Date().after(value);
		}
	}

	private DateValidators() { }

}
