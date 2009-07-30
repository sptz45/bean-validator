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

import com.tzavellas.validation.validators.Validator;

public abstract class IntegerValidators {

	public static final class Range implements Validator<Number> {
		private final long max;
		private final long min;
	
		public Range(long max, long min) {
			this.max = max;
			this.min = min;
		}
	
		public boolean isValid(Number value) {
			long v = value.longValue();
			return (min <= v) && (v <= max);
		}
	}

	public static final class Max implements Validator<Number> {
		private final long max;
	
		public Max(long max) {
			this.max = max;
		}
	
		public boolean isValid(Number value) {
			return value.longValue() <= max;
		}
	}

	public static final class Min implements Validator<Number> {
		private final long min;
	
		public Min(long min) {
			this.min = min;
		}
	
		public boolean isValid(Number value) {
			return value.longValue() >= min;
		}
	}

	private IntegerValidators() { }
	
}
