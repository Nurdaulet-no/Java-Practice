package org.practice.multithreading.Threads;

import java.util.ArrayList;
import java.util.List;

public class WarehouseAndLoaders {
    public static void main(String[] args) {
        BoxStorage storage = new BoxStorage();
        Thread loader1 = new Thread(new Loader(storage), "Loader1");
        Thread loader2 = new Thread(new Loader(storage), "Loader2");

        loader1.start();
        loader2.start();
    }
}

class BoxStorage{
    List<Integer> boxes = new ArrayList<>();

    public BoxStorage(){
        for (int i = 1; i <= 10; i++){
            boxes.add(i);
        }
    }

    public synchronized Integer getBox(){
        if(!boxes.isEmpty()){
            return boxes.remove(0);
        }

        return null;
    }
}

class Loader implements Runnable{
    BoxStorage storage;

    public Loader(BoxStorage storage){
        this.storage = storage;
    }

    @Override
    public void run(){
        while (true){
            Integer box = storage.getBox();
            if(box == null) break;

            try {
                System.out.println("[" + Thread.currentThread().getName() + "] received box: " + box);
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
