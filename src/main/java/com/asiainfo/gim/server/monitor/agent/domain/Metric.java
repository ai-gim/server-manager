package com.asiainfo.gim.server.monitor.agent.domain;

import java.util.Date;

public class Metric
{
	private String ip;
	private String name;
	private Object value;
	private String unit;
	private Date time;
	private int tn;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Object getValue()
	{
		return value;
	}
	public void setValue(Object value)
	{
		this.value = value;
	}
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	public Date getTime()
	{
		return time;
	}
	public void setTime(Date time)
	{
		this.time = time;
	}
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public int getTn()
	{
		return tn;
	}
	public void setTn(int tn)
	{
		this.tn = tn;
	}
}
