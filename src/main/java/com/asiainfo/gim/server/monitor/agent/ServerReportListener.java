/**   
 * @Title: ServerReportListener.java 
 * @Package com.asiainfo.gim.server.monitor.agent 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月10日 下午4:02:51 
 * @version V1.0   
 */
package com.asiainfo.gim.server.monitor.agent;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.asiainfo.gim.server.monitor.agent.domain.Metric;
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
				server = (Server) cache.get(server.getId()).get();
				if (server == null)
				{
					return;
				}
			}

			// 更新监控类型
			if (server.getMonitorType() != Constant.MonitorType.AGENT)
			{
				serverService.updateServer(server);
			}

			// 状态
			if (System.currentTimeMillis() - host.getReportTime().getTime() > 3 * 60 * 1000)
			{
				server.getServerRuntime().setStatus(Constant.ServerStatus.UNREACHABLE);
			}
			else
			{
				server.getServerRuntime().setStatus(Constant.ServerStatus.NORMAL);
			}
			server.getServerRuntime().setRefreshTime(host.getReportTime());

			processMetrics(server, host.getMetrics());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void processMetrics(Server server, Map<String, Metric> metrics)
	{
		ServerRuntime serverRuntime = server.getServerRuntime();

		// cpu使用率
		int cpuIdle = ((Double) metrics.get("cpu_idle").getValue()).intValue();
		serverRuntime.setCpuRate(100 - cpuIdle);

		// 内存使用率
		double memTotal = (double) metrics.get("mem_total").getValue();
		double memFree = (double) metrics.get("mem_free").getValue();
		double memUsed = memTotal = memFree;
		serverRuntime.setMemoryRate((int) (memUsed / memTotal));

		// 磁盘使用率
		double diskTotal = (double) metrics.get("disk_total").getValue();
		double diskFree = (double) metrics.get("disk_free").getValue();
		double diskUsed = diskTotal - diskFree;
		serverRuntime.setDiskRate((int) (diskUsed / diskTotal));
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
