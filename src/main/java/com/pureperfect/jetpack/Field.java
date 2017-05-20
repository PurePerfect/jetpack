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
 * Indicates that a field is mapped to be written to the output stream during
 * serialization.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class Field
{
	private final CharSequence name;

	private final Object value;

	/**
	 * Create a new mapped field.
	 * 
	 * @param name
	 *            the name of the field.
	 * @param value
	 *            the value of the field.
	 */
	public Field(final CharSequence name, final Object value)
	{
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * Get the name of the field.
	 * 
	 * @return the name of the field.
	 */
	public CharSequence getName()
	{
		return this.name;
	}

	/**
	 * Get the value of the field.
	 * 
	 * @return the value of the field.
	 */
	public Object getValue()
	{
		return this.value;
	}
}