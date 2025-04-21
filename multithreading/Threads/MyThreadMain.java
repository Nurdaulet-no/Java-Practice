package org.practice.multithreading.Threads;


// Count number from 1 to 10 first thread
// Count number from 10 to 1 second thread

public class MyThreadMain{
    public static void main(String[] args) {
        Thread countUp = new Thread(new MyRunnable1());
        Thread countDown = new Thread(new MyRunnable2());

        countUp.start();
        countDown.start();
    }
}

class MyRunnable1 implements Runnable{
    @Override
    public void run(){
        int countUp = 0;

        while (countUp < 10){
            try {
                countUp++;
                Thread.sleep(1000);
                System.out.println("Up: " + countUp);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}

class MyRunnable2 implements Runnable{
    @Override
    public void run(){
        int countDown = 11;

        while (countDown > 1){
            try {
                countDown--;
                Thread.sleep(1500);
                System.out.println("Down: " + countDown);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}

