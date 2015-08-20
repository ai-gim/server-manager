/**
 * 
 */
package com.asiainfo.gim.server.util.ipmi;

/**
 * @author zhangli
 *
 */
public class IPMIException extends RuntimeException
{
	private static final long serialVersionUID = 5530698987002407077L;

	public IPMIException()
	{
		super();
	}

	public IPMIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IPMIException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IPMIException(String message)
	{
		super(message);
	}

	public IPMIException(Throwable cause)
	{
		super(cause);
	}

}
