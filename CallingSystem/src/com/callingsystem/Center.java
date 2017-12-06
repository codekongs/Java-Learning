package com.callingsystem;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * 服务中心
 */
public class Center extends Thread {
    private UserInterface mUserInterface;

    private final static int MAXCOUNT = 3;
    //储蓄员阻塞队列
    private BlockingQueue<Waiter> waiters;
    //客户阻塞队列
    private BlockingQueue<Customer> customers;

    private Random rand = new Random(47);

    //用于产生消费线程的休眠随机时间
    private final static int CONSUMERSLEEPSEED = 10000;
    //用于产生生产线程的休眠随机时间
    private final static int PRODUCERSLEEPSEED = 3000;

    public Center(UserInterface userInterface) {
        this.mUserInterface = userInterface;

        //创建3名提供服务的工作人员
        this.waiters = new LinkedBlockingQueue<Waiter>(MAXCOUNT);
        for (int i = 0; i < MAXCOUNT; i++) {
            waiters.add(new Waiter());
        }
        //工作人员工作就绪,创建客户队列
        this.customers = new LinkedBlockingQueue<Customer>();
    }

    //增加客户
    public void produce() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(PRODUCERSLEEPSEED));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();
        this.customers.add(customer);

        mUserInterface.setEnterText(customer.getId());
        System.out.println("产生顾客 " + customer.getId());

        if (customer.getId() > 3){
            //前3名顾客直接开始服务
            if (!mUserInterface.updateRestAraeChairNum(customer.getId())){
                mUserInterface.addNoChairPeople(customer.getId());
            }
        }
    }

    // 为客户服务
    public void consume() {
        try {
            // 服务窗口可用
            Waiter waiter = this.waiters.take();
            this.waiters.remove(waiter);

            // 客户可用
            Customer customer = this.customers.take();
            //将客户从阻塞队列中移除
            this.customers.remove(customer);
            //顾客离开椅子来到窗口接受服务
            mUserInterface.removeNumFromChair(customer.getId());
            //更新窗口服务对象的值
            mUserInterface.updateServicedPeopleNum(waiter.getId() - 1, customer.getId());

            // 窗口显示
            System.out.println(waiter + "正在为" + customer + "服务...");
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(CONSUMERSLEEPSEED));

            //服务完成继续等待服务
            this.waiters.add(waiter);
        } catch (InterruptedException e) {
            System.err.println("---" + e.getMessage());
        }
    }
} 