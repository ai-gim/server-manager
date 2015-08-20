/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

import java.net.InetAddress;
import java.util.List;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

/**
 * @author zhangli
 *
 */
public class SimpleConnectionPool implements ConnectionPool
{
	private IpmiConnector connector;

	private int allocatedPort = 12345;

	public void setAllocatedPort(int allocatedPort)
	{
		this.allocatedPort = allocatedPort;
	}

	public synchronized Connection getConnection(String ip, String username, String password)
	{
		try
		{
			IpmiConnector connector = getConnector();
			ConnectionHandle handle = connector.createConnection(InetAddress.getByName(ip));
			CipherSuite cipherSuite = getCipherSuite(connector, handle, username, password);
			connector.openSession(handle, username, password, "".getBytes());
			
			return new Connection(connector, handle, cipherSuite);
		}
		catch (Exception e)
		{
			throw new IPMIException("Can not create ipmi connection", e);
		}
	}
	
	private static CipherSuite getCipherSuite(IpmiConnector ipmiConnector, ConnectionHandle handle, String ipmiUsername, String ipmiPassword)
	{
		CipherSuite cs;
		try
		{
			// 从远程服务器得到可用的几组秘钥算法列表
			List<CipherSuite> suites = ipmiConnector.getAvailableCipherSuites(handle);
			if (suites.size() > 3)
			{
				cs = suites.get(3);
			}
			else if (suites.size() > 2)
			{
				cs = suites.get(2);
			}
			else if (suites.size() > 1)
			{
				cs = suites.get(1);
			}
			else
			{
				cs = suites.get(0);
			}
			// 根据得到的一组秘钥算法和指定权限得到信道
			ipmiConnector.getChannelAuthenticationCapabilities(handle, cs, PrivilegeLevel.Administrator);
			return cs;
		}
		catch (Exception e)
		{
			throw new IPMIException(e.getMessage(), e);
		}
	}

	private synchronized IpmiConnector getConnector()
	{
		if (connector == null)
		{
			try
			{
				connector = new IpmiConnector(allocatedPort);
			}
			catch (Exception e)
			{
				throw new IPMIException(e.getMessage(), e);
			}
		}
		return connector;
	}
}
