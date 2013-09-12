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

import com.pureperfect.jetpack.TypeConverter;

/**
 * Represents a generic JSONMapped field. A field has a name and a value.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONField implements JSONData
{
	private CharSequence name;

	private Object value;

	/**
	 * Create a new JSONField.
	 * 
	 * @param name
	 *            the name of the field.
	 * @param value
	 *            the field value.
	 */
	public JSONField(final CharSequence name, final Object value)
	{
		this.name = name;
		this.value = value;
	}

	/**
	 * Get the name of this field.
	 * 
	 * @return the name of this field
	 */
	public CharSequence getName()
	{
		return this.name;
	}

	/**
	 * Get the value.
	 * 
	 * @return the value
	 */
	public Object getValue()
	{
		return this.value;
	}

	/**
	 * Set the name for this field.
	 * 
	 * @param name
	 *            the name for this field
	 */
	public void setName(final CharSequence name)
	{
		this.name = name;
	}

	/**
	 * Set the value for this field.
	 * 
	 * @param value
	 *            the value for this field
	 */
	public void setValue(final Object value)
	{
		this.value = value;
	}

	/**
	 * Same as {@link #toText()}, except returns a string.
	 */
	@Override
	public String toString()
	{
		return this.toText().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence toText()
	{
		return this.toText(JSONData.DEFAULT_TYPE_CONVERTER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence toText(final TypeConverter typeConverter)
	{
		/*
		 * Get the value first to calculate length so we don't have to resize
		 * the StringBuilder.
		 */
		CharSequence convertedValue = null;

		/*
		 * Use the TypeConverter if specified. If the TypeConverter is the
		 * default TypeConverter, this if statement is merely a performance
		 * tweak. The results should be the same but passing a JSONObject to the
		 * default type converter will result in the creation of a String, so we
		 * call toText instead.
		 */
		if (this.value instanceof JSONData)
		{
			convertedValue = ((JSONData) this.value).toText(typeConverter);
		}
		else
		{
			convertedValue = typeConverter.convert(this.value);
		}

		int length = convertedValue.length() + this.name.length();

		StringBuilder builder = null;

		if (this.value instanceof CharSequence)
		{
			length += 5;
			builder = new StringBuilder(length);
			builder.append('"');
			builder.append(this.name);
			builder.append('"');
			builder.append(':');
			builder.append('"');
			builder.append(convertedValue);
			builder.append('"');
		}
		else
		{
			length += 3;
			builder = new StringBuilder(length);
			builder.append('"');
			builder.append(this.name);
			builder.append('"');
			builder.append(':');
			builder.append(convertedValue);
		}

		return builder;
	}
}