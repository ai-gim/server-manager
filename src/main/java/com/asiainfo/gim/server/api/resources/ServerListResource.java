/**
 * @File: ServerListResource.java 
 * @Package  com.asiainfo.gim.server.api.resources
 * @Description: 
 * @author luyang
 * @date 2015年8月7日 上午9:21:24 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.asiainfo.gim.common.spring.SpringContext;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.query.ServerQueryCondition;
import com.asiainfo.gim.server.service.ServerService;

/**
 * @author luyang
 *
 */
@Path("/servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerListResource
{
	private ServerService serverService;
	
	@QueryParam("ip")
	private String ip;
	
	public ServerListResource()
	{
		serverService = SpringContext.getBean(ServerService.class);
	}
	
	@GET
	public List<Server> getServer()
	{
		ServerQueryCondition serverQueryCondition = new ServerQueryCondition();
		serverQueryCondition.setIp(ip);
		
		List<Server> servers = serverService.listServers(serverQueryCondition);
		return servers;
	}
}
