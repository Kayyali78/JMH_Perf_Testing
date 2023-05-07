package Benchmarks;

import Blocking_Files.SchoolServer;
import Non_Blocking_Files.NonblockingSchoolServer;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class Benchmarks {

    @State(Scope.Benchmark)
    public static class CustomHash {
        SchoolServer ss;

        @Param({ "50","100"})
        public int students;

        @Setup(Level.Trial)
        public void setup() throws Exception {
            ss = new SchoolServer(students);
        }
    }

    @State(Scope.Benchmark)
    public static class ConcurrentMap {
        NonblockingSchoolServer nbss;

        @Param({ "50","100"})
        public int students;

        @Setup(Level.Trial)
        public void setup() throws Exception {
            nbss = new NonblockingSchoolServer(students);
        }
    }

    @State(Scope.Thread)
    public static class BlockingStudent {

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.AverageTime)
        @Fork(value = 2,warmups = 2)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)

        @Threads(20)
       public void blockingStudentbench1(CustomHash ch) {
            ch.ss.start();
       }
        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.AverageTime)
        @Fork(value = 2,warmups = 2)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)

        @Threads(Threads.MAX)
       public void blockingStudentbench2(CustomHash ch) {
            ch.ss.start();
       }

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.Throughput)
        @Fork(value = 2,warmups = 2)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Threads(20)
        public void blockingStudentbench3(CustomHash ch) {
            ch.ss.start();
        }

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.Throughput)
        @Fork(value = 2,warmups = 2)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Threads(Threads.MAX)
        public void blockingStudentbench4(CustomHash ch) {
            ch.ss.start();
        }
    }

    @State(Scope.Thread)
    public static class NonBlockingStudent {

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.AverageTime)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Fork(value = 2,warmups = 2)
        @Threads(20)
        public void nonblockingStudentbench1(ConcurrentMap concurrentMap) {
            concurrentMap.nbss.start();
        }
        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.AverageTime)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Fork(value = 2,warmups = 2)
        @Threads(Threads.MAX)
        public void nonblockingStudentbench2(ConcurrentMap concurrentMap) {
            concurrentMap.nbss.start();
        }

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.Throughput)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Fork(value = 2,warmups = 2)
        @Threads(20)
        public void nonblockingStudentbench3(ConcurrentMap concurrentMap) {
            concurrentMap.nbss.start();
        }

        @Benchmark
        @OutputTimeUnit(TimeUnit.NANOSECONDS)
        @BenchmarkMode(Mode.Throughput)
        @Warmup( iterations = 3, time = 2)
        @Measurement(iterations = 4)
        @Fork(value = 2,warmups = 2)
        @Threads(Threads.MAX)
        public void nonblockingStudentbench4(ConcurrentMap concurrentMap) {
            concurrentMap.nbss.start();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(Benchmarks.class.getSimpleName()).build();

        new Runner(options).run();
    }
}
