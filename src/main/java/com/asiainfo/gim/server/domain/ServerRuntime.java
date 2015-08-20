/**   
 * @Title: ServerRuntime.java 
 * @Package com.asiainfo.gim.server.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月5日 上午11:45:16 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

/**
 * @author zhangli
 *
 */
public class ServerRuntime
{
	private Integer status;

	private float cpuRate;
	private float memoryRate;
	private float diskRate;

	private long runTime;

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

	public void setCpuRate(float cpuRate)
	{
		this.cpuRate = cpuRate;
	}

	public float getMemoryRate()
	{
		return memoryRate;
	}

	public void setMemoryRate(float memoryRate)
	{
		this.memoryRate = memoryRate;
	}

	public float getDiskRate()
	{
		return diskRate;
	}

	public void setDiskRate(float diskRate)
	{
		this.diskRate = diskRate;
	}

	public long getRunTime()
	{
		return runTime;
	}

	public void setRunTime(long runTime)
	{
		this.runTime = runTime;
	}
}
