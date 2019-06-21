package com.etc.mainboot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ChenDang
 * @date 2019/6/12 0012
 */
public class ThreadTest {

    private static final int loopNum = 1*10;

    public static void main(String args[]) throws InterruptedException {
        ThreadTest TestThreadPool = new ThreadTest();
        long bt = System.currentTimeMillis();
        TestThreadPool.m1();
        long et2 = System.currentTimeMillis();
        System.out.println("[1]耗时:"+(et2 - bt)+ "ms");
        Thread thread = new Thread();
        long at = System.currentTimeMillis();
        TestThreadPool.m2();
        long et3 = System.currentTimeMillis();
        System.out.println("[2]耗时:"+(et3 - at)+ "ms");

    }

    public void m1() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int index = 0; index < loopNum; index++) {
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        new Thread().sleep(1000);  //模拟耗时操作
                        System.out.println("[1]" + Thread.currentThread().getName());
                    } catch (Exception e) {
                    }
                }
            };
            pool.execute(run);
        }
        System.out.println("[1] done!");
        pool.shutdown();
    }

    public void m2() {
        AtomicInteger connectionIds = new AtomicInteger(0);
        for (int index = 0; index < loopNum; index++) {
            try {
                new Thread().sleep(1000);  //模拟耗时操作
                System.out.println("[2]" + Thread.currentThread().getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("[2] done!");
    }
}
