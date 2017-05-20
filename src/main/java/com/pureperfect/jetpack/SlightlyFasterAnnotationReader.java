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
package com.pureperfect.jetpack;

import java.lang.reflect.Method;
import java.util.Iterator;

import com.pureperfect.jetpack.json.JSON;

/**
 * Checks for mapped fields on objects by looking for the {@link JSON}
 * annotation on getter methods. An instance of this class will return a
 * {@link Field} for all of the methods in the object that meet the following
 * criteria;
 * 
 * <ol>
 * <li>Method starts with the prefix "get" as in "getName()".</li>
 * <li>Method takes no parameters.</li>
 * <li>Method has a {@link JSON} annotation.</li>
 * </ol>
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 * @see JSON
 */
public class SlightlyFasterAnnotationReader implements FieldReader
{
	private static final Object[] NO_ARGS = new Object[] {};

	private static final SlightlyFasterAnnotationReader defaultInstance = new SlightlyFasterAnnotationReader();

	private SlightlyFasterAnnotationReader()
	{
		// hide me
	}

	/**
	 * Singleton.
	 * 
	 * @return Singleton.
	 */
	public static final SlightlyFasterAnnotationReader singleton()
	{
		return defaultInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Field> read(final Object o)
	{
		return new Iter(o);
	}

	/**
	 * Appears to provide a slight (10%?) performance boost over the original.
	 * 
	 * @author J. Chris Folsom
	 * @version 2.0.2
	 * @since 2.0.2
	 */
	private class Iter implements Iterator<Field>
	{
		private Field next;

		private int i;

		private Object o;

		private Method[] methods;

		public Iter(Object o)
		{
			this.o = o;
			this.methods = o.getClass().getDeclaredMethods();
		}

		@Override
		public boolean hasNext()
		{
			if (this.next == null)
			{
				advance();
			}

			return this.next != null;
		}

		@Override
		public Field next()
		{
			if (this.next == null)
			{
				advance();
			}

			Field f = this.next;

			this.next = null;

			return f;
		}

		private void advance()
		{
			for (; i < methods.length; ++i)
			{
				Method m = methods[i];

				if (m.getDeclaredAnnotation(JSON.class) != null)
				{
					try
					{
						final Object value = m.invoke(o, NO_ARGS);

						final StringBuilder fieldName = new StringBuilder(
								m.getName());

						final char[] first = new char[1];

						fieldName.getChars(3, 4, first, 0);

						first[0] = Character.toLowerCase(first[0]);

						fieldName.replace(0, 4, new String(first));

						this.next = new Field(fieldName, value);
						break;
					}
					catch (final Exception e)
					{
						throw new SerializeException(e);
					}
				}
			}

			++i;
		}
	}
}