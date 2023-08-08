package com.tpe.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @NotBlank(message = "You have to enter a title..")
    @Column(name = "book_name")
    private String title;

    @NotBlank(message = "Please enter author name..")
    private String author;

    @Digits(message = "Please just enter the year", integer = 4, fraction = 0)
    private String publishDate;
}
