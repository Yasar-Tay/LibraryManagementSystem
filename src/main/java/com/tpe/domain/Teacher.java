package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Setter(AccessLevel.NONE)
    private LocalDateTime registerDate;

    @ManyToMany
    @JoinTable(name = "teacher_book",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookList = new ArrayList<>();

    @PrePersist
    public void setRegisterDate() {
        registerDate = LocalDateTime.now();
    }
}
