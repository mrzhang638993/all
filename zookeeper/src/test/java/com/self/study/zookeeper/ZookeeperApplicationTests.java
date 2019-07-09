package com.self.study.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperApplicationTests {

    private int result = 0;
    @Test
    public void contextLoads() {
    }


    @Autowired
    private CuratorFramework  client;

    //  curator  简易的分布式锁的实现，对应的是简易的分布式锁的实现的。
    @Test
    public    void  testZookeeper() {
        String lockPath = "/stu";
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
       // 尝试创建多个分布式锁来实现相关的机制
        for (int  i=0;i<100;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.acquire();
                        result++;
                        System.out.println("获取到了互斥锁:{}" + result );
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
