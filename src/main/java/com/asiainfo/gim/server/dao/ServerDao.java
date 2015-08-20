/**
 * @File: ServerDao.java 
 * @Package  com.asiainfo.gim.server.dao
 * @Description: 
 * @author luyang
 * @date 2015年8月6日 上午8:55:52 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.dao;

import java.util.List;

import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.query.ServerQueryCondition;

/**
 * @author luyang
 *
 */
public interface ServerDao
{
	public List<Server> listServers(ServerQueryCondition serverQueryCondition);
	
	public Server findServerById(String id);
	
	public Server findServerByIp(String ip);
	
	public void insertServer(Server server);
	
	public void updateServer(Server server);
	
	public void insertSsh(Server server);
	
	public void insertIpmi(Server server);
	
	public void insertAsset(Server server);
	
	public void deleteServer(String id);
	
	public void deleteServerSsh(String id);
	
	public void deleteServerIpmi(String id);
	
	public void deleteServerAsset(String id);
}
