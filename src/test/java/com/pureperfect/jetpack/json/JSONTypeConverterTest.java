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

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.pureperfect.jetpack.TypeConverter;
import com.pureperfect.jetpack.json.JSONTypeConverter;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONTypeConverterTest extends TestCase
{

	public void testConvertBoolean()
	{
		final TypeConverter converter = new JSONTypeConverter();

		assertEquals("true", converter.convert(true));
		assertEquals("false", converter.convert(false));
	}

	public void testConvertCalendar()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Calendar c = Calendar.getInstance();

		assertEquals("" + c.getTime().getTime(), converter.convert(c));
	}

	public void testConvertDate()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Date d = new Date();

		assertEquals("" + d.getTime(), converter.convert(d));
	}

	public void testConvertDouble()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Double d = new Double(23.5);

		assertEquals("23.5", converter.convert(d));

		assertEquals("23.5", converter.convert(23.5));
	}

	public void testConvertFloat()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Float f = new Float(23.5);

		assertEquals("23.5", converter.convert(f));

		assertEquals("23.5", converter.convert((float) 23.5));
	}

	public void testConvertInt()
	{
		final TypeConverter converter = new JSONTypeConverter();

		assertEquals("23", converter.convert(23));

		final Integer i = new Integer(23);

		assertEquals("23", converter.convert(i));

		final Object o = i;

		assertEquals("23", converter.convert(o));
	}

	public void testConvertLong()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Long l = new Long(23);

		assertEquals("23", converter.convert(l));

		assertEquals("23", converter.convert((long) 23));
	}

	public void testConvertNull()
	{
		final TypeConverter converter = new JSONTypeConverter();

		assertEquals("null", converter.convert(null));
	}

	public void testConvertShort()
	{
		final TypeConverter converter = new JSONTypeConverter();

		final Short s = (short) 23;

		assertEquals("23", converter.convert(s));

		assertEquals("23", converter.convert((short) 23));
	}

	public void testConvertString()
	{
		final TypeConverter converter = new JSONTypeConverter();

		assertEquals("rubadubdub", converter.convert("rubadubdub"));
	}
}