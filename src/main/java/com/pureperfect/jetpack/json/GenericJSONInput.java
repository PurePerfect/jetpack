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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.pureperfect.jetpack.Input;

/**
 * Unmarshall inbound data into generic {@link JSONData}.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class GenericJSONInput implements Input
{
	private static boolean isNull(final CharSequence cs)
	{
		return cs.length() == 4 && cs.charAt(0) == 'n' && cs.charAt(1) == 'u'
				&& cs.charAt(2) == 'l' && cs.charAt(3) == 'l';
	}

	private Reader in;

	/**
	 * Create a new unmarshaller.
	 * 
	 * @param in
	 *            the stream to read from
	 * @param charset
	 *            the expected character set
	 */
	public GenericJSONInput(final InputStream in, final Charset charset)
	{
		this(new InputStreamReader(in, charset));
	}

	/**
	 * Create a new unmarshaller.
	 * 
	 * @param in
	 *            the stream to read from
	 * @param charset
	 *            the expected character set
	 * 
	 * @throws UnsupportedEncodingException
	 *             if the charset is not supported.
	 */
	public GenericJSONInput(final InputStream in, final String charset)
			throws UnsupportedEncodingException
	{
		this(new InputStreamReader(in, charset));
	}

	/**
	 * Create a new unmarshaller from the specified reader.
	 * 
	 * @param in
	 *            the reader
	 */
	public GenericJSONInput(final Reader in)
	{
		if (in.markSupported())
		{
			this.in = in;
		}
		else
		{
			this.in = new BufferedReader(in);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException
	{
		this.in.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object read() throws IOException
	{
		final char current = (char) this.in.read();

		if (current == '{')
		{
			return this.readObject();
		}
		else if (current == '[')
		{
			return this.readArray();
		}
		else
		{
			throw new MalformedJSONException("Was expecting either { or [");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	private Object readArray() throws IOException
	{
		final JSONArray<Object> array = new JSONArray<Object>();

		this.eatWhitespace();

		this.in.mark(1);

		char current = (char) this.in.read();

		if (current == ']')
			return array;

		in.reset();

		for (; current != ']'; current = (char) this.in.read())
		{
			final Object value = this.readValue();

			System.out.println();

			array.add(value);
		}

		return array;
	}

	/**
	 * {@inheritDoc}
	 */
	private void eatWhitespace() throws IOException
	{
		in.mark(1);

		while (true)
		{
			char ch = (char) in.read();

			if (Character.isWhitespace(ch))
			{
				in.mark(1);
			}
			else
			{
				in.reset();
				break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	private JSONField readField() throws IOException
	{
		this.eatWhitespace();

		char current = (char) this.in.read();

		if (current != '"')
		{
			throw new MalformedJSONException(
					"Expecting '\"' at start of field.");
		}

		final StringBuilder fieldName = new StringBuilder(8);

		for (current = (char) in.read(); current != '"'; current = (char) this.in
				.read())
		{
			fieldName.append(current);
		}

		this.eatWhitespace();

		current = (char) in.read();

		if (current != ':')
		{
			throw new MalformedJSONException("Expecting ':' after field: "
					+ fieldName);
		}

		final Object result = this.readValue();

		this.eatWhitespace();

		return new JSONField(fieldName, result);
	}

	/**
	 * {@inheritDoc}
	 */
	private Object readNumeric() throws IOException
	{
		final StringBuilder value = new StringBuilder(24);

		boolean containsDecimal = false;

		while (true)
		{
			this.in.mark(Integer.MAX_VALUE);

			final char current = (char) this.in.read();

			if (current == ',' || current == '}' || current == ']')
			{
				this.in.reset();
				break;
			}
			else if (current == '.')
			{
				containsDecimal = true;
			}

			value.append(current);
		}

		if (isNull(value))
		{
			return null;
		}
		else
		{
			if (containsDecimal)
			{
				return new Double(value.toString().trim());
			}
			else if (value.length() > 9)
			{
				return new Long(value.toString().trim());
			}
			else
			{
				return new Integer(value.toString().trim());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	private Object readObject() throws IOException
	{
		final JSONObject object = new JSONObject();

		this.eatWhitespace();

		this.in.mark(1);

		char current = (char) in.read();

		// Check to see if it is an empty object
		if (current == '}')
			return object;

		this.in.reset();

		for (; current != '}'; current = (char) this.in.read())
		{
			final JSONField field = this.readField();

			object.add(field);
		}

		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	private Object readString() throws IOException
	{
		/*
		 * We need to back up one since we will be checking for previous char in
		 * the loop.
		 */
		this.in.reset();
		final StringBuilder value = new StringBuilder(24);

		char current = (char) this.in.read();
		char previous;

		while (true)
		{
			previous = current;
			current = (char) this.in.read();

			if (current == '"' && previous != '\\')
			{
				break;
			}
			else
			{
				value.append(current);
			}
		}

		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	private Object readValue() throws IOException
	{
		this.eatWhitespace();

		this.in.mark(1);

		Object result = null;

		final char current = (char) this.in.read();

		if (current == '{')
		{
			result = this.readObject();
		}
		else if (current == '[')
		{
			result = this.readArray();
		}
		else if (current == '"')
		{
			result = this.readString();
		}
		else
		{
			this.in.reset();
			result = this.readNumeric();
		}

		this.eatWhitespace();

		return result;
	}
}