package com.self.study.netty.rpc.client;



import com.self.study.netty.rpc.client.net.NetClientV2;
import com.self.study.netty.rpc.common.protocol.Request;
import com.self.study.netty.rpc.common.protocol.Response;
import com.self.study.netty.rpc.discovery.ServiceInfo;
import com.self.study.netty.rpc.discovery.ServiceInfoDiscoverer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClientStubProxyFactory
 * 客户端存根代理工厂
 */
public class ClientStubProxyFactory {

    private ServiceInfoDiscoverer sid;

    private NetClientV2 netClient;

    private Map<Class<?>, Object> objectCache = new HashMap<>();

    /**
     * @param <T>
     * @param interf
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> interf) {
        T obj = (T) this.objectCache.get(interf);
        if (obj == null) {
            obj = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class<?>[]{interf},
                    new ClientStubInvocationHandler(interf));
            this.objectCache.put(interf, obj);
        }

        return obj;
    }

    public ServiceInfoDiscoverer getSid() {
        return sid;
    }

    public void setSid(ServiceInfoDiscoverer sid) {
        this.sid = sid;
    }

    public NetClientV2 getNetClient() {
        return netClient;
    }

    public void setNetClient(NetClientV2 netClient) {
        this.netClient = netClient;
    }

    /**
     * ClientStubInvocationHandler
     * 客户端存根代理调用实现
     */
    private class ClientStubInvocationHandler implements InvocationHandler {
        private Class<?> interf;

        public ClientStubInvocationHandler(Class<?> interf) {
            super();
            this.interf = interf;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 1、获得服务信息
            String serviceName = this.interf.getName();
            ServiceInfo sinfo = sid.getServiceInfo(serviceName);

            if (sinfo == null) {
                throw new Exception("远程服务不存在！");
            }

            // 2、构造request对象
            Request req = new Request();
            req.setServiceName(sinfo.getName());
            req.setMethod(method.getName());
            req.setPrameterTypes(method.getParameterTypes());
            req.setParameters(args);
            req.setRequestId(UUID.randomUUID().toString());

            // 4、调用网络层发送请求
            Response rsp = netClient.sendRequest(req, sinfo);

            // 6、结果处理
            if (rsp.getException() != null) {
                throw rsp.getException();
            }

            return rsp.getReturnValue();
        }
    }
}
