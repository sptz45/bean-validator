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

import java.util.regex.Pattern;

import com.tzavellas.validation.validators.Validator;

public abstract class StringValidators {
	
	public static final class Regex implements Validator<String> {
		private Pattern p;
	
		public Regex(String regex) {
			p = Pattern.compile(regex);
		}
	
		public boolean isValid(String value) {
			return p.matcher(value).matches();
		}
	}

	public static final class MinLength implements Validator<String> {
		private final int min;
	
		public MinLength(int min) {
			this.min = min;
		}
	
		public boolean isValid(String value) {
			return value.length() >= min;
		}
	}

	public static final class MaxLength implements Validator<String> {
		private final int max;
	
		public MaxLength(int max) {
			this.max = max;
		}
	
		public boolean isValid(String value) {
			return value.length() <= max;
		}
	}

	private StringValidators() { }

}
