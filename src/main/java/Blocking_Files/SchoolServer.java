package Blocking_Files;

import Courses.Course;
import Courses.CourseCatalog;
import Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SchoolServer {
    Hashtable<Integer, Course> courseHashtable;
    public List<Student> studentList;
    public CourseCatalog cc;
    private final ReentrantReadWriteLock RWL = new ReentrantReadWriteLock(true);
    private final Lock read = RWL.readLock();
    private final Lock write = RWL.writeLock();

    public SchoolServer(int students) throws IOException {
        this.setUp(students);
        }
    public final void setUp(int students) throws IOException {
        cc = new CourseCatalog("/home/sweeks/courselisttest.txt");
        for (int i=0;i<students;i++) {
            studentList = new ArrayList<>();
            studentList.add(new Student(this));
        }
        courseHashtable = cc.getCourseHashtable();
    }
    public Course getCourse (int element){
        read.lock();
        try {
            Integer elem = cc.courseArray[element];
            return cc.getCourseHashtable().get(elem);}
        finally {read.unlock();}
    }

    public void addCourse(Course c, Student s){
        write.lock();
        try{
            if (courseHashtable.get(c.courseId.hashCode()).open) {
                c.seats--;
                c.studentList.add(s);
                s.classes.add(c);
                //System.out.println("Course added: "+c+" for Student: "+s);
                if (c.seats == 0) {
                    c.open = false;
                }
            }
        } finally {
            write.unlock();
        }
    }

    public void start(){
        for (Student s: studentList){
            s.run();
        }
    }
}
