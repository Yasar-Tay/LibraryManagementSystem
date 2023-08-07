package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Map<String,Book>> addBook(@RequestBody Book book){
        Book savedBook = bookService.saveBook(book);
        Map<String,Book> response = new HashMap<>();
        response.put("successfully saved book", savedBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }
}
