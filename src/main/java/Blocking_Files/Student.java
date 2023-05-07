package Blocking_Files;

import Courses.Course;
import Courses.CourseCatalog;
import Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Student implements Runnable {
    public List<Course> classes;
    private List<Course> studentCourseList;
    CourseCatalog cc;
    public SchoolServer server;

    private final ReentrantReadWriteLock RWL = new ReentrantReadWriteLock(true);
    private final Lock write = RWL.writeLock();

    public Student(SchoolServer server) throws IOException {
        classes = new ArrayList<>();
        this.cc = server.cc;
        this.server = server;
        studentCourseList = cc.getCourseList();
    }
    @Override
    public void run() {
        try {
            while (classes.size() < Constants.MAX_LOAD) {
                int size = studentCourseList.size();
                int rand = ThreadLocalRandom.current().nextInt(size);
                write.lock();
                try {
                    Course course = server.getCourse(rand);
                    if (course != null) {
                        server.addCourse(course, this);
                    }
                } finally {
                    write.unlock();
                }
            }
            //System.out.println("Student schedule complete for: "+ Thread.currentThread().getId());

        } catch (Exception e){e.printStackTrace();}
    }
}
