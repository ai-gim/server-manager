/**
 * @File: Role.java 
 * @Package  com.asiainfo.gim.auth.domain
 * @Description: 
 * @author luyang
 * @date 2015年7月29日 上午9:21:02 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.domain;

import java.util.Map;

/**
 * @author luyang
 *
 */
public class Role
{
	private Integer id;
	private String name;
	private String description;
	private Map<String, String> properties;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Map<String, String> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<String, String> properties)
	{
		this.properties = properties;
	}

}
