/**
 * @File: ServerValidator.java 
 * @Package  com.asiainfo.gim.server.api.validator
 * @Description: 
 * @author luyang
 * @date 2015年8月6日 上午11:39:22 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server.api.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.gim.server.domain.Server;

/**
 * @author luyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ServerValidator.Valicator.class)
public @interface ServerValidator
{
	String message() default "Server bean validate fail!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class Valicator implements ConstraintValidator<ServerValidator, Server>
	{
		@Context
		private ContainerRequestContext context;
		
		@Override
		public void initialize(ServerValidator sv)
		{
			
		}

		@Override
		public boolean isValid(Server server, ConstraintValidatorContext cvc)
		{
			if (StringUtils.equals(context.getMethod(), "POST") || StringUtils.equals(context.getMethod(), "PUT") )
			{
				if (StringUtils.isEmpty(server.getIp()))
				{
					return false;
				}
			}
			return true;
		}
		
	}
}
