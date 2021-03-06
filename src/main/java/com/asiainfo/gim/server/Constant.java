/**   
* @Title: Constant.java 
* @Package com.asiainfo.gim.server 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhangli
* @date 2015年8月10日 下午3:38:44 
* @version V1.0   
*/
package com.asiainfo.gim.server;

/**
 * @author zhangli
 *
 */
public interface Constant
{
	public static final String INTERNAL_TOKEN = "E89CAF25-0479-4DE0-AB9B-C9E8F03EC365";
	
	public static interface CacheName
	{
		public static final String SERVER_CACHE = "SERVER_CACHE";
	}
	
	public static interface MonitorType
	{
		public static final int ICMP = 1;
		public static final int SSH = 2;
		public static final int AGENT = 3;
	}
	
	public static interface ServerStatus
	{
		public static final int UNREACHABLE = 0;
		public static final int NORMAL = 1;
	}
	
	public static interface ServerPowerStatus
	{
		public static final int POWEROFF = 0;
		public static final int POWERON = 1;
		public static final int UNKNOWN = 2;
		public static final int NOIPMI = 3;
	}
}
