package com.yue.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yue on 2018/5/25
 */
public class ConditionTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);


      //  lock.lock();
        Condition condition = lock.newCondition();

        final Thread thread1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 正在运行 .....");
                try {
                    Thread.sleep(20000);
                    System.out.println(Thread.currentThread().getName() + " 停止运行, 等待一个 signal ");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 获取一个 signal, 继续执行 ");
            } finally {
                lock.unlock();
            }

        });
        thread1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {

            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 正在运行 .....");
             //   thread1.interrupt();
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                condition.signal();
                System.out.println(Thread.currentThread().getName() + " 发送一个 signal ");
                System.out.println(Thread.currentThread().getName() + " 发送 signal 结束");

            } finally {
                lock.unlock();
            }

        }).start();


    }


}
