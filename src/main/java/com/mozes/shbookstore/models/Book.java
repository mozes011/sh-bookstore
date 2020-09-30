package com.mozes.shbookstore.models;

import java.util.Objects;

public class Book {
    public String name;
    public String author;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
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
}
