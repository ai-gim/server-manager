/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiCommandCoder;
import com.veraxsystems.vxipmi.coding.commands.ResponseData;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

/**
 * @author zhangli
 *
 */
public class IPMITemplate
{
	private ConnectionPool connectionPool;

	public void setConnectionPool(ConnectionPool connectionPool)
	{
		this.connectionPool = connectionPool;
	}

	public ResponseData sendMessage(String ip, String username, String password, IpmiCommandCoder ipmiCommandCoder)
	{
		Connection connection = connectionPool.getConnection(ip, username, password);

		IpmiConnector connector = connection.getConnector();
		ConnectionHandle handle = connection.getHandle();
		CipherSuite cs = connection.getCipherSuite();

		ipmiCommandCoder.setCipherSuite(cs);

		try
		{
			return connector.sendMessage(handle, ipmiCommandCoder);
		}
		catch (Exception e)
		{
			throw new IPMIException(e.getMessage(), e);
		}
		finally
		{
			ConnectionUtils.releaseConnection(connection, connectionPool);
		}
	}

}
