package service;

import model.Book;

/**
 * Interface defining the basic book-related library operations.
 */
public interface Library {

    void create(String title, String author, String isbn);

    void list();

    void findBookByTitle(String title);

    boolean addIsbn(String isbn);

    Book findBookByIsbn(String isbn, boolean print);
}
