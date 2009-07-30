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

public class ObjectValidators {

	public static final class In<T> implements Validator<T> {
		private final Object[] objects;
	
		public In(Object... objects) {
			this.objects = objects;
		}
	
		public boolean isValid(T t) {
			for (Object object : objects)
				if (object.equals(t))
					return true;
			return false;
		}
	}

}
