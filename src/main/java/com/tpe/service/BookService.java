package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

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
}
