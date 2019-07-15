package com.self.study.netty.rpc.server;

import com.self.study.netty.rpc.common.protocol.JSONMessageProtocol;
import com.self.study.netty.rpc.server.register.ServiceObject;
import com.self.study.netty.rpc.server.register.ServiceRegister;
import com.self.study.netty.rpc.server.register.ZookeeperExportServiceRegister;

import java.io.IOException;
import java.util.Map;


/**
 * ServerBootstap
 * 
 * 
 */
public class RpcBootstrap {
	private ServiceRegister register = new ZookeeperExportServiceRegister();
	private ServiceLoader loader = new ServiceLoader();
	private Class protocol = JSONMessageProtocol.class;
	private int port = 9082;
	private RpcServer server;
	
	public void start(String packName) {
		Map<String, Object> services = loader.getService(packName);
		System.out.println("扫描到实现类："+services);
		
		services.forEach((k,v)->{
			Class<?> interf = null;
			Class<?>[] interfaces = v.getClass().getInterfaces();
			for(Class<?> face : interfaces) {
				if(k.equals(face.getName())) {
					interf = face;
				}
			}
			ServiceObject so = new ServiceObject(k, interf, v);
			register.register(so, protocol.getName(), port);
			System.out.println("完成类的注册："+so);
		});
		
		server = new RpcServer(protocol, port);
		RequestHandler handler = new RequestHandler();
		handler.setServiceRegister(register);
		server.setHandler(handler);
		server.start();
	}
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProtocol() {
		return protocol.getName();
	}

	public void setProtocol(Class protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

