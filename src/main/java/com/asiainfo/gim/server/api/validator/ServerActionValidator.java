/**
 * @File: ServerActionValidator.java 
 * @Package  com.asiainfo.gim.server.api.validator
 * @Description: 
 * @author luyang
 * @date 2015年8月10日 下午3:08:17 
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

import com.asiainfo.gim.server.domain.ServerAction;

/**
 * @author luyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ServerActionValidator.Valicator.class)
public @interface ServerActionValidator
{
	String message() default "ServerAction bean validate fail!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class Valicator implements ConstraintValidator<ServerActionValidator, ServerAction>
	{
		@Context
		private ContainerRequestContext context;
		
		@Override
		public void initialize(ServerActionValidator sav)
		{
			
		}

		@Override
		public boolean isValid(ServerAction serverAction, ConstraintValidatorContext cvc)
		{
			if (StringUtils.equals(context.getMethod(), "POST"))
			{
				if (StringUtils.isEmpty(serverAction.getAction()))
				{
					return false;
				}
			}
			return true;
		}
		
	}
}
