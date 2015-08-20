/**   
 * @Title: Ipmi.java 
 * @Package com.asiainfo.gim.server.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月5日 上午11:35:02 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

/**
 * @author zhangli
 *
 */
public class Ipmi
{
	private String host;
	private String username;
	private String password;
	
	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
