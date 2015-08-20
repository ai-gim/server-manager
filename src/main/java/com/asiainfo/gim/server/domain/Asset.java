/**   
 * @Title: Asset.java 
 * @Package com.asiainfo.gim.server.domain 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhangli
 * @date 2015年7月29日 下午12:28:18 
 * @version V1.0   
 */
package com.asiainfo.gim.server.domain;

/**
 * @author zhangli
 *
 */
public class Asset
{
	private String code;
	private String manufacturer;
	private String modal;
	private String serialsNo;
	private String contacter;
	private String telephone;
	private String note;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public String getModal()
	{
		return modal;
	}

	public void setModal(String modal)
	{
		this.modal = modal;
	}

	public String getSerialsNo()
	{
		return serialsNo;
	}

	public void setSerialsNo(String serialsNo)
	{
		this.serialsNo = serialsNo;
	}

	public String getContacter()
	{
		return contacter;
	}

	public void setContacter(String contacter)
	{
		this.contacter = contacter;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

}
