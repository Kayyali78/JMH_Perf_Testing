package Non_Blocking_Files;

import Courses.Course;
import Utils.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCourseCatalog {
    List<Course> courseList = new ArrayList<>();
    public static ConcurrentHashMap<Integer, Course> courseConcurrentHashMap = new ConcurrentHashMap<>();
    public int[] courseArray;

    public ConcurrentCourseCatalog(String filename) throws IOException {
        readIn(filename);
    }
    public ConcurrentCourseCatalog() throws IOException {
        readIn("/home/sweeks/courselisttest.txt");
    }

    private void readIn(String filename) throws IOException {
        List<String> tempCourseList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while (br.ready()){
            String str = br.readLine();
            tempCourseList.add(str);
        }


//        Scanner scanner = new Scanner(new FileReader(filename));
//        List<String> tempCourseList = new ArrayList<>();
//        while (scanner.hasNextLine()){
//            String str = scanner.nextLine();
//            tempCourseList.add(str);
//        }
        String[] courseStrings = tempCourseList.toArray(new String[0]);
        for (int i=0;i < courseStrings.length & i < Constants.MAX_COURSES;i++){
            if (courseStrings[i] != null) {
                createCourse(courseStrings[i]);
            } else {
                break;
            }
        }
        for (Course c : courseList){
            courseConcurrentHashMap.put(c.courseId.hashCode(),c);
        }
        createCourseArray();
    }
    private void createCourse(String courseString){
        String[] tempCourseInfo = courseString.split("-", 2);
        Course temp = new Course(tempCourseInfo[0], tempCourseInfo[1]);
        courseList.add(temp);
    }
    private void createCourseArray(){
        courseArray = new int[courseConcurrentHashMap.size()];
        Course[] temp = courseList.toArray(new Course[0]);
        for (int i=0;i< courseConcurrentHashMap.size();i++){
            Course tempCourse = temp[i];
            courseArray[i] = tempCourse.courseId.hashCode();
        }
    }

    public List<Course> getCourseList(){return courseList;}
    public ConcurrentHashMap<Integer, Course> getCourseConcurrentHashMap() {
        return courseConcurrentHashMap;
    }

}
