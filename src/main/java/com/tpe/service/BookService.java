package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.dto.BookDTO;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TeacherService teacherService;

    @Transactional
    public ResponseEntity<Map<String, String>> addBookForTeacher(Long teacherId, Long bookId) {
        //step 1: find teacher by id
        Teacher teacher = teacherService.findTeacherById(teacherId);

        //Step 2: find Book by id
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()){
            Map<String,String> response = new HashMap<>();
            response.put("message", "Book with id: " + bookId + " doesn't exist");
            response.put("success", "false");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404 NOT FOUND
        }
        Book book = bookOptional.get();

        //Step 3: check if the book already exist for the found teacher.
        boolean teacherAlreadyHasBook = teacher.getBookList().stream()
                .anyMatch(b-> b.getId().equals(book.getId()));
        if (teacherAlreadyHasBook){
            Map<String,String> response = new HashMap<>();
            response.put("message", String.format("Teacher with id: %s already has the book with id: %s", teacherId, bookId));
            response.put("success", "false");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400 BAD_REQUEST
        }

        //Step 4: we can add the book to the teacher.
        teacher.getBookList().add(book);

        Map<String,String> response = new HashMap<>();
        response.put("message", String.format("Book with id: %s has been added to Teacher with id: %s successfully", bookId, teacherId));
        response.put("success", "true");

        return new ResponseEntity<>(response, HttpStatus.CREATED); //201


    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        List<Book> bookList = bookRepository.findAll();

        if (bookList.isEmpty())
            throw new ResourceNotFoundException("No books yet!!");

        return bookList;
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No book with id: " + id));
    }

    public void deleteBook(Long id) {
        Book bookToBeDeleted = getBookById(id);
        bookRepository.delete(bookToBeDeleted);
    }

    public List<Book> findBookByAuthor(String author){
        List<Book> bookList = bookRepository.findByAuthor(author);

        if (bookList.isEmpty())
            throw new ResourceNotFoundException("No books of author : " + author);

        return bookList;
    }

    public Book updateBook(Long id, Book book) {
        Book bookToBeUpdated = getBookById(id);
        //Update Process
        bookToBeUpdated.setTitle(book.getTitle());
        bookToBeUpdated.setAuthor(book.getAuthor());
        bookToBeUpdated.setPublishDate(book.getPublishDate());

        return bookRepository.save(bookToBeUpdated);

    }

    public Page<Book> fetchBooksByPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book updateBookByDto(Long bookId, BookDTO bookDTO) {
        Book bookToBeUpdated = getBookById(bookId);

        //Update Process
        bookToBeUpdated.setTitle(bookDTO.getTitle());
        bookToBeUpdated.setAuthor(bookDTO.getAuthor());
        bookToBeUpdated.setPublishDate(bookDTO.getPublishDate());

        return bookRepository.save(bookToBeUpdated);
    }
}
