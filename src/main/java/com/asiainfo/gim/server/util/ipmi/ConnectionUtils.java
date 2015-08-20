/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

/**
 * @author zhangli
 *
 */
public class ConnectionUtils
{
	public static void releaseConnection(Connection connection, ConnectionPool connectionPool)
	{
		if(connection != null)
		{
			connection.close();
		}
	}
}
