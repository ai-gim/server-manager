/**   
 * @Title: ServerCacheInitBean.java 
 * @Package com.asiainfo.gim.server.init 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年8月10日 下午3:37:03 
 * @version V1.0   
 */
package com.asiainfo.gim.server.init;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.asiainfo.gim.server.Constant;
import com.asiainfo.gim.server.dao.ServerDao;
import com.asiainfo.gim.server.domain.Server;
import com.asiainfo.gim.server.domain.query.ServerQueryCondition;

/**
 * @author zhangli
 *
 */
@Component
public class ServerCacheInitializingBean implements InitializingBean
{
	private CacheManager cacheManager;
	private ServerDao serverDao;

	@Resource
	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
	}

	@Resource
	public void setServerDao(ServerDao serverDao)
	{
		this.serverDao = serverDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Cache cache = cacheManager.getCache(Constant.CacheName.SERVER_CACHE);

		for (Server server : serverDao.listServers(new ServerQueryCondition()))
		{
			cache.put(server.getId(), server);
		}
	}

}
