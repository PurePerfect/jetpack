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
package com.pureperfect.jetpack.mock;

import com.pureperfect.jetpack.json.JSON;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class MockMappedType
{
	private Integer id;

	private Integer iq;

	private String name;

	@JSON
	public Integer getId()
	{
		return this.id;
	}

	@JSON
	public Integer getIq()
	{
		return this.iq;
	}

	@JSON
	public String getName()
	{
		return this.name;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public void setIq(final Integer iq)
	{
		this.iq = iq;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
}
