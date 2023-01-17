package Non_Blocking_Files;

import Utils.Constants;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    From this main, we test nonblocking data structures; in this case concurrent hashmap, so that we can use JMH to benchmark
    performance against blocking, or lock, structure of data management. We initially set up a separate nonblocking server
    that initiates the concurrent hashmap. A threadpool is used to create a fixed number of threads, that is controlled in
    the constants file, that will then be used to initiate varying number of students and then register them for random classes.
*/

public class Non_Blocking_Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        NonblockingSchoolServer nbss = new NonblockingSchoolServer(Constants.MAX_STUDENTS);
        ExecutorService threadPool = Executors.newFixedThreadPool(Constants.TEST_THREADS_LARGE);
        for (int i=0;i< Constants.MAX_STUDENTS;i++){
            threadPool.submit(()-> {
                nbss.start();
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
