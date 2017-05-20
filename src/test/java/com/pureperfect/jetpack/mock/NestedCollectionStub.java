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

import java.util.List;

import com.pureperfect.jetpack.json.JSON;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class NestedCollectionStub
{
	private List<String> list;

	private String name;

	@JSON
	public List<String> getList()
	{
		return this.list;
	}

	@JSON
	public String getName()
	{
		return this.name;
	}

	public void setList(final List<String> list)
	{
		this.list = list;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
}
