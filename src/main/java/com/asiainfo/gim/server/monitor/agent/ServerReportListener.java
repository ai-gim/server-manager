/**   
 * @Title: ServerReportListener.java 
 * @Package com.asiainfo.gim.server.monitor.agent 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月10日 下午4:02:51 
 * @version V1.0   
 */
package com.asiainfo.gim.server.monitor.agent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.asiainfo.gim.common.amqp.rabbitmq.QueueListener;
import com.asiainfo.gim.common.amqp.rabbitmq.RabbitMQTemplate;
import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.domain.Server;
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
			Server server = om.readValue(bytes, Server.class);
			Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);
			if (cache.get(server.getId()) == null)
			{
				server.setMonitorType(Constant.MonitorType.AGENT);
				serverService.addServer(server);
			}
			else
			{
				Server serverInCache = (Server) cache.get(server.getId()).get();
				if (needUpdate(server, serverInCache))
				{
					serverInCache.setIp(server.getIp());
					serverInCache.setMac(server.getMac());
					serverInCache.setNetmask(server.getNetmask());
					serverInCache.setMonitorType(Constant.MonitorType.AGENT);
					serverService.updateServer(serverInCache);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private boolean needUpdate(Server server, Server serverInCache)
	{
		if (!StringUtils.equals(server.getIp(), serverInCache.getIp()))
		{
			return true;
		}
		if (!StringUtils.equals(server.getMac(), serverInCache.getMac()))
		{
			return true;
		}
		if (!StringUtils.equals(server.getNetmask(), serverInCache.getNetmask()))
		{
			return true;
		}
		if (serverInCache.getMonitorType() != Constant.MonitorType.AGENT)
		{
			return true;
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		rabbitMQTemplate.exchangeDeclare(exchange, "direct");
		rabbitMQTemplate.queueDeclare(queueName);
		rabbitMQTemplate.bind(exchange, queueName, routingKey);
		
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		this.start();
	}
}
