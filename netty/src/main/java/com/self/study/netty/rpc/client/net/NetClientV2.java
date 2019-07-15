package com.self.study.netty.rpc.client.net;


import com.self.study.netty.rpc.common.protocol.Request;
import com.self.study.netty.rpc.common.protocol.Response;
import com.self.study.netty.rpc.discovery.ServiceInfo;

public interface NetClientV2 {
	Response sendRequest(Request data, ServiceInfo sinfo);
}
