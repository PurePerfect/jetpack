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

import com.pureperfect.jetpack.TypeConverter;

/**
 * Converts types to character sequences. Dates and Calendars are converted to
 * time in milliseconds in the <a href="http://en.wikipedia.org/wiki/Unix_time">UNIX Epoch</a>.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONTypeConverter implements TypeConverter
{
	private static final JSONTypeConverter defaultInstance = new JSONTypeConverter();

	/**
	 * Singleton instance of this class.
	 * 
	 * @return the default instance.
	 */
	public static JSONTypeConverter defaultInstance()
	{
		return defaultInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final boolean b)
	{
		return String.valueOf(b);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final byte b)
	{
		return String.valueOf(b);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final double d)
	{
		return String.valueOf(d);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final float f)
	{
		return String.valueOf(f);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final int i)
	{
		return String.valueOf(i);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final long l)
	{
		return String.valueOf(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final Object o)
	{
		if (o instanceof Date)
		{
			final Date d = (Date) o;
			return String.valueOf(d.getTime());
		}
		else if (o instanceof Calendar)
		{
			final Calendar c = (Calendar) o;

			return String.valueOf(c.getTime().getTime());
		}
		else
		{
			return String.valueOf(o);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence convert(final short s)
	{
		return String.valueOf(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence getNull()
	{
		return "null";
	}
}