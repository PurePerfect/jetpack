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
import com.pureperfect.jetpack.json.JSONField;
import com.pureperfect.jetpack.json.JSONObject;

import junit.framework.TestCase;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONFieldTest extends TestCase
{
	public void testToTextArray()
	{
		final JSONArray<Integer> array = new JSONArray<Integer>();

		array.add(1);
		array.add(2);
		array.add(3);

		final JSONField field = new JSONField("myarray", array);

		assertEquals("\"myarray\":[1,2,3]", field.toText().toString());
	}

	public void testToTextCharSequence()
	{
		final JSONField field = new JSONField("myfield", "myvalue");

		assertEquals("\"myfield\":\"myvalue\"", field.toText().toString());
	}

	public void testToTextJSONObject()
	{
		final JSONField field = new JSONField("myfield", "myvalue");

		final JSONObject obj = new JSONObject();

		obj.add(field);

		final JSONField two = new JSONField("obj", obj);

		assertEquals("\"obj\":{\"myfield\":\"myvalue\"}", two.toText().toString());
	}

	public void testToTextNumeric()
	{
		final JSONField field = new JSONField("myfield", new Integer(23));

		assertEquals("\"myfield\":23", field.toText().toString());
	}
}