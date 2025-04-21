package org.practice.multithreading.Threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessing {
    public static void main(String[] args) {
        Thread importantFiles = new Thread(new ProcessFiles("important_file"));
        Thread regularFiles = new Thread(new ProcessFiles("regular_file"));
        Thread backgroundFiles = new Thread(new ProcessFiles("background file"));

        importantFiles.setName("Important Files");
        regularFiles.setName("Regular Files");
        backgroundFiles.setName("Background Files");

        importantFiles.setPriority(Thread.MAX_PRIORITY);
        regularFiles.setPriority(Thread.NORM_PRIORITY);
        backgroundFiles.setPriority(Thread.MIN_PRIORITY);

        importantFiles.start();
        regularFiles.start();
        backgroundFiles.start();

        try {
            importantFiles.join();
            regularFiles.join();
            backgroundFiles.join();
        }catch (InterruptedException e){
            System.out.println("Thread interrupted");
        }
        System.out.println("All files processed");
    }
}

class ProcessFiles implements Runnable{
    String prefix;

    public ProcessFiles(String prefix){
        this.prefix = prefix;
    }

    @Override
    public void run(){

        for(int i = 0; i < 5; i++){
            if(i == 2 && Thread.currentThread().getName().equals("Background Files")){
                Thread.currentThread().interrupt();
            }

            if(Thread.currentThread().isInterrupted()){
                System.out.println("[" + Thread.currentThread().getName() + "] was interrupted");
            }

            Path path = Path.of("src/main/java/org/practice/multithreading/Threads/just/"+ prefix+"_" + i);
            try{
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                Files.writeString(path, "Index of this file is - "+i);
                System.out.println("[" + Thread.currentThread().getName() + "] processed");
                Thread.sleep(500);
            }catch (InterruptedException | IOException e){
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
    }
}