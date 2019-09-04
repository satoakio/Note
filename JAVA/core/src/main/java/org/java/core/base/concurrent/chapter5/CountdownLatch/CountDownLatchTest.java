package org.java.core.base.concurrent.chapter5.CountdownLatch;

import java.util.concurrent.CountDownLatch;

// 计数器
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException{
        // 给一个初始值2,
        final CountDownLatch latch = new CountDownLatch(2);

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("子线程: " + Thread.currentThread().getName() + "正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程: " + Thread.currentThread().getName() + "执行完毕");
                    // 该方法会将 AQS 内置的一个 state 状态 -1
                    latch.countDown();//将count值减1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("等待2个子线程执行完毕..");

        // 主线程就会等到CountDownLatch的值为0时才能继续往下执行
        // 如果CountDownLatch设置的值大于2的话，那么主线程就会一直等待下去，
        // 因为上面2个线程,即使CountDownLatch的值即使减去2次，还是大于0，主线程只能一直等待。
        latch.await(); // 调用await()方法的线程会被挂起，它会等待直到AQS 内置的 state为0才继续执行
        // 如果不想一直等待下去，可以调用:await(long timeout, TimeUnit unit)

        System.out.println("2个子线程已经执行完毕...");
        System.out.println("继续执行主线程");
    }
}