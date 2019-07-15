package com.self.study.netty.rpc.client.net;


import com.self.study.netty.rpc.discovery.ServiceInfo;

public interface NetClient {
	byte[] sendRequest(byte[] data, ServiceInfo sinfo);
}
