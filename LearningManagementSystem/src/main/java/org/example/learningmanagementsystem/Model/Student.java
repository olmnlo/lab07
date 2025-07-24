package org.example.learningmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Student {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "name must be chars only")
    private String name;

    @NotBlank(message = "Student ID is required")
    @Pattern(regexp = "^[A-Z]{2,4}\\d{4,6}$", message = "Student ID must follow a valid format like CS202300")
    private String studentId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(05|5)[0-9]{8}$", message = "Phone number must be a valid Saudi number (e.g., 0551234567)")
    private String phoneNumber;

    @DecimalMin(value = "0.0", inclusive = true, message = "GPA must be 0.0 or greater")
    @DecimalMax(value = "5.0", inclusive = true, message = "GPA must be 5.0 or less")
    private double gpa;

    @NotBlank(message = "Major is required")
    @Pattern(regexp = "[A-Za-z]+", message = "major must be chars only")
    private String major;

    private ArrayList<Course> coursesPlan;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 27, message = "Age must be at most 27")
    private int age;

    @Pattern(regexp = "(active)", message = "studentStatus must be active")
    @NotBlank(message = "student status is required")
    private String studentStatus;

    @NotNull(message = "Join date is required")
    @PastOrPresent(message = "Join date must be in the past or today")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @NotNull(message = "is have housing is required")
    private boolean isHaveHousing;
}
