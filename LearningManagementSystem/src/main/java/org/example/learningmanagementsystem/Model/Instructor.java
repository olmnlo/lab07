package org.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Instructor {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Instructor ID is required")
    @Pattern(regexp = "^[A-Z]{2,4}\\d{4,6}$", message = "Instructor ID must follow a valid format like IN202300")
    private String instructorId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(05|5)[0-9]{8}$", message = "Phone number must be a valid Saudi number (e.g., 0551234567)")
    private String phoneNumber;

    @NotBlank(message = "Major is required")
    private String major;

    private ArrayList<Course> coursesName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 25, message = "Age must be at least 25")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    @Min(value = 0, message = "Annual leave must be zero or positive")
    @Max(value = 60, message = "Annual leave cannot exceed 60 days")
    private int annualLeave;

    private boolean isLeave;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(teacher|chairman)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Role must be either 'teacher' or 'chairman'")
    private String role;

    @NotNull(message = "weekly load hours is required")
    @Positive(message = "weekly load hours must be positive")
    @Max(value = 40, message = "max load hours is 40")
    private int weeklyLoadHours;

    @NotNull(message = "balance is required")
    @Positive(message = "balance must be positive")
    private double balance;
}
