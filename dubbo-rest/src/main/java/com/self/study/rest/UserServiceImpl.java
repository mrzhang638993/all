package com.self.study.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.RpcContext;


@Path("user")
public class UserServiceImpl implements UserService {

    @POST
    @Path("regist")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    @Override
    public String regist(User user) {
        System.out.println(user);
        HttpServletRequest request = (HttpServletRequest) RpcContext.getContext().getRequest();
        String host = request.getLocalAddr();
        return "regist success!, ur host is:" + host;
    }

    @POST
    @Path("{id:\\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public User queryById(@PathParam("id") Long uid) {
        User user = new User(uid,"jetty",25);
        return user;
    }

}