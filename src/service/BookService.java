package service;

import model.Book;

import java.util.*;

/**
 * Manages all book-related operations within the library.
 * Supports adding, searching, displaying, checking out, and returning books.
 */

public class BookService implements Library {
    // A list to store all books in the library
    private final List<Book> books = new ArrayList<>();

    // A set to store normalized ISBNs for fast uniqueness check
    private final Set<String> isbnSet = new HashSet<>();

    /**
     * Adds a new book to the library collection.
     *
     * @param title  Title of the book
     * @param author Author of the book
     * @param isbn   ISBN of the book (should be validated before)
     */
    public void create(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        books.add(book);
        System.out.println("Book added successfully!");
    }

    /**
     * Attempts to add an ISBN to the internal set.
     *
     * @param isbn The ISBN to add (in any format)
     * @return true if ISBN was not already present and is now added; false otherwise
     */
    public boolean addIsbn(String isbn) {
        return isbnSet.add(normalizeIsbn(isbn));
    }

    /**
     * Displays all books in the library.
     * Prints a message if no books are available.
     */
    public void list() {
        if (books.isEmpty()) {
            System.out.println("No books are currently available in the library.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Searches for books with titles that contain the specified keyword.
     *
     * @param title Partial or full title to search for (case-insensitive)
     */
    public void findBookByTitle(String title) {
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found containing the title: " + title);
        }
    }

    /**
     * Finds a book by exact ISBN match.
     * Optionally prints the book details.
     *
     * @param isbn  ISBN to search for.
     * @param print If true, prints the found book.
     * @return The found book or null if none matched.
     */
    @Override
    public Book findBookByIsbn(String isbn, boolean print) {
        isbn = normalizeIsbn(isbn);
        Book foundBook = null;

        for (Book book : books) {
            if (normalizeIsbn(book.getIsbn()).equals(isbn)) {
                foundBook = book;
                if (print) {
                    System.out.println(book);
                }
                break;
            }
        }
        if (foundBook == null) {
            System.out.println("No book found with the ISBN: " + isbn);
        }
        return foundBook;
    }

    /**
     * Normalizes ISBN by removing hyphens and spaces, and converting to uppercase.
     *
     * @param isbn Raw ISBN string.
     * @return Normalized ISBN string.
     */
    public String normalizeIsbn(String isbn) {
        if (isbn == null) return "";
        return isbn.replace("-", "").replace(" ", "").toUpperCase();
    }

}