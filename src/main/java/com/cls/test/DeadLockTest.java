package com.cls.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class DeadLockTest {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();



    public static void main(String[] args) throws InterruptedException {
        DeadLockTest deadLockTest = new DeadLockTest();
        deadLockTest.lockTest();

    }

    public void lockTest(){
        synchronized (DeadLockTest.class) {
            System.out.println("A");

            Future future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized (DeadLockTest.class){
                        System.out.println("B");
                    }
                }
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

}
