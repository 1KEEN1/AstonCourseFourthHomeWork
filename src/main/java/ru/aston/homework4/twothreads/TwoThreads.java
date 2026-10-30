package ru.aston.homework4.twothreads;

public class TwoThreads {

    private static final Object lock = new Object();

    private static boolean firstTurn = true;

    public static void main(String[] args) {

        Runnable task1 = () -> {
            while (true) {
                synchronized (lock) {
                    while (!firstTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.print("1");
                    firstTurn = false;
                    lock.notifyAll();
                }
            }
        };

        Runnable task2 = () -> {
            while (true) {
                synchronized (lock) {
                    while (firstTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("2");
                    firstTurn = true;
                    lock.notifyAll();
                }
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }
}
