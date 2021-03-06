/*
 * Copyright [2008] PurePerfect.com Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License.
 * 
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pureperfect.jetpack.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.pureperfect.jetpack.CharacterFormatter;
import com.pureperfect.jetpack.Field;
import com.pureperfect.jetpack.FieldReader;
import com.pureperfect.jetpack.Output;
import com.pureperfect.jetpack.SlightlyFasterAnnotationReader;
import com.pureperfect.jetpack.TypeConverter;

/**
 * Marshalls objects to an output stream as JSON data.
 * 
 * The JSONOut performs several steps before writing an object to the output
 * stream.
 * 
 * The rules for marshalling are as follows:
 * 
 * <ul>
 * <li>If the type is a primative type, Date or Calendar, it is converted using
 * the set {@link TypeConverter} and written to the output stream.</li>
 * <li>If the type is a string type, it is written to the output stream by the
 * {@link CharacterFormatter} to make sure that any necessary formatting (e.g.
 * escaping certain characters with '\') takes place before writing to the
 * output stream.</li>
 * <li>If the object is an object, it is written to the output stream between
 * braces {...} to signify an object in {@link JSON}.;</li>
 * <li>If the object is a {@link java.util.Map java.util.Map} it is written to
 * the output stream between braces {...} to signify an object in {@link JSON}
 * .</li>
 * <li>If the object is an instance of java.util.Collection or the object is an
 * array, it is delimited by brackets [...], to signify an array in
 * {@link JSON}.</li>
 * </ul>
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONOut implements Output
{
	private FieldReader fieldReader;

	private final Writer out;

	private TypeConverter typeConverter;

	private CharacterFormatter charFormat;

	/**
	 * Create a new {@link JSONOut} using the default {@link FieldReader} and
	 * {@link TypeConverter}.
	 * 
	 * @param out
	 *            The output stream to use.
	 * @param cs
	 *            The character set to use.
	 */
	public JSONOut(final OutputStream out, final Charset cs)
	{
		this(new OutputStreamWriter(out, cs));
	}

	/**
	 * Create a new {@link JSONOut} using the default {@link FieldReader} and
	 * {@link TypeConverter}.
	 * 
	 * @param out
	 *            the output stream to use.
	 * 
	 * @param charset
	 *            the charset to use
	 * @throws UnsupportedEncodingException
	 *             If the specified character set is not supported.
	 */
	public JSONOut(final OutputStream out, final String charset)
			throws UnsupportedEncodingException
	{
		this(new OutputStreamWriter(out, charset));
	}

	/**
	 * Create a new {@link JSONOut} using the default {@link FieldReader} and
	 * {@link TypeConverter}.
	 * 
	 * @param out
	 *            The writer to write {@link JSON} data to.
	 */
	public JSONOut(final Writer out)
	{
		this.out = out;
		this.fieldReader = new SlightlyFasterAnnotationReader(JSON.class);
		this.typeConverter = JSONTypeConverter.defaultInstance();
		this.charFormat = CharFormatter.singleton();
	}

	@Override
	public void close() throws IOException
	{
		this.out.close();
	}

	@Override
	public void flush() throws IOException
	{
		this.out.flush();
	}

	/**
	 * Get the current FieldReader.
	 * 
	 * @return the current FieldReader.
	 */
	public FieldReader getFieldReader()
	{
		return this.fieldReader;
	}

	/**
	 * Get the current {@link TypeConverter}.
	 * 
	 * @return the current {@link TypeConverter}.
	 */
	public TypeConverter getTypeConverter()
	{
		return this.typeConverter;
	}

	/**
	 * Set the {@link FieldReader}.
	 * 
	 * @param fieldReader
	 *            the {@link FieldReader}.
	 */
	public void setFieldReader(final FieldReader fieldReader)
	{
		this.fieldReader = fieldReader;
	}

	/**
	 * Set the {@link TypeConverter}.
	 * 
	 * @param converter
	 *            the new {@link TypeConverter}.
	 */
	public void setTypeConverter(final TypeConverter converter)
	{
		this.typeConverter = converter;
	}

	/**
	 * Get the current CharacterEscaper.
	 * 
	 * @return the current CharacterEscaper
	 */
	public CharacterFormatter getCharFormat()
	{
		return charFormat;
	}

	/**
	 * Set the CharacterEscaper.
	 * 
	 * @param characterHandler
	 *            the new CharacterEscaper
	 */
	public void setCharFormat(CharacterFormatter characterHandler)
	{
		this.charFormat = characterHandler;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void write(final Object o) throws IOException
	{
		/*
		 * Handle strings.
		 */
		if (o instanceof CharSequence)
		{
			this.out.append('\"');

			this.charFormat.write((CharSequence) o, this.out);

			this.out.append('\"');
		}

		/*
		 * Handle collections.
		 */
		else if (o instanceof Collection)
		{
			this.out.append('[');

			final Collection c = (Collection) o;

			int i = 0;
			final int stop = c.size();

			for (final Object obj : c)
			{
				this.write(obj);

				if (++i < stop)
				{
					this.out.append(",");
				}
			}

			this.out.append(']');
		}

		/*
		 * Handle null values.
		 */
		else if (o == null)
		{
			this.out.append(this.typeConverter.getNull());
		}

		/*
		 * Handle maps.
		 */
		else if (o instanceof Map)
		{
			this.out.append('{');

			final Map map = (Map) o;

			@SuppressWarnings("unchecked")
			final Set<Map.Entry<?, ?>> entries = map.entrySet();

			int i = 0;
			final int stop = map.size();

			for (final Map.Entry<?, ?> entry : entries)
			{
				this.out.append("\"");

				/*
				 * The key is converted to a string before writing.
				 */
				this.out.append(this.typeConverter.convert(entry.getKey()));
				this.out.append("\":");

				this.write(entry.getValue());

				if (++i < stop)
				{
					this.out.append(',');
				}
			}

			this.out.append('}');
		}

		/*
		 * Handle arrays.
		 */
		else if (o.getClass().isArray())
		{
			this.out.append('[');

			for (int i = 0, length = Array.getLength(o);;)
			{
				this.write(Array.get(o, i));

				if (++i < length)
				{
					this.out.append(',');
				}
				else
				{
					break;
				}
			}

			this.out.append(']');
		}

		/*
		 * Write mapped types.
		 */
		else
		{
			final Iterator<Field> fields = this.fieldReader.read(o);

			if (fields.hasNext())
			{
				this.out.append('{');

				while (fields.hasNext())
				{
					Field field = fields.next();

					this.out.append('"');
					this.out.append(field.getName());
					this.out.append("\":");

					final Object value = field.getValue();

					/*
					 * Recursive call to write out field value.
					 */
					this.write(value);

					if (fields.hasNext())
					{
						this.out.append(',');
					}
				}

				this.out.append('}');
			}
			else
			{
				/*
				 * It's an object, but it's not a mapped type recognized by our
				 * FieldReader. The converter should still do the best it can to
				 * convert the object.
				 */
				this.out.append(this.typeConverter.convert(o));
			}
		}
	}
}