package model;

import model.enums.Status;

/**
 * Represents a book in the library.
 */
public class Book {

    private String title;
    private String author;
    private String isbn;
    private Status status;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = Status.AVAILABLE;  // New books are available by default
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Status getStatus() {
        return status;
    }

    // Setters
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nAuthor: " + author +
                "\nISBN: " + isbn +
                "\nStatus: " + status +
                "\n-----------------------------";
    }
}
