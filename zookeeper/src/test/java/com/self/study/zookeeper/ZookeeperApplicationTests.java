package com.self.study.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.study.zookeeper.vo.User;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.server.UnicastRemoteObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private ZkClient  zkClient;


    @Test
    public   void   testCreateNode(){
        String  path="/mynode";
        String  data="hello";
        String result = zkClient.create(path, data, CreateMode.PERSISTENT);
        System.out.println(result);
        //  测试节点的获取
        String  response = zkClient.readData(path);
        System.out.println(response);
    }


    @Test
    public   void   testCreateNodeForObject(){
        String  path="/stu";

        User user= new  User();
        user.setAge(18);
        user.setName("zhangchenglong");
        String result = zkClient.create(path, user, CreateMode.PERSISTENT);
        System.out.println(result);
        //  测试节点的获取
        User  response = zkClient.readData(path);
        System.out.println(response);
    }


    @Autowired
    private CuratorFramework  curatorFramework;

    @Test
    public  void  testCurator() throws Exception {
        User user= new  User();
        user.setAge(18);
        user.setName("zhangchenglong");
        String result = JSON.toJSONString(user);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/user",result.getBytes());
        //  测试接受的信息
        byte[] bytes = curatorFramework.getData().forPath("/user");
        JSONObject parse = (JSONObject) JSON.parse(new String(bytes));
        System.out.println(parse.get("name")+"###########"+parse.get("age"));
     /*   System.out.println(user1.toString());*/
        //byte[] bytes = curatorFramework.getData().forPath("/user");
    }
}
