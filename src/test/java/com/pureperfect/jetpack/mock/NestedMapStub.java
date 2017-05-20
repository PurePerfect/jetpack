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

import java.util.Map;

import com.pureperfect.jetpack.json.JSON;

/**
 * 
 * @author J. Chris Folsom
 * @version 2.0
 * @since 2.0
 */
public class NestedMapStub
{
	private Map<String, Integer> map;

	private String name;

	@JSON
	public Map<String, Integer> getMap()
	{
		return this.map;
	}

	@JSON
	public String getName()
	{
		return this.name;
	}

	public void setMap(final Map<String, Integer> map)
	{
		this.map = map;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
}
