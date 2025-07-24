package org.example.learningmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Model.Instructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CourseService {

    private ArrayList<Course> courses = new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public boolean updateCourse(String courseCode, Course course){
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getCourseCode().equals(courseCode)){
                courses.set(i,course);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(String courseCode){
        for (Course c : courses){
            if(c.getCourseCode().equals(courseCode)){
                courses.remove(c);
                return true;
            }
        }
        return false;
    }


    public ArrayList<Course> searchCourses(String keyword) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (c.getCourseName().toLowerCase().contains(keyword.toLowerCase()) || c.getCourseCode().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }





    public ArrayList<Course> getCourseByDepartment(String departmentName){
        ArrayList<Course> department = new ArrayList<>();

        for (Course c : courses){
            if(c.getDepartment().equals(departmentName)){
                department.add(c);
            }
        }
        return department;
    }

    public ArrayList<Course> getCoursesBySemester(int n){
        ArrayList<Course> available = new ArrayList<>();
        for (Course c : courses){
            if(c.getSemesterAvailable() == n){
                available.add(c);
            }
        }
        return available;
    }

    public ArrayList<Course> getCourseByNotElective(){
        ArrayList<Course> notElective = new ArrayList<>();
        for (Course c : courses){
            if(!c.isElectiveCourse()){
                notElective.add(c);
            }
        }
        return notElective;
    }



}
