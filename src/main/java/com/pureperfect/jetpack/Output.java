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

/**
 * Define writers for marshalling objects.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public interface Output
{
	/**
	 * Close the output stream.
	 * 
	 * @throws IOException
	 *             if an error occurs
	 */
	public void close() throws IOException;

	/**
	 * Flush the output stream.
	 * 
	 * @throws IOException
	 *             if an error occurs
	 */
	public void flush() throws IOException;

	/**
	 * Write an object to the output stream.
	 * 
	 * @throws IOException
	 *             if an error occurs
	 */
	public void write(Object o) throws IOException;
}