package Courses;

import Blocking_Files.Student;
import Non_Blocking_Files.NonblockingStudent;

import java.util.ArrayList;
import java.util.List;

import static Utils.Constants.*;

public class Course {
    public String courseId, name;
    public int seats;
    public Boolean open;

    public List<Student> studentList;
    public List<NonblockingStudent> nbStudentList;
    public Course(String courseId, String courseName){
        this.courseId = courseId;
        this.name = courseName;
        this.open = true;
        this.studentList = new ArrayList<>();
        this.nbStudentList = new ArrayList<>();
        this.seats = SEATS_PER_COURSE;
    }

    public synchronized String toString(){
        return courseId+" -> "+name;
    }
}
