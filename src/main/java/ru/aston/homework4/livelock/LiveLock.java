package ru.aston.homework4.livelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {

    public static void log(String text) {
        String name = Thread.currentThread().getName();
        System.out.println(name + ": " + text);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lock first = new ReentrantLock();
        Lock second = new ReentrantLock();

        Runnable locker = () -> {
            boolean firstLocked = false;
            boolean secondLocked = false;
            try {
                while (!firstLocked || !secondLocked) {
                    firstLocked = first.tryLock(100, TimeUnit.MILLISECONDS);
                    log("First Locked: " + firstLocked);
                    secondLocked = second.tryLock(100, TimeUnit.MILLISECONDS);
                    log("Second Locked: " + secondLocked);
                }
                first.unlock();
                second.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(locker).start();
        new Thread(locker).start();
    }
}
