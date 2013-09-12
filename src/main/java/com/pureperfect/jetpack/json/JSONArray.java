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
 * Represents an array in JavaScript Object Notation.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 * 
 * @param <T>
 *            The type of values this array should hold.
 */
public class JSONArray<T> implements JSONData, List<T>
{
	private final List<T> values;

	/**
	 * Create a new JSONArray.
	 */
	public JSONArray()
	{
		this.values = new LinkedList<T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(final int index, final T element)
	{
		this.values.add(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(final T obj)
	{
		return this.values.add(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final Collection<? extends T> c)
	{
		return this.values.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(final int index, final Collection<? extends T> c)
	{
		return this.values.addAll(index, c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear()
	{
		this.values.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final Object o)
	{
		return this.values.contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(final Collection<?> c)
	{
		return this.values.containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(final int index)
	{
		return this.values.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(final Object o)
	{
		return this.values.indexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty()
	{
		return this.values.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator()
	{
		return this.values.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int lastIndexOf(final Object o)
	{
		return this.values.lastIndexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListIterator<T> listIterator()
	{
		return this.values.listIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListIterator<T> listIterator(final int index)
	{
		return this.values.listIterator(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T remove(final int index)
	{
		return this.values.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(final Object o)
	{
		return this.values.remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeAll(final Collection<?> c)
	{
		return this.values.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c)
	{
		return this.values.retainAll(c);
	}

	@Override
	public T set(final int index, final T element)
	{
		return this.values.set(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size()
	{
		return this.values.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> subList(final int fromIndex, final int toIndex)
	{
		return this.values.subList(fromIndex, toIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray()
	{
		return this.values.toArray();
	}

	@Override
	@SuppressWarnings("hiding")
	public <T> T[] toArray(final T[] a)
	{
		return this.values.toArray(a);
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

		final StringBuilder builder = new StringBuilder(512);

		builder.append('[');

		int i = 1;

		for (final T value : this.values)
		{
			builder.append(converter.convert(value));

			if (i < size)
			{
				builder.append(',');
			}

			++i;
		}

		builder.append(']');

		return builder;
	}
}
