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

import java.util.Iterator;

/**
 * Used by an {@link Output} to indicate which fields of an object will be
 * written to the output stream. Values intended to be written to the output
 * stream should be returned as mapped fields. Values not returned as mapped
 * fields will not be written to the output stream.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public interface FieldReader
{
	/**
	 * Returns the lists of mapped fields for the given object.
	 * 
	 * @param o
	 *            the object to find mapped fields for.
	 * @return The fields on the object that are mapped.
	 */
	public Iterator<Field> read(Object o);
}
 