/**
 * @File: IpmiValidator.java 
 * @Package  com.asiainfo.gim.server.api.validator
 * @Description: 
 * @author luyang
 * @date 2015年8月10日 上午9:54:56 
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

import com.asiainfo.gim.server.domain.Ipmi;

/**
 * @author luyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IpmiValidator.Valicator.class)
public @interface IpmiValidator
{
	String message() default "Ipmi bean validate fail!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class Valicator implements ConstraintValidator<IpmiValidator, Ipmi>
	{
		@Context
		private ContainerRequestContext context;
		
		@Override
		public void initialize(IpmiValidator iv)
		{
			
		}

		@Override
		public boolean isValid(Ipmi ipmi, ConstraintValidatorContext cvc)
		{
			if (StringUtils.equals(context.getMethod(), "PUT"))
			{
				if (StringUtils.isEmpty(ipmi.getHost()) || StringUtils.isEmpty(ipmi.getUsername())
						|| StringUtils.isEmpty(ipmi.getPassword()))
				{
					return false;
				}
			}
			return true;
		}
		
	}
}
