/**
 * @File: ServerPowerStatusCollectJob.java 
 * @Package  com.asiainfo.gim.server.monitor.ipmi
 * @Description: 
 * @author luyang
 * @date 2015年9月14日 下午3:48:47 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.monitor.ipmi;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.asiainfo.gim.common.spring.SpringContext;
import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.Constant.ServerPowerStatus;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.util.ipmi.IPMITemplate;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;

/**
 * @author luyang
 *
 */
public class ServerPowerStatusCollectJob implements Job
{
	private static Log log = LogFactory.getLog(ServerPowerStatusCollectJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		CacheManager cacheManager = (CacheManager) SpringContext.getBean("cacheManager");
		ThreadPoolTaskExecutor thread = (ThreadPoolTaskExecutor) SpringContext.getBean("taskExecutThreadPool");

		Collection<Object> servers = ((Map) cacheManager.getCache(Constant.CacheName.SERVER_CACHE).getNativeCache())
				.values();
		
		for (Object obj : servers)
		{
			Server server = (Server) obj;
			if (server.getIpmi() == null)
			{
				server.setPowerStatus(ServerPowerStatus.NOIPMI);
			}
			else
			{
				thread.execute(new CollectThread(server));
			}
		}
	}
	
	private class CollectThread implements Runnable
	{
		private Server server;

		public CollectThread(Server server2)
		{
			this.server = server2;
		}
		
		public void run()
		{
			IPMITemplate ipmiTemplate = (IPMITemplate) SpringContext.getBean("ipmiTemplate");
			
			GetChassisStatusResponseData rd;
			try
			{
				rd = (GetChassisStatusResponseData) ipmiTemplate.sendMessage(server.getIpmi().getHost(),
						server.getIpmi().getUsername(), server.getIpmi().getPassword(), new GetChassisStatus(IpmiVersion.V20, null,
								AuthenticationType.RMCPPlus));
				if (rd != null)
				{
					server.setPowerStatus(rd.isPowerOn() ? ServerPowerStatus.POWERON : ServerPowerStatus.POWEROFF);
				}
			}
			catch (Exception e)
			{
				server.setPowerStatus(ServerPowerStatus.UNKNOWN);
				log.error(e.getMessage(), e);
			}
		}
	}
}
