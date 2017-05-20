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

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.pureperfect.jetpack.FieldReader;
import com.pureperfect.jetpack.Field;
import com.pureperfect.jetpack.SerializeException;

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
public class AnnotationReader implements FieldReader
{
	private static final Object[] NO_ARGS = new Object[] {};

	private static final AnnotationReader defaultInstance = new AnnotationReader();

	private AnnotationReader()
	{
		// hide me
	}

	/**
	 * Singleton.
	 * 
	 * @return Singleton.
	 */
	public static final AnnotationReader singleton()
	{
		return defaultInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Field> read(final Object o)
	{
		final Method[] methods = o.getClass().getMethods();

		final List<Field> fields = new LinkedList<Field>();
		
		for (final Method m : methods)
		{
			if (m.getAnnotation(JSON.class) != null)
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

					fields.add(new Field(fieldName, value));
				}
				catch (final Exception e)
				{
					throw new SerializeException(e);
				}
			}
		}

		return fields.iterator();
	}
}