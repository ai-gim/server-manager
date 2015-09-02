/**   
 * @Title: ServerRuntime.java 
 * @Package com.asiainfo.gim.server.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月5日 上午11:45:16 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

import java.util.Date;
import java.util.Map;

import com.asiainfo.gim.server.monitor.agent.domain.Metric;

/**
 * @author zhangli
 *
 */
public class ServerRuntime
{
	private Integer status;
	private Map<String, Metric> metrics;
	private Date reportTime;

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Map<String, Metric> getMetrics()
	{
		return metrics;
	}

	public void setMetrics(Map<String, Metric> metrics)
	{
		this.metrics = metrics;
	}

	public Date getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(Date reportTime)
	{
		this.reportTime = reportTime;
	}
}
