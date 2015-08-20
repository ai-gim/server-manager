/**
 * @File: ServerQueryCondition.java 
 * @Package  com.asiainfo.gim.server.domain.query
 * @Description: 
 * @author luyang
 * @date 2015年8月7日 上午9:13:11 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.domain.query;

/**
 * @author luyang
 *
 */
public class ServerQueryCondition extends QueryCondition
{
	private String ip;

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}
}
