package Blocking_Files;

import Blocking_Files.Student;
import Courses.Course;
import Courses.CourseCatalog;
import Utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;

import static Utils.Constants.FILENAME;

public class SchoolServer {
    Hashtable<Integer, Course> courseHashtable;

    private List<Student> studentList;
    private CourseCatalog cc;

    public SchoolServer() throws IOException {
        this.start();
        }
    public final void start() throws IOException {
        cc = new CourseCatalog(new File(FILENAME));
        studentList = new ArrayList<>();
        for (int i=0;i< Constants.MAX_STUDENTS;i++){
            studentList.add(new Student(cc));
        }
        courseHashtable = cc.getCourseHashtable();
    }
}
