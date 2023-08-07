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
@RequestMapping("/books") //http://localhost:8081/books
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

    @GetMapping("/{id}") //http://localhost:8081/books/{id}
    public ResponseEntity<Book> getBookByIdWithPathVariable(@PathVariable("id") Long id){
        Book foundBook = bookService.getBookById(id);
        return ResponseEntity.ok(foundBook);
    }

    @GetMapping("/query") //http://localhost:8081/books/query?id={id}
    public ResponseEntity<Book> getBookByIdWithRequestParam(@RequestParam Long id){
        Book foundBook = bookService.getBookById(id);
        return ResponseEntity.ok(foundBook);
    }

    @GetMapping("/author") //http://localhost:8081/books/author?author={author}
    public ResponseEntity<List<Book>> getBookByAuthorWithRequestParam(@RequestParam String author){
        List<Book> foundBooks = bookService.findBookByAuthor(author);
        return ResponseEntity.ok(foundBooks);
    }

    @DeleteMapping("/{id}") //http://localhost:8081/books/{id}
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        String message = String.format("Book with id: %s is deleted successfully", id);
        return ResponseEntity.ok(message);
    }
}
