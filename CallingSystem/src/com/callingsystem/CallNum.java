package com.callingsystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟银行叫号系统
 */
public class CallNum {

    public static void main(String[] args) throws InterruptedException {
        //显示界面
        start(new UserInterface());
    }

    private static void start(UserInterface userInterface) throws InterruptedException {
        //创建服务中心,如一个银行的营业厅
        Center center = new Center(userInterface);
        //足够多的空椅子
        ExecutorService exec = Executors.newCachedThreadPool();
        //模拟产生服务人员
        Producer producer = new Producer(center);
        //模拟产生N多客户
        Consumer consumer = new Consumer(center);
        exec.execute(producer);
        exec.execute(consumer);

        exec.execute(consumer);
        TimeUnit.SECONDS.sleep(10);
        exec.shutdown();
    }
}