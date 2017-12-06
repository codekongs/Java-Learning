package com.callingsystem;

/**
 * 模拟客户完成业务需求后,服务人员空闲就绪
 */
public class Consumer implements Runnable {
    private Center center;

    public Consumer(Center center) {
        this.center = center;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            center.consume();
        }
    }

}  