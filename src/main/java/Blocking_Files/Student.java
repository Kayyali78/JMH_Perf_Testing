import Courses.Course;
import Courses.CourseCatalog;
import Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Student implements Runnable {
    private long studentId;
    List<Course> classes;
    private List<Course> studentCourseList;
    CourseCatalog cc;
    private final ReentrantReadWriteLock RWL = new ReentrantReadWriteLock(true);
    private final Lock read = RWL.readLock();
    private final Lock write = RWL.writeLock();

    public Student() throws IOException {
        classes = new ArrayList<>();
        cc = new CourseCatalog();
        studentCourseList = cc.getCourseList();
    }

    public List<Course> getClasses(){ return classes;}
    public void setCourseCatalog(CourseCatalog courses){this.cc = courses;}

    @Override
    public void run() {
        try {
            int size = studentCourseList.size();
            int rand = ThreadLocalRandom.current().nextInt(size);
            classes.add(cc.getCourse(rand));

        } catch (Exception e){e.printStackTrace();}
    }

    public long getStudentId(){return studentId;}

}
