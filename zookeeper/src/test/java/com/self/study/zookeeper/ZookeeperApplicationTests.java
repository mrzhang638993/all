package com.self.study.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.study.zookeeper.lock.CuratorLock;
import com.self.study.zookeeper.vo.User;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


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


 /* @Autowired
    private CuratorLock  curatorLock;*/
  @Test
    public   void  TestCuratorLock(){
        Thread  thread1= new Thread(new CuratorLock(curatorFramework,"thread1") );
        Thread  thread2= new Thread(new CuratorLock(curatorFramework,"thread2"));
        Thread  thread3= new Thread(new CuratorLock(curatorFramework,"thread3") );
        Thread  thread4= new Thread(new CuratorLock(curatorFramework,"thread4"));
        Thread  thread5= new Thread(new CuratorLock(curatorFramework,"thread5"));
      thread1.start();
      thread2.start();
      thread3.start();
      thread4.start();
      thread5.start();
    }
}
