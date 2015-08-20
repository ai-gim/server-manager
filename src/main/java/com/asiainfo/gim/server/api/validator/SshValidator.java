/**
 * @File: SshValidator.java 
 * @Package  com.asiainfo.gim.server.api.validator
 * @Description: 
 * @author luyang
 * @date 2015年8月7日 下午2:17:24 
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

import com.asiainfo.gim.server.domain.Ssh;

/**
 * @author luyang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SshValidator.Valicator.class)
public @interface SshValidator
{
	String message() default "Ssh bean validate fail!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	public class Valicator implements ConstraintValidator<SshValidator, Ssh>
	{
		@Context
		private ContainerRequestContext context;
		
		@Override
		public void initialize(SshValidator sv)
		{
			
		}

		@Override
		public boolean isValid(Ssh ssh, ConstraintValidatorContext cvc)
		{
			if (StringUtils.equals(context.getMethod(), "PUT"))
			{
				if (StringUtils.isEmpty(ssh.getHost()) || StringUtils.isEmpty(ssh.getUsername())
						|| StringUtils.isEmpty(ssh.getPassword()) || ssh.getPort() == null)
				{
					return false;
				}
			}
			return true;
		}
		
	}
}
