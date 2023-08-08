package com.tpe.dto;

import com.tpe.domain.Teacher;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be white space")
    @Size(min = 2, max = 25, message = " Name(${validatedValue}) must be between {min} and {max} characters")
    @Column(name = "teacher_name")
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Email(message = "Please provide a proper email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Please provide a proper phone number..")
    private String phoneNumber;

    public TeacherDTO(Teacher teacher){
        this.name = teacher.getName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
        this.phoneNumber = teacher.getPhoneNumber();
    }

}
