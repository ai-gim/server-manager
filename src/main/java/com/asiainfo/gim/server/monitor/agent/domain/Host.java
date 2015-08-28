package com.asiainfo.gim.server.monitor.agent.domain;

import java.util.Date;
import java.util.Map;

public class Host
{

	private Map<String, Metric> metrics;
	private String name;
	private String ip;
	private Date reportTime;
	private String location;

	public Map<String, Metric> getMetrics()
	{
		return metrics;
	}

	public void setMetrics(Map<String, Metric> metrics)
	{
		this.metrics = metrics;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public Date getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(Date reportTime)
	{
		this.reportTime = reportTime;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

}
