/**   
 * @Title: Token.java 
 * @Package com.asiainfo.gim.auth.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月8日 下午4:47:54 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

import java.util.Date;

/**
 * @author zhangli
 *
 */
public class Token
{
	private String id;
	private Date expired;
	private User user;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Date getExpired()
	{
		return expired;
	}

	public void setExpired(Date expired)
	{
		this.expired = expired;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

}
