package org.example.learningmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Model.Instructor;
import org.example.learningmanagementsystem.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final StudentService studentService;
    private final CourseService courseService;
    private ArrayList<Instructor> instructors = new ArrayList<>();

    public ArrayList<Instructor> getInstructors(){
        return instructors;
    }

    public void addInstructor(Instructor instructor) {
        instructor.setCoursesName(new ArrayList<Course>());
        instructors.add(instructor);
    }

    public boolean updateInstructor(String instructorId, Instructor updatedInstructor) {
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).getInstructorId().equals(instructorId)) {
                instructors.set(i, updatedInstructor);
                return true;
            }
        }
        return false;
    }

    public boolean deleteInstructor(String instructorId) {
        for (Instructor instructor : instructors) {
            if (instructor.getInstructorId().equals(instructorId)) {
                instructors.remove(instructor);
                return true;
            }
        }
        return false;
    }


    public boolean hoursLoadBonus(String requesterId){
        boolean flag = false;
        for (Instructor i: instructors){
            if (i.getInstructorId().equals(requesterId)){
                if (i.getRole().equals("chairman")){
                    for (Instructor ii : instructors){
                        if(ii.getWeeklyLoadHours() > 30){
                            ii.setBalance(ii.getBalance()+(ii.getBalance()*0.1));
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

    public String assignInstructor(String courseCode, String requesterId, String instructorId) {
        boolean flag = false;
        for(Instructor i : instructors){
            if (i.getInstructorId().equals(requesterId)){
                if(i.getRole().equals("chairman")){
                    flag = true;
                }else {
                    return "requester is not chairman";
                }
            }
        }

        if(flag) {
            for (Course course : courseService.getCourses()) {
                if (course.getCourseCode().equals(courseCode)) {
                    for (Instructor i : instructors) {
                        if (i.getInstructorId().equals(instructorId)) {
                            i.getCoursesName().add(course);
                            return "course assigned with instructor";
                        }
                    }
                    return "instructor not found";
                }
            }
        }
        return "course not found";
    }

    public String requestLeave(String requesterId, String chairmanId){
        for (Instructor i : instructors){
            if(i.getInstructorId().equals(chairmanId)) {
                if (i.getRole().equals("chairman")){
                    for (Instructor ii : instructors){
                        if(ii.getInstructorId().equals(requesterId)){
                            if(ii.getAnnualLeave()>0) {
                                ii.setLeave(true);
                                ii.setAnnualLeave(ii.getAnnualLeave() - 1);
                                return "have a good vacation";
                            }else {
                                return "you cannot take leave";
                            }
                        }
                    }
                    return "requesterId not found";
                }
            }
        }
        return "you are not chairman";
    }


    //int changeStudentStatus(studentId, superVisorId): PUT
    public String changeStudentStatus(String studentId, String chairmanId, String status){
        for (Instructor i : instructors){
            if(i.getInstructorId().equals(chairmanId) && i.getRole().equals("chairman")){
                for( Student s: studentService.getStudents()){
                    if(s.getStudentId().equals(studentId)){
                        s.setStudentStatus(status);
                        return "student status updated successfully";
                    }
                }
                return "student not found";
            }
        }
        return "you are not chairman or your id is wrong";
    }


}
