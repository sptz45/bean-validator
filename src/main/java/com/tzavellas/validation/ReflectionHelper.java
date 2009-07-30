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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


abstract class ReflectionHelper {
	
	private ReflectionHelper() { }
	
	private static ConcurrentHashMap<CacheKey, Method> propertyReaderCache = new ConcurrentHashMap<CacheKey, Method>();
	
	static Object readProperty(Object target, String propertyPath) {
		if (target == null) {
			throw new IllegalArgumentException("The argument 'target' cannot be null");
		}
		if (propertyPath == null) {
			throw new IllegalArgumentException("The argument 'propertyPath' cannot be null");
		}
		
		if (! isNestedProperty(propertyPath)) {
			return readPropertyInternal(target, propertyPath);
		}
		
		String[] properties = propertyPath.split("\\.");
		Object tmpTarget = target;
		for (int i = 0; i < properties.length - 1; i++) {
			tmpTarget = readPropertyInternal(tmpTarget, properties[i]);
			if (tmpTarget == null) {
				throw new IllegalStateException("Found null in '" + properties[i] +
						"' while trying to read ' " + propertyPath + "'");
			}
		}
		
		return readPropertyInternal(tmpTarget, properties[properties.length -1]);
	}
	
	private static Object readPropertyInternal(Object target, String property) {
		Class<?> cls = target.getClass();
		CacheKey key = new CacheKey(cls, property);
		
		Method getter = propertyReaderCache.get(key);
		if (getter != null) {
			return invoke(getter, target);
		}
		
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(cls);
		} catch (IntrospectionException e) {
			throw new RuntimeException("Could not find BeanInfo for class '" + cls + "'", e);
		}
		
		for (PropertyDescriptor desc: beanInfo.getPropertyDescriptors())
			if (desc.getName().equals(property)) {
				getter = desc.getReadMethod();
				propertyReaderCache.put(key, getter);
				break;
			}
		
		flushIntrospector(cls);
		
		if (getter == null) {
			throw new IllegalArgumentException(
					"Could not find property '" + property + "' in class ' " + cls + "'");
		}
		
		return invoke(getter, target);
	}
	
	
	private static boolean isNestedProperty(String propertyPath) {
		return propertyPath.indexOf(".") != -1;
	}
	
	
	/**
	 * Invoke the specified method on the specified object with the specified parameters.
	 */
	private static Object invoke(Method m, Object obj, Object... args) {
		try {
			return m.invoke(obj, args);
			
		} catch (IllegalAccessException e) {
			throw new RuntimeException("The method '" + m + "' is not accessible", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Exception while invoking method '" + m + "'", e.getTargetException());
		}
	}
	
	
	/**
	 * Flush java.beans.Introspector caches for the specified class
	 * and all super-classes.
	 */
	private static void flushIntrospector(Class<?> cls) {
		Class<?> flushMe = cls;
		do {
			Introspector.flushFromCaches(flushMe);
			flushMe = flushMe.getSuperclass();
		} while (flushMe != null);
	}
	
	
	/**
	 * To be used as a key in the method cache
	 */
	private static class CacheKey {
		
		private Class<?> clazz;
		private String prop;
		
		/** We assume cls and property are not null */
		CacheKey(Class<?> cls, String property) {
			clazz = cls;
			prop = property;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (! (obj instanceof CacheKey))
				return false;

			CacheKey that = (CacheKey) obj;
			return this.clazz.equals(that.clazz) && this.prop.equals(that.prop);
		}
		
		@Override
		public int hashCode() {
			return clazz.hashCode() + prop.hashCode();
		}	
	}
}
