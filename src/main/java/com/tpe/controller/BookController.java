package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping("/{id}") //http://localhost:8081/books/{id}
    public ResponseEntity<Map<String,Book>> updateBook(@Valid @PathVariable Long id, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(id, book);
        Map<String,Book> response = new HashMap<>();
        response.put("Successfully updated book: ", updatedBook);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page") //http://localhost:8081/books/page?page=1&size=2&sort=year&direction=ASC/DESC
    public ResponseEntity<Page<Book>> getBooksByPage(@RequestParam("page") int pageNumber,
                                                     @RequestParam("size") int pageSize,
                                                     @RequestParam("sort") String sortProp,
                                                     @RequestParam("direction")Sort.Direction direction) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortProp));
        Page<Book> bookPage = bookService.fetchBooksByPage(pageable);

        return ResponseEntity.ok(bookPage);
    }



}
