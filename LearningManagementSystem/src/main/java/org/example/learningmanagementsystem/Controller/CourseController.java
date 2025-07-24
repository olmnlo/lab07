package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Api.ApiResponse;
import org.example.learningmanagementsystem.Model.Course;
import org.example.learningmanagementsystem.Service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;



    @GetMapping("/get")
    public ResponseEntity<?> getCourses() {
        if (courseService.getCourses().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no courses found"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourses());
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@Valid@RequestBody Course course, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        }else {
            courseService.addCourse(course);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("courses added successfully"));
        }
    }

    @PutMapping("/update/{courseCode}")
    public ResponseEntity<?> updateCourse(@PathVariable String courseCode, @Valid@RequestBody Course course, Errors error){
        if (error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        }else {
            if (courseService.updateCourse(courseCode, course)) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("course updated successfully"));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("course not found"));
            }
        }
    }


    @DeleteMapping("/delete/{courseCode}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseCode){
        if(courseService.deleteCourse(courseCode)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("course deleted successfully"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("course not found"));
        }
    }


//    @PutMapping("/assign-instructor/course/{courseCode}/instructor/{instructorId}/requester/{requesterId}")
//    public ResponseEntity<?> assignInstructor(@PathVariable String courseCode, @PathVariable String requesterId, @PathVariable String instructorId){
//        String assign = courseService.assignInstructor(courseCode, requesterId, instructorId);
//        if(assign.equals("requester is not chairman")){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(assign));
//        }else if (assign.equals("instructor not found") || assign.equals("course not found")){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(assign));
//        }else {
//            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(assign));
//        }
//    }


    @GetMapping("/get/department/{departmentName}")
    public ResponseEntity<?> getCourseByDepartment(@PathVariable String departmentName){
        ArrayList<Course> department = courseService.getCourseByDepartment(departmentName);
        if (department.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no courses in this department"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(department);
        }
    }

    @GetMapping("/get/available/{nSemester}")
    public ResponseEntity<?> getCourseByNSemester(@PathVariable int nSemester){
        ArrayList<Course> semester = courseService.getCoursesBySemester(nSemester);
        if (semester.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no courses in this semester"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(semester);
        }
    }


    @GetMapping("/get/not-elective")
    public ResponseEntity<?> getCourseByNotElective(){
        ArrayList<Course> elective = courseService.getCourseByNotElective();
        if (elective.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no courses in this is elective"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(elective);
        }
    }


}
