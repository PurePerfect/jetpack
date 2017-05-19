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
import java.io.Writer;

import com.pureperfect.jetpack.CharacterEscaper;

/**
 * Format characters before writing to the output.
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class JSONCharacterEscaper implements CharacterEscaper
{
	private static final JSONCharacterEscaper defaultInstance = new JSONCharacterEscaper();

	private JSONCharacterEscaper()
	{
		// hide me
	}

	/**
	 * Singleton.
	 * 
	 * @return Singleton
	 */
	public static final JSONCharacterEscaper singleton()
	{
		return defaultInstance;
	}

	// TESTME
	@Override
	public void write(CharSequence string, Writer out) throws IOException
	{
		for (int j = 0, length = string.length(); j < length; ++j)
		{
			final char c = string.charAt(j);

			/*
			 * Escape certain characters.
			 */
			if (c == '"' || c == '\\' || c == '\r' || c == '/' || c == '\b'
					|| c == '\f' || c == '\n' || c == '\t')
			{
				out.append("\\");
			}

			out.append(c);
		}
	}
}