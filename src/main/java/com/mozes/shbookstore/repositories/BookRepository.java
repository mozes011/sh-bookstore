package com.mozes.shbookstore.repositories;

import com.mozes.shbookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthor(String author);
    List<Book> findByName(String name);
    List<Book> findByQuantityGreaterThan(Integer quantity);
}
