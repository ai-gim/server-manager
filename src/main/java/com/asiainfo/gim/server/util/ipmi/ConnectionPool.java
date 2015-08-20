/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

/**
 * @author zhangli
 *
 */
public interface ConnectionPool
{
	public Connection getConnection(String ip, String username, String password);
}
