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

/**
 * A class with a collection of utility methods.
 * 
 * @author Spiros Tzavellas
 */
abstract class Utils {
	
	private Utils() { }
	
	
	/**
	 * Convert the specified parameters into a string.
	 * 
	 * @return If a parameter is not specified, or it is null the the empty
	 *         string "" is returned. Else this method return a string representation
	 *         of the specified parameters.
	 */
	static String arrayToString(Object... objects) {
		if (objects == null)
			return "";

		StringBuilder sb = new StringBuilder();
		for (Object o: objects) {
			sb.append(o).append(", ");
		}
		if (sb.length() <= 2) {
			return "";
		}
		sb.delete(sb.length() - 2, sb.length());
		
		return sb.toString();
	}
}
