package com.self.study.netty.rpc.demo.provider;


import com.self.study.netty.rpc.server.RpcBootstrap;

public class Provider {
	
	/*
	 * 运行代码依赖zk地址，在app.properties中配置即可
	 * 配置项：zk.address=
	 */
	public static void main(String[] args) throws Exception {
		RpcBootstrap bootstrap = new RpcBootstrap();
		bootstrap.start("edu.dongnao.study.rpc.demo");

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				bootstrap.close();
			}

		}));

	}
}
