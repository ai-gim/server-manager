/**   
 * @Title: Group.java 
 * @Package com.asiainfo.gim.auth.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月8日 上午10:03:50 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

import java.util.Map;

/**
 * @author zhangli
 *
 */
public class Group
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
