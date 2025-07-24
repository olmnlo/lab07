package org.example.learningmanagementsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.learningmanagementsystem.Api.ApiResponse;
import org.example.learningmanagementsystem.Model.Instructor;
import org.example.learningmanagementsystem.Model.Student;
import org.example.learningmanagementsystem.Service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    private final InstructorService instructorService;




    @GetMapping("/get")
    public ResponseEntity<?> getInstructors() {
        ArrayList<Instructor> instructors = instructorService.getInstructors();
        if (instructors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no instructors"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(instructors);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInstructor(@Valid @RequestBody Instructor instructor, Errors error) {
        if (error.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        } else {
            instructorService.addInstructor(instructor);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("instructor added successfully"));
        }
    }

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<?> updateInstructor(@PathVariable String instructorId, @Valid @RequestBody Instructor instructor, Errors error) {
        if (error.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getFieldError().getDefaultMessage());
        }
        if (instructorService.updateInstructor(instructorId, instructor)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("instructor updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("instructor not found"));
        }
    }

    @DeleteMapping("/delete/{instructorId}")
    public ResponseEntity<?> deleteInstructor(@PathVariable String instructorId) {
        if (instructorService.deleteInstructor(instructorId)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("instructor deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("instructor not found"));
        }
    }

    @PutMapping("/load-bonus/{requesterId}")
    public ResponseEntity<?> hoursLoadBonus(@PathVariable String requesterId){
        if(instructorService.hoursLoadBonus(requesterId)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("all teachers have load hours more than 30 take bonus"));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("no teachers have weekly load hours more than 30"));
        }
    }

    @PutMapping("/assign-instructor/course/{courseCode}/instructor/{instructorId}/requester/{requesterId}")
    public ResponseEntity<?> assignInstructor(@PathVariable String courseCode, @PathVariable String requesterId, @PathVariable String instructorId){
        String assign = instructorService.assignInstructor(courseCode, requesterId, instructorId);
        if(assign.equals("requester is not chairman")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(assign));
        }else if (assign.equals("instructor not found") || assign.equals("course not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(assign));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(assign));
        }
    }


    @PutMapping("/request-leave/requester/{requesterId}/chairman/{chairmanId}")
    public ResponseEntity<?>requestLeave(@PathVariable String requesterId, @PathVariable String chairmanId){
        String msg = instructorService.requestLeave(requesterId, chairmanId);
        if(msg.equals("requesterId not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(msg));
        }else if (msg.equals("you are not chairman")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(msg));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(msg));
        }
    }

    @PutMapping("/update/student/{studentId}/chairman/{chairmanId}/status/{status}")
    public ResponseEntity<?> changeStudentStatus(@PathVariable String studentId, @PathVariable String chairmanId, @PathVariable String status){
        String msg = instructorService.changeStudentStatus(studentId, chairmanId, status);
        if(msg.equals("student not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(msg));
        }else if (msg.equals("you are not chairman or your id is wrong")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(msg));
        }
    }


}
