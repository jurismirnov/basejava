package ru.javawebinar.basejava;

public class MainDeadLock {
    private static int counter;
    private static final String LOCK1 = "lock1";
    private static final String LOCK2 = "lock2";

    public static void main(String[] args) {
        startNewThread(LOCK1, LOCK2);
        startNewThread(LOCK2, LOCK1);
    }

    private static void startNewThread(Object lock1, Object lock2) {
        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " started");
            synchronized (lock1) {
                System.out.println(threadName + " " + lock1);
                threadPause();
                synchronized (lock2) {
                    System.out.println(threadName + " " + lock2);
                }
            }
            System.out.println(threadName + " completed");
        }).start();
    }

    private static void threadPause() {
        try {
            Thread.sleep(333);
        } catch (InterruptedException ignored) {
        }
    }
}