package com.cls.test;

public class TwoThreadPrint {

    public static void main(String[] args) {

        Object lock1 = "lock1";
        Object lock2 = "lock2";
        FlagObject flagObject = new FlagObject();

        Thread1 thread1 = new Thread1(lock1,lock2,flagObject);
        Thread2 thread2 = new Thread2(lock1,lock2,flagObject);
        thread1.start();
        thread2.start();

    }

}

class FlagObject {
    private boolean thread1Pause = false;
    private boolean thread2Pause = false;

    public boolean isThread1Pause() {
        return thread1Pause;
    }

    public void setThread1Pause(boolean thread1Pause) {
        this.thread1Pause = thread1Pause;
    }

    public boolean isThread2Pause() {
        return thread2Pause;
    }

    public void setThread2Pause(boolean thread2Pause) {
        this.thread2Pause = thread2Pause;
    }
}




class Thread1 extends Thread{

    private Object lock1;
    private Object lock2;

    private FlagObject flagObject;

    public Thread1(Object lock1,Object lock2,FlagObject flagObject){
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.flagObject = flagObject;

    }

    @Override
    public void run() {
        for (int i=0;i <= 100;i++){
            System.out.println(Thread.currentThread().getName()+": "+i);
            i++;

            //只有等thread2暂停后才notify，防止thread2还未执行或还未暂停（thread2执行完lock1.notify()后，可能被调度为睡眠，导致lock2.wait()还未执行）
            while (!flagObject.isThread2Pause()){
            }
            synchronized (lock2){
                lock2.notify();
            }

            //打印完100后，应该结束
            if (i <= 100){
                synchronized (lock1){
                    try {
                        flagObject.setThread1Pause(true);
                        lock1.wait();
                        flagObject.setThread1Pause(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }
}

class Thread2 extends Thread{
    private Object lock1;
    private Object lock2;
    private FlagObject flagObject;

    public Thread2(Object lock1,Object lock2,FlagObject flagObject){
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.flagObject = flagObject;

    }

    @Override
    public void run() {
        for (int i=1;i < 100;i++){
            synchronized (lock2){
                try {
                    //thread2未执行完wait方法之前，thread1不可能执行lock2.notify,因为他没有锁
                    flagObject.setThread2Pause(true);
                    lock2.wait();
                    flagObject.setThread2Pause(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.println(Thread.currentThread().getName()+": "+i);
            i++;

            //只有等thread1暂停后，才调用lock1.notify()，防止thread1执行lock2.notify()代码后，就被调度为睡眠,导致lock1.wait()为执行到（也就是thread1还未暂停）
            while (!flagObject.isThread1Pause()){

            }

            synchronized (lock1){
                lock1.notify();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        flagObject.setThread2Pause(true);

    }
}
