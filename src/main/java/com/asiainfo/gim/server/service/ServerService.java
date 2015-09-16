/**
 * @File: ServerService.java 
 * @Package  com.asiainfo.gim.server.service
 * @Description: 
 * @author luyang
 * @date 2015年8月6日 上午9:32:55 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.Constant.MonitorType;
import com.asiainfo.gim.server.Constant.ServerPowerStatus;
import com.asiainfo.gim.server.dao.ServerDao;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerRuntime;
import com.asiainfo.gim.server.domain.query.ServerQueryCondition;
import com.asiainfo.gim.server.monitor.icmp.ICMPDetector;
import com.asiainfo.gim.server.util.ipmi.IPMITemplate;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControl;
import com.veraxsystems.vxipmi.coding.commands.chassis.PowerCommand;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;

/**
 * @author luyang
 *
 */
@Service
public class ServerService
{
	private ServerDao serverDao;
	private IPMITemplate ipmiTemplate;
	private CacheManager cacheManager;

	@Resource
	public void setIpmiTemplate(IPMITemplate ipmiTemplate)
	{
		this.ipmiTemplate = ipmiTemplate;
	}

	@Resource
	public void setServerDao(ServerDao serverDao)
	{
		this.serverDao = serverDao;
	}
	
	@Resource
	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	public List<Server> listServers()
	{
		return listServers(new ServerQueryCondition());
	}

	public List<Server> listServers(ServerQueryCondition serverQueryCondition)
	{
		List<Server> serverInCaches = new ArrayList<Server>();
		Collection<Object> servers = ((Map)cacheManager.getCache(Constant.CacheName.SERVER_CACHE).getNativeCache()).values();
		for (Object obj : servers)
		{
			Server serverInCache = (Server) obj;
			serverInCaches.add(serverInCache);
		}
		return serverInCaches;
	}

	public Server findServerById(String id)
	{
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		return (Server) cache.get(id).get();
	}

	public Server findServerByIp(String ip)
	{
		return serverDao.findServerByIp(ip);
	}

	public Server addServer(Server server)
	{
		if (StringUtils.isEmpty(server.getId()))
		{
			String id = UUID.randomUUID().toString();
			server.setId(id);
		}
		if (server.getMonitorType() == null)
		{
			server.setMonitorType(1);
		}

		serverDao.insertServer(server);
		
		//新增完成后先查询一次服务器的状态
		if (server.getMonitorType() == MonitorType.ICMP)
		{
			boolean isReachable = ICMPDetector.isReachable(server.getIp(), 3000);
			ServerRuntime serverRuntime = server.getServerRuntime();
			if (serverRuntime == null)
			{
				serverRuntime = new ServerRuntime();
				server.setServerRuntime(serverRuntime);
			}
			serverRuntime.setStatus(isReachable ? 1 : 0);
		}
		
		//新增完成后服务器电源状态为noipmi
		server.setPowerStatus(ServerPowerStatus.NOIPMI);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		cache.put(server.getId(), server);
		
		return findServerById(server.getId());
	}

	public Server updateServer(Server server)
	{
		if (server.getMonitorType() == null)
		{
			server.setMonitorType(1);
		}

		serverDao.updateServer(server);

		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		Server serverInCache = (Server) cache.get(server.getId()).get();
		serverInCache.setIp(server.getIp());
		serverInCache.setAlias(server.getAlias());
		serverInCache.setMac(server.getMac());
		serverInCache.setNetmask(server.getNetmask());
		serverInCache.setMonitorType(server.getMonitorType());
		serverInCache.setProperties(server.getProperties());
		serverInCache.setSite(server.getSite());
		
		return findServerById(server.getId());
	}

	public Server editSsh(Server server)
	{
		serverDao.deleteServerSsh(server.getId());
		serverDao.insertSsh(server);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		Server serverInCache = (Server) cache.get(server.getId()).get();
		serverInCache.setSsh(server.getSsh());
		
		return findServerById(server.getId());
	}

	public Server editIpmi(Server server)
	{
		serverDao.deleteServerIpmi(server.getId());
		serverDao.insertIpmi(server);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		Server serverInCache = (Server) cache.get(server.getId()).get();
		serverInCache.setIpmi(server.getIpmi());
		
		/**
		 * 修改ipmi信息后,修改服务器电源状态信息
		 */
		if (server.getIpmi() != null)
		{
			serverInCache.setPowerStatus(ServerPowerStatus.UNKNOWN);
		}
		else
		{
			serverInCache.setPowerStatus(ServerPowerStatus.NOIPMI);
		}
		
		return findServerById(server.getId());
	}

	public Server editAsset(Server server)
	{
		serverDao.deleteServerAsset(server.getId());
		serverDao.insertAsset(server);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		Server serverInCache = (Server) cache.get(server.getId()).get();
		serverInCache.setAsset(server.getAsset());
		
		return findServerById(server.getId());
	}

	public void deleteServer(String id)
	{
		serverDao.deleteServerSsh(id);
		serverDao.deleteServerIpmi(id);
		serverDao.deleteServerAsset(id);
		serverDao.deleteServer(id);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		cache.evict(id);
	}

	public void serverStart(Server server)
	{
		ipmiTemplate.sendMessage(server.getIpmi().getHost(), server.getIpmi().getUsername(), server.getIpmi()
				.getPassword(), new ChassisControl(IpmiVersion.V20, null, AuthenticationType.RMCPPlus,
				PowerCommand.PowerUp));
	}

	public void serverStop(Server server)
	{
		ipmiTemplate.sendMessage(server.getIpmi().getHost(), server.getIpmi().getUsername(), server.getIpmi()
				.getPassword(), new ChassisControl(IpmiVersion.V20, null, AuthenticationType.RMCPPlus,
				PowerCommand.PowerDown));
	}

	public void serverReboot(Server server)
	{
		ipmiTemplate.sendMessage(server.getIpmi().getHost(), server.getIpmi().getUsername(), server.getIpmi()
				.getPassword(), new ChassisControl(IpmiVersion.V20, null, AuthenticationType.RMCPPlus,
				PowerCommand.HardReset));
	}
	
}
