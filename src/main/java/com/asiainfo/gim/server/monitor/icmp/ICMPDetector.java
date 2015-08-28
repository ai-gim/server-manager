/**   
* @Title: ICMPDetector.java 
* @Package com.asiainfo.gim.server.monitor.icmp 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhangli
* @date 2015年8月28日 上午9:20:45 
* @version V1.0   
*/
package com.asiainfo.gim.server.monitor.icmp;

import java.net.InetAddress;

/**
 * @author zhangli
 *
 */
public class ICMPDetector
{
	public static boolean isReachable(String host, int timeout)
	{
		try
		{
			InetAddress address = InetAddress.getByName(host);
			return address.isReachable(3000);
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
