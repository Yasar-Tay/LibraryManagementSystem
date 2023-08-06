package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "You have to enter a title..")
    @Column(name = "book_name")
    private String title;

    @NotBlank(message = "Please enter author name..")
    private String author;

    @Digits(message = "Please just enter the year", integer = 4, fraction = 0)
    private String publishDate;

    @ManyToMany(mappedBy = "bookList", cascade = CascadeType.REMOVE)
    private List<Teacher> teacherList;

}
