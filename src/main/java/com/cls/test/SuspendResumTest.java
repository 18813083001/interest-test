package com.cls.test;

import java.util.concurrent.locks.LockSupport;

public class SuspendResumTest {
    public static void main(String[] args) {
//        Thread thread = Thread.currentThread();
//        thread.resume();
//        thread.suspend();
//        System.out.println("hello");

        LockSupport.unpark(Thread.currentThread());
        LockSupport.unpark(Thread.currentThread());
        LockSupport.unpark(Thread.currentThread());
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("hello");


    }
}
