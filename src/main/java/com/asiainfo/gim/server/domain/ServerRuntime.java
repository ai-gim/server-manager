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

/**
 * @author zhangli
 *
 */
public class ServerRuntime
{
	private Integer status;

	private int cpuRate;
	private int memoryRate;
	private int diskRate;
	private long runTime;
	private Date refreshTime;

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public float getCpuRate()
	{
		return cpuRate;
	}

	public int getMemoryRate()
	{
		return memoryRate;
	}

	public void setMemoryRate(int memoryRate)
	{
		this.memoryRate = memoryRate;
	}

	public int getDiskRate()
	{
		return diskRate;
	}

	public void setDiskRate(int diskRate)
	{
		this.diskRate = diskRate;
	}

	public void setCpuRate(int cpuRate)
	{
		this.cpuRate = cpuRate;
	}

	public long getRunTime()
	{
		return runTime;
	}

	public void setRunTime(long runTime)
	{
		this.runTime = runTime;
	}

	public Date getRefreshTime()
	{
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime)
	{
		this.refreshTime = refreshTime;
	}

}
