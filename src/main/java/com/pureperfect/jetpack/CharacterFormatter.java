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

import java.io.IOException;
import java.io.Writer;

/**
 * Performs any last formatting of characters before serialization/marshalling.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public interface CharacterFormatter
{
	/**
	 * Perform any necessary formatting and write the results to the output.
	 * 
	 * @param string
	 *            the string to write
	 * @param out
	 *            where to write to after formatting
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void write(CharSequence string, Writer out) throws IOException;
}
