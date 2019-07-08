package com.self.study.zookeeper.lock;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class CuratorLock  implements   Runnable{

    // 执行分布式锁的节点
    private String lockPAth = "/stu";
    private int i;
    private String clientName;
    //  分布式锁
    private InterProcessMutex lock;

    public CuratorLock(CuratorFramework  client,String clientName) {
        lock = new InterProcessMutex(client, lockPAth);
        this.clientName = clientName;
    }


    @Override
    public void run() {
        try {
            Thread.sleep((new java.util.Random().nextInt(2000)));
            lock.acquire();  //增加第一把锁
            i++;
            System.out.println("当前数值：########################################"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                lock.release();
                System.out.println(clientName + " 释放锁");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
