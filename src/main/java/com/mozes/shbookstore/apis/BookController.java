package com.mozes.shbookstore.apis;

import com.mozes.shbookstore.models.Book;
import com.mozes.shbookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books/add")
    public String addBook(@RequestBody List<Book> books){
        for (Book b:books
             ) {
            bookService.addBook(b);
        }
        return "book added";
    }

    @PostMapping("books/buy")
    public String buyBook(@RequestBody Book book){
        Boolean result = bookService.removeBook(book);
        String msg = "Book was " + (result ? "":"not " )+"successfully purchased!";
        return msg;
    }

    @GetMapping("books")
    @ResponseBody
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }


}
