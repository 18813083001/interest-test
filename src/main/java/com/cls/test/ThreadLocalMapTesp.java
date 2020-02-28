package com.cls.test;

public class ThreadLocalMapTesp {

    static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {

        for (int i =0;i < 20;i ++){
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocal.set(j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(threadLocal.get());
                }
            }
            ).start();
        }

    }
}
