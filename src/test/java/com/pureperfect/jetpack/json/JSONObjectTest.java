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

import com.pureperfect.jetpack.json.JSONField;
import com.pureperfect.jetpack.json.JSONObject;

import junit.framework.TestCase;

/**
 * 
 * @author J. Chris Folsom
 * @since 2.0
 * @version 2.0
 */
public class JSONObjectTest extends TestCase
{
	public void testToText()
	{
		final JSONObject obj = new JSONObject();

		final JSONField one = new JSONField("one", "one");
		final JSONField two = new JSONField("two", 2);

		obj.add(one);
		obj.add(two);

		assertEquals("{\"one\":\"one\",\"two\":2}", obj.toText().toString());
	}
}
