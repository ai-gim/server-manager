/**   
 * @Title: ServerReportListener.java 
 * @Package com.asiainfo.gim.server.monitor.agent 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月10日 下午4:02:51 
 * @version V1.0   
 */
package com.asiainfo.gim.server.monitor.agent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.asiainfo.gim.common.amqp.rabbitmq.QueueListener;
import com.asiainfo.gim.common.amqp.rabbitmq.RabbitMQTemplate;
import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerRuntime;
import com.asiainfo.gim.server.monitor.agent.domain.Host;
import com.asiainfo.gim.server.service.ServerService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;

/**
 * @author zhangli
 *
 */
public class ServerReportListener extends QueueListener implements InitializingBean
{
	private Log log = LogFactory.getLog(ServerReportListener.class);

	private CacheManager cacheManager;
	private ServerService serverService;
	private RabbitMQTemplate rabbitMQTemplate;
	private ObjectMapper om = new ObjectMapper();

	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	public void setServerService(ServerService serverService)
	{
		this.serverService = serverService;
	}

	public void setRabbitMQTemplate(RabbitMQTemplate rabbitMQTemplate)
	{
		this.rabbitMQTemplate = rabbitMQTemplate;
	}

	@Override
	public void execute(String consumerTag, Envelope envelope, BasicProperties basicProperties, byte[] bytes)
	{
		log.debug("receive message: " + new String(bytes));
		try
		{
			Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
			Host host = om.readValue(bytes, Host.class);
			Server server = serverService.findServerByIp(host.getIp());
			if (server == null)
			{
				server = new Server();
				server.setIp(host.getIp());
				server.setMonitorType(Constant.MonitorType.AGENT);
				serverService.addServer(server);
			}
			else
			{
				Server serverInCache = (Server) cache.get(server.getId()).get();
				if (serverInCache == null)
				{
					cache.put(server.getId(), server);
				}
				else
				{
					server = serverInCache;
				}
			}
			
			//主机名
			server.setHostname(host.getName());
			
			//runtime对象
			ServerRuntime serverRuntime = server.getServerRuntime();
			if (serverRuntime == null)
			{
				serverRuntime = new ServerRuntime();
				server.setServerRuntime(serverRuntime);
			}
			
			serverRuntime.setReportTime(host.getReportTime());
			serverRuntime.setMetrics(host.getMetrics());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		rabbitMQTemplate.exchangeDeclare(exchange, "fanout");
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.start();
	}
}
