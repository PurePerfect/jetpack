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
package com.pureperfect.jetpack.json;

import com.pureperfect.jetpack.TypeConverter;

/**
 * Represents data in JavaScript Object Notation.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public interface JSONData
{
	/**
	 * {@link JSONTypeConverter}
	 */
	public static final TypeConverter DEFAULT_TYPE_CONVERTER = new JSONTypeConverter();

	/**
	 * Convert this JSONMapped object to text using the default {@link TypeConverter}.
	 * 
	 * @return the {@link JSONData} as text.
	 */
	public CharSequence toText();

	/**
	 * Convert this JSONMapped object to text using the specified type converter.
	 * 
	 * @param converter
	 *            the converter to use.
	 * @return the {@link JSONData} as text.
	 */
	public CharSequence toText(TypeConverter converter);
}
