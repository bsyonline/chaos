package com.rolex.alphax.concurrent.thread;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 end");
        }, "t1");
        t1.start();
        //t1.join(); 让主线程等待t1线程执行完
        System.out.println("main end");
    }
}