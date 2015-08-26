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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.asiainfo.gim.common.spring.SpringContext;
import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.dao.ServerDao;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerRuntime;
import com.asiainfo.gim.server.domain.query.ServerQueryCondition;

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
		ServerDao serverDao = SpringContext.getBean(ServerDao.class);
		
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
		ThreadPoolTaskExecutor thread = (ThreadPoolTaskExecutor) SpringContext.getBean("taskExecutThreadPool");
		
		for (Server server : serverDao.listServers(new ServerQueryCondition()))
		{
			Server serverInCache = (Server) cache.get(server.getId()).get();
			thread.execute(new CollectThread(serverInCache));
			cache.put(server.getId(), serverInCache);
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
			try {
	            InetAddress address = InetAddress.getByName(server.getIp());
	            ServerRuntime serverRuntime = new ServerRuntime();
	            if (server.getServerRuntime() != null)
				{
	            	serverRuntime = server.getServerRuntime();
				}
	            //1表示状态OK，0表示状态异常
	            if (address.isReachable(5000))
				{
	            	serverRuntime.setStatus(1);
				}
	            else
	            {
	            	serverRuntime.setStatus(0);
	            }
	            server.setServerRuntime(serverRuntime);
	        } catch (UnknownHostException e) {
	            log.error(e.getMessage(), e);
	        } catch (IOException e) {
	            log.error(e.getMessage(), e);
	        }
		}
		
	}
}
