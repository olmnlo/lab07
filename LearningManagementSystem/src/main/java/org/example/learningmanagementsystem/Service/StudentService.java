package org.example.learningmanagementsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final CourseService courseService;



    private ArrayList<Student> students = new ArrayList<>();

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        student.setCoursesPlan(new ArrayList<Course>());
        students.add(student);
    }
    
    public boolean updateStudent(String studentId, Student student){
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getStudentId().equals(studentId)){
                students.set(i,student);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(String studentId){
        for (Student s : students){
            if(s.getStudentId().equals(studentId)){
                students.remove(s);
                return true;
            }
        }
        return false;
    }

    public int maximumCourseHoursInSemester(String studentId){
        for (Student s: students){
            if (s.getStudentId().equals(studentId)){
                if(s.getGpa()<3){
                    return 12;
                }else {
                    return 20;
                }
            }
        }
        return -1;
    }

    public String haveHonorClass(String studentId){
        for (Student s: students){
            if (s.getStudentId().equals(studentId)){
                if(s.getGpa()>=4.5){
                    return "first honor student";
                }else if(s.getGpa() >= 4.0){
                    return "second honor student";
                }else {
                    return "student";
                }
            }
        }
        return "student not found";
    }

    public String assignCourse(String studentId, String courseCode){
        for (Course c : courseService.getCourses()){
            if(c.getCourseCode().equals(courseCode)){
                for (Student s : students){
                    if(s.getStudentId().equals(studentId)){
                        int studentTotalCredit = 0;
                        int maximumCredit = maximumCourseHoursInSemester(studentId);
                        for (Course c_s: s.getCoursesPlan()){
                            studentTotalCredit+=c_s.getCourseCreditHour();
                        }
                        if(studentTotalCredit + c.getCourseCreditHour() <= maximumCredit){
                            if(s.getCoursesPlan().contains(c)){
                                return "course is added you cannot duplicate courses";
                            }
                            s.getCoursesPlan().add(c);
                            return "course added successfully";
                        }else {
                            return "student cannot add this course";
                        }
                    }
                }
                return "student not found";
            }
        }
        return "course not found";
    }

    public String isHaveHousing(String studentId){
        for (Student s : students){
            if(s.getStudentId().equals(studentId)){
                if(s.isHaveHousing()){
                    return "true";
                }else {
                    return "false";
                }
            }
        }
        return "student not found";
    }

//    public ArrayList<Student> listOfGraduatedStudent(String requesterId){
//        ArrayList<Student> graduated = new ArrayList<>();
//        boolean flag = false;
//        for(Instructor i : instructorService.getInstructors()){
//            if(i.getInstructorId().equals(requesterId) && i.getRole().equals("supervisor")){
//                flag = true;
//                break;
//            }
//        }
//        if(flag){
//            for (Student s : students) {
//                if (s.getStudentStatus().equals("graduated")) {
//                    graduated.add(s);
//                }
//            }
//        }
//        return graduated;
//    }


}
