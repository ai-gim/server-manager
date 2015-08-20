/**
 * @File: ServerAction.java 
 * @Package  com.asiainfo.gim.server.domain
 * @Description: 
 * @author luyang
 * @date 2015年8月10日 下午2:05:12 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.domain;

import java.util.Map;

/**
 * @author luyang
 *
 */
public class ServerAction
{
	private String action;
	private Map<String, String> params;

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public Map<String, String> getParams()
	{
		return params;
	}

	public void setParams(Map<String, String> params)
	{
		this.params = params;
	}

}
