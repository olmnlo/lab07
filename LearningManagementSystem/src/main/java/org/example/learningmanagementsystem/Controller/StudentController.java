package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Api.ApiResponse;
import org.example.learningmanagementsystem.Model.Student;
import org.example.learningmanagementsystem.Service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping("/get")
    public ResponseEntity<?> getStudents(){
        ArrayList<Student> students = studentService.getStudents();
        if(students.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no students"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(students);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@Valid@RequestBody Student student, Errors error){
        if(error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        }else {
            studentService.addStudent(student);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("student add successfully"));
        }
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable String studentId, @Valid@RequestBody Student student, Errors error){
        if(error.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        }
        if(studentService.updateStudent(studentId, student)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("student updated successfully"));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("student not found"));
        }
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId){
        if(studentService.deleteStudent(studentId)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("student deleted successfully"));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("student not found"));
        }
    }


    @GetMapping("/get/credit-hours/{studentId}")
    public ResponseEntity<?> maximumCourseHoursInSemester(@PathVariable String studentId){
        int creditHours = studentService.maximumCourseHoursInSemester(studentId);

        if (creditHours == -1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("student not found"));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("your maximum credit hours is: "+ creditHours));
        }
    }

    @GetMapping("/get/honor/{studentId}")
    public ResponseEntity<?> haveHonorClass(@PathVariable String studentId){
        String honor = studentService.haveHonorClass(studentId);
        if(honor.equals("student not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(honor));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(honor));
        }
    }

    @PutMapping("assign/{courseCode}/student/{studentId}")
    public ResponseEntity<?> assignCourse(@PathVariable String studentId,@PathVariable String courseCode){
        String assigned = studentService.assignCourse(studentId,courseCode);

        if (assigned.equals("course not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(assigned));
        } else if (assigned.equals("student not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(assigned));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(assigned));
        }
    }

    @GetMapping("get/student/{studentId}/housing")
    public ResponseEntity<?> isHaveHousing(@PathVariable String studentId){
        String haveHouse = studentService.isHaveHousing(studentId);
        if(haveHouse.equals("student not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(haveHouse));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(haveHouse));
        }
    }

}
