package com.mozes.shbookstore.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
@Entity
public class Book implements Serializable {
    @Id
    public String name;
    public String author;
    public Integer quantity;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.quantity=1;
    }

    public Book(String name, String author, Integer quantity) {
        this.name = name;
        this.author = author;
        this.quantity = quantity==null? 1:quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return name.equals(book.name) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
