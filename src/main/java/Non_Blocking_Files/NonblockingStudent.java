package Non_Blocking_Files;

import Courses.Course;
import Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NonblockingStudent implements Runnable {
    public ConcurrentCourseCatalog ccc;
    public List<Course> classes;
    public NonblockingSchoolServer server;
    private List<Course> studentCourseList;

    public NonblockingStudent(NonblockingSchoolServer server) throws IOException {
        classes = new ArrayList<>();
        this.ccc = server.ccc;
        this.server = server;
        studentCourseList = ccc.getCourseList();
    }

    @Override
    public void run() {
        while (classes.size() < Constants.MAX_LOAD) {
            int size = studentCourseList.size();
            int rand = ThreadLocalRandom.current().nextInt(size);
            Course course = server.getCourse(rand);
            if (course != null) {
                    server.addCourse(course, this);
            }
        }
        //System.out.println("Student schedule complete for: "+ Thread.currentThread().getId());
    }
}
