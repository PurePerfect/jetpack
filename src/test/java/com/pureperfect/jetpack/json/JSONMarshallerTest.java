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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import com.pureperfect.jetpack.Output;
import com.pureperfect.jetpack.json.JSONOut;
import com.pureperfect.jetpack.mock.MockMappedType;
import com.pureperfect.jetpack.mock.NestedArray;
import com.pureperfect.jetpack.mock.NestedCollection;
import com.pureperfect.jetpack.mock.NestedMap;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONMarshallerTest extends TestCase
{
	private InputStream in;

	private OutputStream out;

	void printOut() throws IOException
	{
		while (true)
		{
			System.out.print((char) this.in.read());
		}
	}

	@Override
	public void setUp() throws Exception
	{
		this.out = new PipedOutputStream();
		this.in = new PipedInputStream((PipedOutputStream) this.out);
	}

	public void testArray() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final int[] array = new int[] { 1, 2, 3 };

		jm.write(array);

		jm.flush();

		assertEquals('[', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('2', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('3', this.in.read());
		assertEquals(']', this.in.read());
	}

	public void testCollection() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final List<String> list = new LinkedList<String>();

		list.add("one");
		list.add("two");

		jm.write(list);

		jm.flush();

		assertEquals('[', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('n', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('t', this.in.read());
		assertEquals('w', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(']', this.in.read());
	}

	public void testMap() throws IOException
	{
		/*
		 * CAUTION this test assumes that the fields are in alphabetical order.
		 * It may not work on your platform.
		 */
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final Map<String, Integer> map = new TreeMap<String, Integer>();

		map.put("one", 1);
		map.put("two", 2);

		jm.write(map);

		jm.flush();

		assertEquals('{', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('n', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('t', this.in.read());
		assertEquals('w', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('2', this.in.read());
		assertEquals('}', this.in.read());
	}

	public void testMappedType() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final MockMappedType p = new MockMappedType();

		p.setId(23);

		p.setName("rb");

		jm.write(p);

		jm.flush();

		byte[] result = new byte[31];

		in.read(result);

		/*
		 * WARNING this test assumes that the fields are in a set order. It may
		 * not work on your platform.
		 */
		assertEquals("{\"iq\":null,\"name\":\"rb\",\"id\":23}",
				new String(result));
	}

	public void testNestedArray() throws IOException
	{
		/*
		 * WARNING this test assumes that the fields are in a set order. It may
		 * not work on your platform.
		 */
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final int[] array = new int[] { 1, 2, 3 };

		final NestedArray testme = new NestedArray();

		testme.setName("rb");
		testme.setArray(array);

		jm.write(testme);

		jm.flush();

		assertEquals('{', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('n', this.in.read());
		assertEquals('a', this.in.read());
		assertEquals('m', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('r', this.in.read());
		assertEquals('b', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('a', this.in.read());
		assertEquals('r', this.in.read());
		assertEquals('r', this.in.read());
		assertEquals('a', this.in.read());
		assertEquals('y', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('[', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('2', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('3', this.in.read());
		assertEquals(']', this.in.read());
		assertEquals('}', this.in.read());
	}

	public void testNestedCollection() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final List<String> list = new LinkedList<String>();

		list.add("one");
		list.add("two");

		final NestedCollection testme = new NestedCollection();

		testme.setName("rb");

		testme.setList(list);

		jm.write(testme);

		jm.flush();

		byte[] result = new byte[34];

		in.read(result);

		/*
		 * WARNING this test assumes that the fields are in a set order. It may
		 * not work on your platform.
		 */
		assertEquals("{\"list\":[\"one\",\"two\"],\"name\":\"rb\"}",
				new String(result));
	}

	public void testNestedMap() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final Map<String, Integer> map = new TreeMap<String, Integer>();

		map.put("one", 1);
		map.put("two", 2);

		final NestedMap testme = new NestedMap();

		testme.setMap(map);
		testme.setName("rb");

		jm.write(testme);

		jm.flush();

		assertEquals('{', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('n', this.in.read());
		assertEquals('a', this.in.read());
		assertEquals('m', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('r', this.in.read());
		assertEquals('b', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('m', this.in.read());
		assertEquals('a', this.in.read());
		assertEquals('p', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('{', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('n', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals(',', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('t', this.in.read());
		assertEquals('w', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('2', this.in.read());
		assertEquals('}', this.in.read());
		assertEquals('}', this.in.read());
	}

	public void testNestedMappedType() throws IOException
	{
		/*
		 * WARNING this test assumes that the fields are sorted in a particular
		 * order. It may not work on your platform.
		 */
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		final MockMappedType p = new MockMappedType();

		p.setId(23);

		p.setName("rb");

		jm.write(p);

		jm.flush();

		byte[] result = new byte[31];

		in.read(result);

		/*
		 * WARNING this test assumes that the fields are in a set order. It may
		 * not work on your platform.
		 */
		assertEquals("{\"iq\":null,\"name\":\"rb\",\"id\":23}",
				new String(result));
	}

	public void testString() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write("fire!");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('f', this.in.read());
		assertEquals('i', this.in.read());
		assertEquals('r', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('!', this.in.read());
		assertEquals('"', this.in.read());

		// Escape "
		jm.write("\"Hello\"");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('H', this.in.read());
		assertEquals('e', this.in.read());
		assertEquals('l', this.in.read());
		assertEquals('l', this.in.read());
		assertEquals('o', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('"', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \
		jm.write("C:\\");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \r
		jm.write("C:\r");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\r', this.in.read());
		assertEquals('"', this.in.read());

		// Escape /
		jm.write("C:/");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('/', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \b
		jm.write("C:\b");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\b', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \f
		jm.write("C:\f");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\f', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \n
		jm.write("C:\n");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\n', this.in.read());
		assertEquals('"', this.in.read());

		// Escape \t
		jm.write("C:\t");

		jm.flush();

		assertEquals('"', this.in.read());
		assertEquals('C', (char) this.in.read());
		assertEquals(':', this.in.read());
		assertEquals('\\', this.in.read());
		assertEquals('\t', this.in.read());
		assertEquals('"', this.in.read());
	}

	public void testWriteDouble() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write(171.6);

		jm.flush();

		assertEquals('1', this.in.read());
		assertEquals('7', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals('.', this.in.read());
		assertEquals('6', this.in.read());
	}

	public void testWriteFloat() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write((float) 171.6);

		jm.flush();

		assertEquals('1', this.in.read());
		assertEquals('7', this.in.read());
		assertEquals('1', this.in.read());
		assertEquals('.', this.in.read());
		assertEquals('6', this.in.read());
	}

	public void testWriteInt() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write(171);

		jm.flush();

		assertEquals('1', this.in.read());
		assertEquals('7', this.in.read());
		assertEquals('1', this.in.read());
	}

	public void testWriteLong() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write((long) 171);

		jm.flush();

		assertEquals('1', this.in.read());
		assertEquals('7', this.in.read());
		assertEquals('1', this.in.read());
	}

	public void testWriteShort() throws IOException
	{
		final Writer w = new OutputStreamWriter(this.out);

		final Output jm = new JSONOut(w);

		jm.write((short) 171);

		jm.flush();

		assertEquals('1', this.in.read());
		assertEquals('7', this.in.read());
		assertEquals('1', this.in.read());
	}
}
