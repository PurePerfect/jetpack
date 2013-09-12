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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.pureperfect.jetpack.TypeConverter;

/**
 * Represents an object in JavaScript Object Notation. An object is essentially
 * a list of {@link JSONField JSONFields}.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONObject implements JSONData, List<JSONField>
{
	private final List<JSONField> fields;

	/**
	 * Create a new {@link JSONObject}.
	 */
	public JSONObject()
	{
		this.fields = new LinkedList<JSONField>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(final int index, final JSONField element)
	{
		this.fields.add(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(final JSONField e)
	{
		return this.fields.add(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final Collection<? extends JSONField> c)
	{
		return this.fields.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final int index,
			final Collection<? extends JSONField> c)
	{
		return this.fields.addAll(index, c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear()
	{
		this.fields.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final Object o)
	{
		return this.fields.contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(final Collection<?> c)
	{
		return this.fields.containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSONField get(final int index)
	{
		return this.fields.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(final Object o)
	{
		return this.fields.indexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty()
	{
		return this.fields.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<JSONField> iterator()
	{
		return this.fields.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int lastIndexOf(final Object o)
	{
		return this.fields.lastIndexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListIterator<JSONField> listIterator()
	{
		return this.fields.listIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListIterator<JSONField> listIterator(final int index)
	{
		return this.fields.listIterator(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSONField remove(final int index)
	{
		return this.fields.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(final Object o)
	{
		return this.fields.remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeAll(final Collection<?> c)
	{
		return this.fields.removeAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean retainAll(final Collection<?> c)
	{
		return this.fields.retainAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSONField set(final int index, final JSONField element)
	{
		return this.fields.set(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size()
	{
		return this.fields.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<JSONField> subList(final int fromIndex, final int toIndex)
	{
		return this.fields.subList(fromIndex, toIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray()
	{
		return this.fields.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T[] toArray(final T[] a)
	{
		return this.fields.toArray(a);
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
	public CharSequence toText(final TypeConverter converter)
	{
		final int size = this.size();
		final int length = 24 * size;

		final StringBuilder builder = new StringBuilder(length);

		builder.append('{');

		int i = 1;

		for (final JSONField field : this.fields)
		{
			builder.append(field.toText());

			if (i < size)
			{
				builder.append(',');
			}
			++i;
		}

		builder.append('}');

		return builder;
	}
}