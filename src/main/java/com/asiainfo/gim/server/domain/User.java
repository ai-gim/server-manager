/**
 * @File: User.java 
 * @Package  com.asiainfo.gim.auth.domain
 * @Description: 
 * @author luyang
 * @date 2015年7月29日 上午10:59:26 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.domain;

import java.util.Date;
import java.util.Map;

/**
 * @author luyang
 *
 */
public class User
{
	private Integer id;
	private String account;
	private String password;
	private Integer state;
	private String name;
	private String description;
	private String email;
	private Date time;
	private Map<String, String> properties;

	private Role role;
	private Group group;
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}

	public Map<String, String> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<String, String> properties)
	{
		this.properties = properties;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

}
