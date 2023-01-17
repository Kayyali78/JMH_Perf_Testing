package Blocking_Files;

import Utils.Constants;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Blocking_Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        SchoolServer ss = new SchoolServer(Constants.MAX_STUDENTS);
        ExecutorService threadPool = Executors.newFixedThreadPool(Constants.TEST_THREADS_LARGE);
        for (int i=0;i<Constants.MAX_STUDENTS;i++){
            threadPool.submit(() -> {
                ss.start();
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
