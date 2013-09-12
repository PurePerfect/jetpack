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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.util.Iterator;

import junit.framework.TestCase;

import com.pureperfect.jetpack.Input;
import com.pureperfect.jetpack.json.JSONArray;
import com.pureperfect.jetpack.json.GenericJSONInput;
import com.pureperfect.jetpack.json.JSONField;
import com.pureperfect.jetpack.json.JSONObject;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONUnmarshallerTest extends TestCase
{
	private InputStream in;

	private OutputStream out;

	@Override
	public void setUp() throws Exception
	{
		this.out = new PipedOutputStream();
		this.in = new PipedInputStream((PipedOutputStream) this.out);
	}

	@SuppressWarnings("unchecked")
	public void testArrayWithLeadingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[   [\"foo\"   ,\"bar\"  ], [1,2]]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		JSONArray<Object> child1 = (JSONArray<Object>) jo.get(0);
		JSONArray<Object> child2 = (JSONArray<Object>) jo.get(1);

		assertEquals("foo", child1.get(0).toString());
		assertEquals("bar", child1.get(1).toString());

		assertEquals(1, child2.get(0));
		assertEquals(2, child2.get(1));
	}

	@SuppressWarnings("unchecked")
	public void testArrayWithLeadingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":   [1,2,3],\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());

		JSONArray<Object> array = (JSONArray<Object>) field1.getValue();

		assertEquals(3, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	@SuppressWarnings("unchecked")
	public void testArrayWithTrailingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[[\"foo\"   ,\"bar\"  ]   ,[1,2]       ]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		JSONArray<Object> child1 = (JSONArray<Object>) jo.get(0);
		JSONArray<Object> child2 = (JSONArray<Object>) jo.get(1);

		assertEquals("foo", child1.get(0).toString());
		assertEquals("bar", child1.get(1).toString());

		assertEquals(1, child2.get(0));
		assertEquals(2, child2.get(1));
	}

	@SuppressWarnings("unchecked")
	public void testArrayWithTrailingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":[1,2,3]   ,\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());

		JSONArray<Object> array = (JSONArray<Object>) field1.getValue();

		assertEquals(3, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	@SuppressWarnings("unchecked")
	public void testFieldWithLeadingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{     \"one\":[1,2,3]   ,\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());

		JSONArray<Object> array = (JSONArray<Object>) field1.getValue();

		assertEquals(3, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	public void testEmptyStringInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":\"value\",\"two\":\"\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals("value", field1.getValue().toString());
		assertEquals("two", field2.getName().toString());
		assertEquals("", field2.getValue().toString());
	}

	@SuppressWarnings("unchecked")
	public void testEmptyStringInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[\"value\", \"\"]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONArray<Object> array = (JSONArray<Object>) um.read();

		assertEquals("value", array.get(0).toString());
		assertEquals("", array.get(1).toString());
	}

	@SuppressWarnings("unchecked")
	public void testFieldWithTrailingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\"    :[1,2,3]   ,\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());

		JSONArray<Object> array = (JSONArray<Object>) field1.getValue();

		assertEquals(3, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	public void testNumericWithLeadingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[     110.23456799,   2023456.789]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Double(110.23456799), i.next());
		assertEquals(new Double(2023456.789), i.next());
	}

	public void testNumericWithLeadingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\": 1,\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals(1, field1.getValue());

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	public void testNumericWithTrailingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[110.23456799   , 2023456.789  ]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Double(110.23456799), i.next());
		assertEquals(new Double(2023456.789), i.next());
	}

	public void testNumericWithTrailingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\": 3,\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals(3, field1.getValue());

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	@SuppressWarnings("unchecked")
	public void testObjectWithLeadingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[   {\"one\":1,\"two\":2}, [3,4]]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		JSONObject child1 = (JSONObject) jo.get(0);
		JSONArray<Object> child2 = (JSONArray<Object>) jo.get(1);

		JSONField field1 = child1.get(0);
		JSONField field2 = child1.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals(1, field1.getValue());
		assertEquals("two", field2.getName().toString());
		assertEquals(2, field2.getValue());

		assertEquals(3, child2.get(0));
		assertEquals(4, child2.get(1));
	}

	public void testWhiteSpaceInFieldName() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one  \":1,\"two\":2}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final JSONObject o = (JSONObject) um.read();

		JSONField one = o.get(0);
		JSONField two = o.get(1);

		assertEquals("one  ", one.getName().toString());
		assertEquals(1, one.getValue());
		assertEquals("two", two.getName().toString());
		assertEquals(2, two.getValue());
	}

	public void testObjectWithLeadingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\": {\"three\":3, \"four\":4},\"two\":\"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());

		JSONObject one = (JSONObject) field1.getValue();

		assertEquals(2, one.size());

		JSONField three = one.get(0);
		JSONField four = one.get(1);

		assertEquals("three", three.getName().toString());
		assertEquals(3, three.getValue());
		assertEquals("four", four.getName().toString());
		assertEquals(4, four.getValue());

		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	@SuppressWarnings("unchecked")
	public void testObjectWithTrailingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[   {\"one\":1,\"two\":2}    , [3,4]]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		JSONObject child1 = (JSONObject) jo.get(0);
		JSONArray<Object> child2 = (JSONArray<Object>) jo.get(1);

		JSONField field1 = child1.get(0);
		JSONField field2 = child1.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals(1, field1.getValue());
		assertEquals("two", field2.getName().toString());
		assertEquals(2, field2.getValue());

		assertEquals(3, child2.get(0));
		assertEquals(4, child2.get(1));
	}

	@SuppressWarnings("unchecked")
	public void testObjectWithTrailingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{   \"field\":{\"one\":1,\"two\":2}    ,\"fieldTwo\":[3,4]}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONObject jo = (JSONObject) o;

		assertEquals(2, jo.size());

		JSONField one = jo.get(0);
		JSONField two = jo.get(1);

		assertEquals("field", one.getName().toString());

		JSONObject obj = (JSONObject) one.getValue();

		JSONField child1 = obj.get(0);
		JSONField child2 = obj.get(1);

		assertEquals("one", child1.getName().toString());
		assertEquals(1, child1.getValue());

		assertEquals("two", child2.getName().toString());
		assertEquals(2, child2.getValue());

		assertEquals("fieldTwo", two.getName().toString());

		JSONArray<Object> array = (JSONArray<Object>) two.getValue();

		assertEquals(2, array.size());

		assertEquals(3, array.get(0));
		assertEquals(4, array.get(1));
	}

	public void testReadArrayInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799,[1,2,3],2023456789]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(3, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Long(11023456799L), i.next());

		@SuppressWarnings("unchecked")
		final JSONArray<Object> array2 = (JSONArray<Object>) i.next();

		assertEquals(3, array2.size());

		assertEquals(1, array2.get(0));
		assertEquals(2, array2.get(1));
		assertEquals(3, array2.get(2));

		assertEquals(new Long(2023456789), i.next());
	}

	public void testReadArrayInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":null,\"two\":[1,2,3]}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final JSONObject o = (JSONObject) um.read();

		final JSONField one = o.get(0);

		assertEquals("one", one.getName().toString());
		assertNull(one.getValue());

		final JSONField two = o.get(1);

		@SuppressWarnings("unchecked")
		final JSONArray<Object> array = (JSONArray<Object>) two.getValue();

		assertEquals("two", two.getName().toString());

		assertEquals(3, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));
	}

	public void testReadDoubleInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[110.23456799,2023456.789]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Double(110.23456799), i.next());
		assertEquals(new Double(2023456.789), i.next());
	}

	public void testReadDoubleInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":110234567.99,\"two\":20.23456789}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONObject jo = (JSONObject) o;

		assertEquals(2, jo.size());

		final Iterator<JSONField> i = jo.iterator();

		final JSONField one = i.next();
		final JSONField two = i.next();

		assertEquals("one", one.getName().toString());
		assertEquals("two", two.getName().toString());

		assertEquals(new Double(110234567.99), one.getValue());
		assertEquals(new Double(20.23456789), two.getValue());
	}

	public void testReadIntegerInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[110,2023456]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Integer(110), i.next());
		assertEquals(new Integer(2023456), i.next());
	}

	public void testReadIntegerInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":1,\"two\":2}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		assertEquals("{\"one\":1,\"two\":2}", o.toString());

		final JSONObject jo = (JSONObject) o;

		assertEquals(2, jo.size());

		final Iterator<JSONField> i = jo.iterator();

		final JSONField one = i.next();
		final JSONField two = i.next();

		assertEquals("one", one.getName().toString());
		assertEquals("two", two.getName().toString());

		assertEquals(new Integer(1), one.getValue());
		assertEquals(new Integer(2), two.getValue());
	}

	public void testReadLongInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799,2023456789]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Long(11023456799L), i.next());
		assertEquals(new Long(2023456789), i.next());
	}

	public void testReadLongInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":11023456799,\"two\":2023456789}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		final JSONObject jo = (JSONObject) o;

		assertEquals(2, jo.size());

		final Iterator<JSONField> i = jo.iterator();

		final JSONField one = i.next();
		final JSONField two = i.next();

		assertEquals("one", one.getName().toString());
		assertEquals("two", two.getName().toString());

		assertEquals(new Long(11023456799L), one.getValue());
		assertEquals(new Long(2023456789), two.getValue());
	}

	public void testIntegerNumberFormatExceptionInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799, 1234asdf56]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		try
		{
			um.read();
			fail();
		}
		catch(NumberFormatException e)
		{
			assertTrue(e.getMessage().contains("1234asdf56"));
		}
	}
	
	public void testIntegerNumberFormatExceptionInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":11023456799, \"two\":1234asdf56]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		try
		{
			um.read();
			fail();
		}
		catch (NumberFormatException e)
		{
			assertTrue(e.getMessage().contains("1234asdf56"));
		}
	}
	
	public void testFloatingPointNumberFormatExceptionInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":11023456799, \"two\":1234.asdf56}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		try
		{
			um.read();
			fail();
		}
		catch (NumberFormatException e)
		{
			assertTrue(e.getMessage().contains("1234.asdf56"));
		}
	}
	
	public void testFloatingPointNumberFormatExceptionInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799, 1234.asdf56]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		try
		{
			um.read();
			fail();
		}
		catch (NumberFormatException e)
		{
			assertTrue(e.getMessage().contains("1234.asdf56"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testEmptyArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[ ]");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONArray<Object> object = (JSONArray<Object>) um.read();
		
		assertEquals(0, object.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testEmptyArrayInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[1,2,[  ] ]");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONArray<Object> object = (JSONArray<Object>) um.read();
		
		assertEquals(3, object.size());
		
		assertEquals(1, object.get(0));
		assertEquals(2, object.get(1));
		
		JSONArray<Object> child = (JSONArray<Object>) object.get(2);
		
		assertEquals(0, child.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testEmptyArrayInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":1,\"two\":[  ] }");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject outer = (JSONObject) um.read();
		
		assertEquals(2, outer.size());
		
		JSONField one = outer.get(0);
		JSONField two = outer.get(1);
		
		assertEquals("one", one.getName().toString());
		assertEquals(1, one.getValue());
		
		assertEquals("two", two.getName().toString());
		
		JSONArray<Object> arr = (JSONArray<Object>) two.getValue();
		
		assertEquals(0, arr.size());
	}
	
	public void testEmptyObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{  }");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONObject object = (JSONObject) um.read();
		
		assertEquals(0, object.size());
	}
	
	public void testEmptyObjectInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{  \"one\" : {  }}");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONObject object = (JSONObject) um.read();
		
		assertEquals(1, object.size());
		
		JSONField field = object.get(0);
		
		assertEquals("one", field.getName().toString());
		
		JSONObject value = (JSONObject) field.getValue();
		
		assertEquals(0, value.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testWhiteSpaceAfterNumericValueInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[ 1,2, {  }]");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONArray<Object> array =  (JSONArray<Object>) um.read();
		
		assertEquals(3, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		
		JSONObject obj = (JSONObject) array.get(2);
		
		assertEquals(0, obj.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testEmptyObjectInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[ 1,2, {  }]");

		myout.flush();
		
		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));
		
		JSONArray<Object> array =  (JSONArray<Object>) um.read();
		
		assertEquals(3, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		
		JSONObject obj = (JSONObject) array.get(2);
		
		assertEquals(0, obj.size());
	}

	public void testReadMultipleArrays() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799,null,2023456789][1,2,3]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(3, jo.size());

		Iterator<Object> i = jo.iterator();

		assertEquals(new Long(11023456799L), i.next());
		assertNull(i.next());
		assertEquals(new Long(2023456789), i.next());

		o = um.read();

		// read second array
		@SuppressWarnings("unchecked")
		final JSONArray<Object> array2 = (JSONArray<Object>) o;

		assertEquals(3, array2.size());

		i = array2.iterator();

		assertEquals(new Integer(1), i.next());
		assertEquals(new Integer(2), i.next());
		assertEquals(new Integer(3), i.next());
	}

	public void testReadMultipleObjects() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":1,\"two\":2}{\"one\":3,\"two\":4}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		Object o = um.read();

		final JSONObject jo = (JSONObject) o;

		assertEquals(2, jo.size());

		Iterator<JSONField> i = jo.iterator();

		JSONField one = i.next();
		JSONField two = i.next();

		assertEquals("one", one.getName().toString());
		assertEquals("two", two.getName().toString());

		assertEquals(new Integer(1), one.getValue());
		assertEquals(new Integer(2), two.getValue());

		// read second object
		o = um.read();

		final JSONObject obj2 = (JSONObject) o;

		assertEquals(2, obj2.size());

		i = obj2.iterator();

		one = i.next();
		two = i.next();

		assertEquals("one", one.getName().toString());
		assertEquals("two", two.getName().toString());

		assertEquals(new Integer(3), one.getValue());
		assertEquals(new Integer(4), two.getValue());
	}

	public void testReadNullInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[11023456799,null,2023456789]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(3, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals(new Long(11023456799L), i.next());
		assertNull(i.next());
		assertEquals(new Long(2023456789), i.next());

	}

	public void testReadNullInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":null}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final JSONObject o = (JSONObject) um.read();

		final JSONField one = o.get(0);

		assertEquals("one", one.getName().toString());
		assertNull(one.getValue());
	}

	public void testReadObjectInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[1,{\"two\":2},3]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		@SuppressWarnings("unchecked")
		final JSONArray<Object> array = (JSONArray<Object>) um.read();

		assertEquals(3, array.size());

		final Object one = array.get(0);
		final JSONObject two = (JSONObject) array.get(1);
		final Object three = array.get(2);

		assertEquals(1, one);

		assertEquals(1, two.size());

		final JSONField twoF = two.get(0);

		assertEquals("two", twoF.getName().toString());
		assertEquals(2, twoF.getValue());

		assertEquals(3, three);
	}

	public void testReadObjectInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":1,\"two\":{\"one\":3}}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final JSONObject o = (JSONObject) um.read();

		final JSONField one = o.get(0);
		final JSONField two = o.get(1);

		assertEquals("one", one.getName().toString());
		assertEquals(1, one.getValue());
		assertEquals("two", two.getName().toString());

		final JSONObject child = (JSONObject) two.getValue();

		assertEquals(1, child.size());

		final JSONField childField = child.get(0);

		assertEquals("one", childField.getName().toString());
		assertEquals(3, childField.getValue());
	}

	public void testReadStringInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[\"foo\",123,\"bar\"]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		@SuppressWarnings("unchecked")
		final JSONArray<Object> array = (JSONArray<Object>) um.read();

		assertEquals(3, array.size());

		assertEquals("foo", array.get(0).toString());
		assertEquals(123, array.get(1));
		assertEquals("bar", array.get(2).toString());
	}

	public void testReadStringInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":\"foo\",\"two\":\"bar\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final JSONObject obj = (JSONObject) um.read();

		final JSONField one = obj.get(0);
		final JSONField two = obj.get(1);

		assertEquals("one", one.getName().toString());
		assertEquals("foo", one.getValue().toString());
		assertEquals("two", two.getName().toString());
		assertEquals("bar", two.getValue().toString());
	}

	public void testEscapedCharsInString()
	{
		// TESTME escaped chars in string...
	}

	public void testStringWithLeadingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[    \"foo\"   ,   \"bar\"  ]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals("foo", i.next().toString());
		assertEquals("bar", i.next().toString());
	}

	public void testStringWithLeadingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":      \"one\",\"two\":   \"two\"}");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals("one", field1.getValue().toString());
		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}

	public void testStringWithTrailingWhiteSpaceInArray() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("[\"foo\"   ,\"bar\"  ]");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		final Object o = um.read();

		@SuppressWarnings("unchecked")
		final JSONArray<Object> jo = (JSONArray<Object>) o;

		assertEquals(2, jo.size());

		final Iterator<Object> i = jo.iterator();

		assertEquals("foo", i.next().toString());
		assertEquals("bar", i.next().toString());
	}

	public void testStringWithTrailingWhiteSpaceInObject() throws IOException
	{
		final Writer myout = new OutputStreamWriter(this.out);

		myout.write("{\"one\":\"one\"    ,\"two\":\"two\"    }");

		myout.flush();

		final Input um = new GenericJSONInput(new InputStreamReader(
				this.in));

		JSONObject obj = (JSONObject) um.read();

		JSONField field1 = obj.get(0);
		JSONField field2 = obj.get(1);

		assertEquals("one", field1.getName().toString());
		assertEquals("one", field1.getValue().toString());
		assertEquals("two", field2.getName().toString());
		assertEquals("two", field2.getValue().toString());
	}
}