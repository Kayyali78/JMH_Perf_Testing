package Courses;

import Utils.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CourseCatalog {
    List<Course> courseList = new ArrayList<>();
    public static Hashtable<Integer, Course> courseHashtable = new Hashtable<>();
    public int[] courseArray;
    private final ReentrantReadWriteLock RWL = new ReentrantReadWriteLock(true);
    private final Lock read = RWL.readLock();

    public CourseCatalog(String filename) throws IOException {
        readIn(filename);
    }
    public CourseCatalog() throws IOException {
        String filename = "/home/sweeks/courselisttest.txt";
        readIn(filename);
    }

    private void readIn(String filename) throws IOException {
        List<String> tempCourseList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while (br.readLine() != null) {
            line = br.readLine();
            tempCourseList.add(line);
        }
        String[] courseStrings = tempCourseList.toArray(new String[0]);
        for (int i=0;i < courseStrings.length & i < Constants.MAX_COURSES;i++){
            if (courseStrings[i] != null) {
                createCourse(courseStrings[i]);
            } else {
                break;
            }
        }
        for (Course c : courseList){
            courseHashtable.put(c.courseId.hashCode(),c);
        }
        createCourseArray();
    }
    private void createCourse(String courseString){
            String[] tempCourseInfo = courseString.split("-", 2);
            Course temp = new Course(tempCourseInfo[0], tempCourseInfo[1]);
            courseList.add(temp);
        //System.out.println("Course added: "+temp);
    }
    private void createCourseArray(){
        courseArray = new int[courseHashtable.size()];
        Course[] temp = courseList.toArray(new Course[0]);
        for (int i=0;i< courseHashtable.size();i++){
            Course tempCourse = temp[i];
            courseArray[i] = tempCourse.courseId.hashCode();
        }
    }

    public List<Course> getCourseList(){
        read.lock();
        try {
            return courseList;
        } finally {
            read.unlock();
        }
    }
    public Hashtable<Integer,Course> getCourseHashtable() {
        read.lock();
        try {
            return courseHashtable;
        } finally   {
            read.unlock();
        }
    }

}
