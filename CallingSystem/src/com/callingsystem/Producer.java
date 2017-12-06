package com.callingsystem;

/**
 * 生产者线程,模拟银行工作人员服务完成一位客户后开始准备服务下一位客户
 */
public class Producer implements Runnable {
    private Center center;

    public Producer(Center center) {
        this.center = center;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //产生客户
            center.produce();
        }
    }
}