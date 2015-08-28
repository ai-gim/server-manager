/**
 * @File: ServerStatusCollectJob.java 
 * @Package  com.asiainfo.gim.server.monitor.icmp
 * @Description: 
 * @author luyang
 * @date 2015年8月26日 上午9:51:41 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.monitor.icmp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerRuntime;

/**
 * @author luyang
 *
 */
public class ServerStatusCollectJob implements Job
{
	private static Log log = LogFactory.getLog(ServerStatusCollectJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		CacheManager cacheManager = (CacheManager) SpringContext.getBean("cacheManager");
		ThreadPoolTaskExecutor thread = (ThreadPoolTaskExecutor) SpringContext.getBean("taskExecutThreadPool");

		Collection<Object> servers = ((Map)cacheManager.getCache(Constant.CacheName.SERVER_CACHE).getNativeCache()).values();
		for (Object obj : servers)
		{
			Server serverInCache = (Server) obj;
			thread.execute(new CollectThread(serverInCache));
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
			getServerStatus(server);
		}
	}
	
	public void getServerStatus(Server server)
	{
		ServerRuntime serverRuntime = server.getServerRuntime();
		if (server.getServerRuntime() == null)
		{
			serverRuntime = new ServerRuntime();
			server.setServerRuntime(serverRuntime);
		}

		try
		{
			InetAddress address = InetAddress.getByName(server.getIp());

			// 1表示状态OK，0表示状态异常
			if (address.isReachable(3000))
			{
				serverRuntime.setStatus(1);
			}
			else
			{
				serverRuntime.setStatus(0);
			}
		}
		catch (UnknownHostException e)
		{
			serverRuntime.setStatus(0);
			log.error(e.getMessage(), e);
		}
		catch (IOException e)
		{
			serverRuntime.setStatus(0);
			log.error(e.getMessage(), e);
		}
	}
}
