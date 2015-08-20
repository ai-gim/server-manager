/**
 * @File: ServerResource.java 
 * @Package  com.asiainfo.gim.server.api.resources
 * @Description: 
 * @author luyang
 * @date 2015年8月6日 上午9:40:38 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.gim.common.rest.exception.ResourceNotFoundException;
import com.asiainfo.gim.common.rest.exception.ValidationException;
import com.asiainfo.gim.common.spring.SpringContext;
import com.asiainfo.gim.server.api.validator.IpmiValidator;
import com.asiainfo.gim.server.api.validator.ServerActionValidator;
import com.asiainfo.gim.server.api.validator.ServerValidator;
import com.asiainfo.gim.server.api.validator.SshValidator;
import com.asiainfo.gim.server.domain.Asset;
import com.asiainfo.gim.server.domain.Ipmi;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.ServerAction;
import com.asiainfo.gim.server.domain.Ssh;
import com.asiainfo.gim.server.service.ServerService;

/**
 * @author luyang
 *
 */
@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource
{
	private ServerService serverService;
	
	@PathParam("id")
	private String id;
	
	public ServerResource()
	{
		serverService = SpringContext.getBean(ServerService.class);
	}
	
	@GET
	@Path("{id}")
	public Server getServer()
	{
		Server server = serverService.findServerById(id);
		if (server == null)
		{
			throw new ResourceNotFoundException();
		}
		return server;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Server addServer(@ServerValidator Server server)
	{
		if (server == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerByIp(server.getIp());
		if (serverInDb != null)
		{
			throw new ValidationException("Ip conflict");
		}
		
		if (!StringUtils.isEmpty(server.getId()))
		{
			Server serverInDbById = serverService.findServerById(server.getId());
			if (serverInDbById != null)
			{
				throw new ValidationException("Id conflict");
			}
		}
		
		return serverService.addServer(server);
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Server updateServer(@ServerValidator Server server)
	{
		if (server == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerById(id);
		if (serverInDb == null)
		{
			throw new ResourceNotFoundException();
		}
		
		if (!StringUtils.equals(server.getIp(), serverInDb.getIp()))
		{
			Server serverInDbByIp = serverService.findServerByIp(server.getIp());
			if (serverInDbByIp != null)
			{
				throw new ValidationException("Ip conflict");
			}
		}
		
		server.setId(id);
		
		return serverService.updateServer(server);
	}
	
	@PUT
	@Path("{id}/ssh")
	@Produces(MediaType.APPLICATION_JSON)
	public Server updateServerSsh(@SshValidator Ssh ssh)
	{
		if (ssh == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerById(id);
		if (serverInDb == null)
		{
			throw new ResourceNotFoundException();
		}
		
		serverInDb.setSsh(ssh);
		return serverService.editSsh(serverInDb);
	}
	
	@PUT
	@Path("{id}/ipmi")
	@Produces(MediaType.APPLICATION_JSON)
	public Server updateServerIpmi(@IpmiValidator Ipmi ipmi)
	{
		if (ipmi == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerById(id);
		if (serverInDb == null)
		{
			throw new ResourceNotFoundException();
		}
		
		serverInDb.setIpmi(ipmi);
		return serverService.editIpmi(serverInDb);
	}
	
	@PUT
	@Path("{id}/asset")
	@Produces(MediaType.APPLICATION_JSON)
	public Server updateServerAsset(Asset asset)
	{
		if (asset == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerById(id);
		if (serverInDb == null)
		{
			throw new ResourceNotFoundException();
		}
		
		serverInDb.setAsset(asset);
		return serverService.editAsset(serverInDb);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteServer()
	{
		serverService.deleteServer(id);
	}
	
	@POST
	@Path("{id}/action")
	public void serverAction(@ServerActionValidator ServerAction serverAction)
	{
		if (serverAction == null)
		{
			throw new ValidationException();
		}
		
		Server serverInDb = serverService.findServerById(id);
		if (serverInDb == null)
		{
			throw new ResourceNotFoundException();
		}
		else if (serverInDb.getIpmi() == null)
		{
			throw new ValidationException();
		}
		
		if (StringUtils.equals(serverAction.getAction(), "start"))
		{
			serverService.serverStart(serverInDb);
		}
		else if (StringUtils.equals(serverAction.getAction(), "stop")) 
		{
			serverService.serverStop(serverInDb);
		}
		else if (StringUtils.equals(serverAction.getAction(), "reboot")) 
		{
			serverService.serverReboot(serverInDb);
		}
	}
}
