package com.self.study.zookeeper;

import com.self.study.zookeeper.lock.ImproveLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperApplicationTests {

    private AtomicInteger result = new AtomicInteger(0);
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
                       result.getAndIncrement();
                        System.out.println("获取到了互斥锁:{}" + result.get() );
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


    @Autowired
     private  ImproveLock  improveLock;


    //  测试zk的分布式锁
    @Test
    public  void    testZookeeperDistribute(){
        for (int  i=0;i<100;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        improveLock.lock();

                        result.getAndIncrement();
                        System.out.println("获取到了互斥锁:{}" + result.get() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            improveLock.unlock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
