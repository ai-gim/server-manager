/**
 * @File: Main.java 
 * @Package  com.asiainfo.gim.server
 * @Description: 
 * @author luyang
 * @date 2015年8月6日 上午9:44:15 
 * @version V1.0
 * 
 */
package com.asiainfo.gim.server;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.gim.common.spring.SpringContext;

public class Main
{
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		// 初始化Spring
		String[] springConfigFiles = {"spring-base.xml", "spring-db.xml", "spring-amqp.xml", "spring-ipmi.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfigFiles);

		// 初始化日志
		DOMConfigurator.configure(Main.class.getClassLoader().getResource("log4j.xml"));

		// 启动 HTTP Server
		String host = SpringContext.getProperty("service.server.host");
		int port = NumberUtils.toInt(SpringContext.getProperty("service.server.port"), 9002);

		URI baseUri = UriBuilder.fromUri("http://" + host).port(port).build();
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, new ServerApplication());
		server.start();
	}
}
