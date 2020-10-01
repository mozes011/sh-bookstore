package com.mozes.shbookstore.services;

import com.mozes.shbookstore.models.Book;
import com.mozes.shbookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookService() {

    }

    public BookService(BookRepository bookRepository) {
    }

    public synchronized void addBook(Book book) {
        List<Book> requestedBooks = bookRepository.findByName(book.getName());
        if(requestedBooks.isEmpty()){
            bookRepository.save(book);
        }
        else{
           Book requestedBook = requestedBooks.get(0);
           requestedBook.setQuantity(requestedBook.getQuantity()+book.getQuantity());
            bookRepository.save(requestedBook);
        }
    }

    public  Integer getBookQuantity(Book book){
        List<Book> requestedBooks = new LinkedList<>();
        synchronized (this){
            requestedBooks = bookRepository.findByName(book.getName());
        }

        if(requestedBooks.isEmpty()) {
            return 0;
        }
        else{
            Book requestedBook = requestedBooks.get(0);
            Integer quan = requestedBook.getQuantity();
            return quan;
        }
    }
    public synchronized Book purchaseBook(Book book) {
        List<Book> requestedBooks = bookRepository.findByName(book.getName());
        if (!requestedBooks.isEmpty()) {
            Book requestedBook = requestedBooks.get(0);
            Integer newQuantity = requestedBook.getQuantity() - book.getQuantity();
            if (newQuantity >= 0) {
                requestedBook.setQuantity(newQuantity);
                bookRepository.save(requestedBook);
                return requestedBook;
            }
        }
        return null;
    }

    public  List<Book> getAllBooks() {
        List<Book> availableBooks = new LinkedList<>();
        synchronized (this) {
            availableBooks = bookRepository.findByQuantityGreaterThan(0);
        }
        return availableBooks;
    }
}
