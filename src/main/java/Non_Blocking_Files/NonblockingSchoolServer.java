package Non_Blocking_Files;

import Blocking_Files.Student;
import Courses.Course;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NonblockingSchoolServer {
    ConcurrentHashMap<Integer, Course> courseConcurrentHashMap;
    public ConcurrentCourseCatalog ccc;
    public List<NonblockingStudent> studentList;

    public NonblockingSchoolServer(int students) throws IOException {
        this.setUp(students);
    }
    public Course getCourse (int element){
            Integer elem = ccc.courseArray[element];
            return ccc.getCourseConcurrentHashMap().get(elem);
    }
    public void addCourse(Course c, NonblockingStudent s){
        if (courseConcurrentHashMap.get(c.courseId.hashCode()).open) {
            Course temp = courseConcurrentHashMap.get(c.courseId.hashCode());
            temp.seats--;
            temp.nbStudentList.add(s);
            s.classes.add(c);
            System.out.println("Course added: "+c+" for Student: "+s);
            if (temp.seats == 0) {
                temp.open = false;
            }
            courseConcurrentHashMap.replace(temp.courseId.hashCode(),c, temp);
        }
    }
    public final void setUp(int students) throws IOException {
        ccc = new ConcurrentCourseCatalog("/home/sweeks/courselisttest.txt");
        courseConcurrentHashMap = ccc.getCourseConcurrentHashMap();
        for (int i=0;i<students;i++) {
            studentList = new ArrayList<>();
            studentList.add(new NonblockingStudent(this));
        }
    }

    public void start(){
        for (NonblockingStudent s: studentList){
            s.run();
        }
    }
}
