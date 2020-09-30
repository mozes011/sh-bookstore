package com.mozes.shbookstore.services;

import com.mozes.shbookstore.models.Book;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookService {
    private ConcurrentHashMap<Book,Integer> inventory;

    public BookService() {
        inventory = new ConcurrentHashMap<>();
    }

    public BookService(ConcurrentHashMap <Book, Integer> inventory) {
        this.inventory = inventory;
    }

    public void addBook(Book book) {
        inventory.merge(book,1,(existValue,newValue)->existValue+1);

    }

    public Integer getBookQuantity(Book book){
        return inventory.getOrDefault(book,0);
    }
    public Boolean removeBook(Book book) {
        AtomicReference<Boolean> result = new AtomicReference<>(true);
        inventory.merge(book,0,(existValue,newValue)-> {
            if(existValue - 1 >= 0){

               return existValue-1;
            }
           else{
               result.set(false);
               return 0;
           }

        });

        return result.get();
    }

    public List<Book> getAllBooks() {
        List<Book> availableBooks = new LinkedList<>();
        for (Map.Entry<Book,Integer> entry: inventory.entrySet()) {
            if(entry.getValue() > 0){
                availableBooks.add(entry.getKey());
            }
        }

        return availableBooks;
    }
}
