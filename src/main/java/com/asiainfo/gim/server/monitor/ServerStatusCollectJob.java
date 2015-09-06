/**
 * @File: ServerStatusCollectJob.java 
 * @Package  com.asiainfo.gim.server.monitor.icmp
 * @Description: 
 * @author luyang
 * @date 2015年8月26日 上午9:51:41 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.monitor;

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
import com.asiainfo.gim.server.Constant.MonitorType;
import com.asiainfo.gim.server.Constant.ServerStatus;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerRuntime;
import com.asiainfo.gim.server.monitor.icmp.ICMPDetector;

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

		Collection<Object> servers = ((Map) cacheManager.getCache(Constant.CacheName.SERVER_CACHE).getNativeCache())
				.values();
		for (Object obj : servers)
		{
			Server server = (Server) obj;
			if (server.getMonitorType() == MonitorType.ICMP)
			{
				thread.execute(new CollectThread(server));
			}
			else if (server.getMonitorType() == MonitorType.AGENT)
			{
				ServerRuntime serverRuntime = server.getServerRuntime();
				if (serverRuntime == null)
				{
					serverRuntime = new ServerRuntime();
					server.setServerRuntime(serverRuntime);
				}

				if (serverRuntime.getReportTime() == null)
				{
					serverRuntime.setStatus(ServerStatus.UNREACHABLE);
				}
				else if ((System.currentTimeMillis() - serverRuntime.getReportTime().getTime()) > 60 * 1000)
				{
					serverRuntime.setStatus(ServerStatus.UNREACHABLE);
				}
				else
				{
					serverRuntime.setStatus(ServerStatus.NORMAL);
				}
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
			boolean isReachable = ICMPDetector.isReachable(server.getIp(), 3000);
			server.getServerRuntime().setStatus(isReachable ? 1 : 0);
		}
	}
}
