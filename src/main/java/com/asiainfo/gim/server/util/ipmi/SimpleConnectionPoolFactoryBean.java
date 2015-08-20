/**
 * @File: SimpleConnectionPoolFactoryBean.java 
 * @Package  com.asiainfo.gim.server.util.ipmi
 * @Description: 
 * @author luyang
 * @date 2015年8月10日 下午5:40:21 
 * @version V1.0
 * 
 */
/**
 * @File: SimpleConnectionPoolFactoryBean.java 
 * @Package  com.asiainfo.gim.server.util.ipmi
 * @Description: 
 * @author luyang
 * @date 2015年8月10日 下午5:40:21 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author luyang
 *
 */
public class SimpleConnectionPoolFactoryBean implements FactoryBean<SimpleConnectionPool>, InitializingBean
{
	private SimpleConnectionPool connectionPool;
	private int port;

	public void setPort(int port)
	{
		this.port = port;
	}

	@Override
	public SimpleConnectionPool getObject() throws Exception
	{
		return connectionPool;
	}

	@Override
	public Class<?> getObjectType()
	{
		return SimpleConnectionPool.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (port < 80)
		{
			while (true)
			{
				Random random = new Random();
				port = random.nextInt(9999) + 30000;
				if (!isPortUsing("127.0.0.1", port))
				{
					break;
				}
			}
		}
		
		connectionPool = new SimpleConnectionPool();
		connectionPool.setAllocatedPort(port);
	}

	private boolean isPortUsing(String host, int port)
	{
		boolean flag = false;

		try
		{
			InetAddress theAddress = InetAddress.getByName(host);
			Socket socket = new Socket(theAddress, port);
			flag = true;
		}
		catch (IOException e)
		{

		}
		return flag;
	}
}
