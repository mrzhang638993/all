package com.self.study.netty.rpc.discovery;

/**
 * ServiceInfoDiscoverer
 * 远程服务信息发现接口
 */
public interface ServiceInfoDiscoverer {
	/**
	 * 根据服务名获得远程服务信息
	 * @param name
	 * @return
	 */
	ServiceInfo getServiceInfo(String name);
}
