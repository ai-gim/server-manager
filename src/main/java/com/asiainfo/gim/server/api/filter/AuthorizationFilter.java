/**   
 * @Title: AuthorizationFilter.java 
 * @Package com.asiainfo.gim.auth.api.filter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月8日 下午4:34:22 
 * @version V1.0   
 */
package com.asiainfo.gim.server.api.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.gim.common.rest.RestContext;
import com.asiainfo.gim.common.rest.RestSession;
import com.asiainfo.gim.common.rest.exception.RestException;
import com.asiainfo.gim.common.spring.SpringContext;
import com.asiainfo.gim.server.domain.Token;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * @author zhangli
 *
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter
{
	@Override
	public void filter(ContainerRequestContext context) throws IOException
	{
		if (!StringUtils.equals(context.getUriInfo().getPath(), "token"))
		{
			if (context.getHeaders().containsKey("X-AUTH-TOKEN"))
			{
				String tokenId = context.getHeaders().get("X-AUTH-TOKEN").get(0);
				if (!authorise(tokenId))
				{
					context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
			}
			else
			{
				context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		}
	}

	private boolean authorise(String tokenId)
	{
		Client client = ClientBuilder.newClient();
		client.register(JacksonJsonProvider.class);

		WebTarget target = client.target(SpringContext.getProperty("service.auth.endpoint") + "/token");
		Response response = target.request().header("X-AUTH-TOKEN", tokenId).get();
		if (response.getStatus() == 200)
		{
			Token token = response.readEntity(Token.class);
			RestSession session = RestContext.getSession();
			session.setAttribute("token_id", token.getId());
			session.setAttribute("user", token.getUser());
			return true;
		}
		else if (response.getStatus() == 401)
		{
			return false;
		}
		else
		{
			throw new RestException("Authorization is failed: " + String.valueOf(response.getStatusInfo()));
		}
	}
}
