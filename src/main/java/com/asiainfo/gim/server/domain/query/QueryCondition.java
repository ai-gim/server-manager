/**   
 * @Title: QueryCondition.java 
 * @Package com.asiainfo.gim.auth.domain.query 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月30日 上午10:24:42 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain.query;

import java.util.Map;

/**
 * @author zhangli
 *
 */
public abstract class QueryCondition
{
	private int start;
	private int limit;

	private Map<String, String> orders;

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public Map<String, String> getOrders()
	{
		return orders;
	}

	public void setOrders(Map<String, String> orders)
	{
		this.orders = orders;
	}
}
