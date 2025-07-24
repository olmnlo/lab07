package org.example.learningmanagementsystem.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 100, message = "Course name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "course name must be chars")
    private String courseName;

    @NotBlank(message = "Course code is required")
    @Pattern(regexp = "^[A-Z]{2,4}\\d{3}$", message = "Course code must follow pattern like CSC101")
    private String courseCode;

    @NotBlank(message = "department is required")
    @Size(min = 3, max = 100, message = "department must be between 3 and 100 characters")
    private String department;

    @Min(value = 1, message = "Credit hour must be at least 1")
    @Max(value = 6, message = "Credit hour must be at most 6")
    private int courseCreditHour;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 10, message = "Semester must be at most 10")
    private int semesterAvailable;


    private boolean isElectiveCourse;



}
