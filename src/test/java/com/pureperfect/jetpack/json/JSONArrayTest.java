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

import com.pureperfect.jetpack.json.JSONArray;

import junit.framework.TestCase;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONArrayTest extends TestCase
{
	public void testEmptyArray()
	{
		final JSONArray<Integer> array = new JSONArray<Integer>();

		assertEquals("[]", array.toText().toString());
	}

	public void testToText()
	{
		final JSONArray<Integer> array = new JSONArray<Integer>();

		array.add(1);
		array.add(2);
		array.add(3);

		assertEquals("[1,2,3]", array.toText().toString());
	}
}
