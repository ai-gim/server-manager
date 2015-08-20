/**   
 * @Title: Server.java 
 * @Package com.asiainfo.gim.server.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月29日 上午10:34:49 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

/**
 * @author zhangli
 *
 */
public class Server
{
	private String id;
	private String alias;

	private String hostname;
	private String ip;
	private String mac;
	private String netmask;

	// 监视类型 1.icmp 2.ssh 3.agent
	private Integer monitorType;

	private Site site;
	private Asset asset;

	private Ipmi ipmi;
	private Ssh ssh;

	private ServerRuntime serverRuntime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public String getNetmask()
	{
		return netmask;
	}

	public void setNetmask(String netmask)
	{
		this.netmask = netmask;
	}

	public Integer getMonitorType()
	{
		return monitorType;
	}

	public void setMonitorType(Integer monitorType)
	{
		this.monitorType = monitorType;
	}

	public Site getSite()
	{
		return site;
	}

	public void setSite(Site site)
	{
		this.site = site;
	}

	public Asset getAsset()
	{
		return asset;
	}

	public void setAsset(Asset asset)
	{
		this.asset = asset;
	}

	public Ipmi getIpmi()
	{
		return ipmi;
	}

	public void setIpmi(Ipmi ipmi)
	{
		this.ipmi = ipmi;
	}

	public Ssh getSsh()
	{
		return ssh;
	}

	public void setSsh(Ssh ssh)
	{
		this.ssh = ssh;
	}

	public ServerRuntime getServerRuntime()
	{
		return serverRuntime;
	}

	public void setServerRuntime(ServerRuntime serverRuntime)
	{
		this.serverRuntime = serverRuntime;
	}

}
