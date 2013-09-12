/*
 * Copyright [2008] PurePerfect.com
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * 
 * You may obtain a copy of the License at 
 * 		http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. 
 * 
 * See the License for the specific language governing permissions
 * and limitations under the License. 
 */
package com.pureperfect.jetpack;

/**
 * Interface for converting Java types to characters.
 * 
 * @author J. Chris Folsom
 * @version 2.0.1
 * @since 2.0
 */
public interface TypeConverter
{
	/**
	 * Convert a boolean to a string.
	 * 
	 * @param b
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(boolean b);

	/**
	 * Convert a byte to a string.
	 * 
	 * @param b
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(byte b);

	/**
	 * Convert a double to a string.
	 * 
	 * @param d
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(double d);

	/**
	 * Convert a float to a string.
	 * 
	 * @param f
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(float f);

	/**
	 * Convert an integer to a string.
	 * 
	 * @param i
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(int i);

	/**
	 * Convert a long to a string.
	 * 
	 * @param l
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(long l);

	/**
	 * Convert an object to a string.
	 * 
	 * @param b
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(Object o);

	/**
	 * Convert a short to a string.
	 * 
	 * @param s
	 *            the value to convert
	 * @return the resulting string.
	 */
	public CharSequence convert(short s);

	/**
	 * Get a representation of <code>null</code>.
	 * 
	 * @return the resulting string
	 */
	public CharSequence getNull();
}