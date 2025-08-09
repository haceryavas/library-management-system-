package model;

import java.time.LocalDate;

/**
 * Represents a book loan transaction between a member and the library.
 * Holds loan date, due date, and optional return date.
 */
public class Loan {
    private Member member;
    private Book book;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    /**
     * Creates a Loan instance with loanDate set to now and dueDate one month later.
     *
     * @param member Borrowing member.
     * @param book   Borrowed book.
     */
    public Loan(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusMonths(1);
    }

    //Getters
    public Book getBook() {
        return book;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    //Setters
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
