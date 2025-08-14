package com.hezhu.octopusaiagent.demo;

/**
 * @Author Marshall
 * @Date 2025/7/31 17:48
 * @Description: 死锁案例
 */
public class DeadLockDemo {
    private final static Object lockA = new Object();
    private final static Object lockB = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lockA) {
                System.out.println("线程1获取A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lockB) {
                    System.out.println("线程1获取B");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println("线程2获取B");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lockA) {
                    System.out.println("线程2获取A");
                }
            }
        }).start();
    }
}
