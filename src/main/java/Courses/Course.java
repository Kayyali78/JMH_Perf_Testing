import static Utils.Constants.*;

public class Course {
    String courseId, name;
    int seats = SEATS_PER_COURSE;
    Boolean open;

    public Course(String courseId, String courseName){
        this.courseId = courseId;
        this.name = courseName;
        this.open = true;
    }

    

}
