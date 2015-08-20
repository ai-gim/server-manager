/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

/**
 * @author zhangli
 *
 */
public class Connection
{
	private IpmiConnector connector;
	private ConnectionHandle handle;
	private CipherSuite cipherSuite;

	public Connection(IpmiConnector connector, ConnectionHandle handle, CipherSuite cipherSuite)
	{
		this.connector = connector;
		this.handle = handle;
		this.cipherSuite = cipherSuite;
	}

	public IpmiConnector getConnector()
	{
		return connector;
	}

	public ConnectionHandle getHandle()
	{
		return handle;
	}

	public CipherSuite getCipherSuite()
	{
		return cipherSuite;
	}

	public void close()
	{
		try
		{
			connector.closeSession(handle);
		}
		catch (Exception e)
		{
			throw new IPMIException(e.getMessage(), e);
		}
		connector.closeConnection(handle);
	}
}
