package service;

import model.Book;
import model.Loan;
import model.Member;
import model.enums.Status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Handles book lending (check out) and return operations.
 */
public class LoanService {

    /**
     * Daily late fee amount charged per overdue day.
     */
    private static final int LATE_FEE_PER_DAY = 5;

    /**
     * Checks out a book to a member if the book is available.
     * Updates book status and adds a loan record to the member.
     *
     * @param foundMember The member borrowing the book.
     * @param foundBook   The book to be borrowed.
     */
    public void checkOut(Member foundMember, Book foundBook) {
        if (foundBook == null || foundMember == null) {
            System.out.println("Invalid member or book.");
            return;
        }

        if (foundBook.getStatus() == Status.AVAILABLE) {
            foundBook.setStatus(Status.UNAVAILABLE);

            // Create a new Loan record
            Loan loan = new Loan(foundMember, foundBook);

            // Add loan to member's loan list
            foundMember.getLoanList().add(loan);

            System.out.println("Book successfully checked out to user: " + foundMember.getEmail());
        } else {
            System.out.println("Book not found or already checked out.");
        }
    }

    /**
     * Processes the return of a book by a member.
     * Calculates any late fees, updates the book's status to AVAILABLE, and sets the loan return date to now.
     *
     * @param foundBook   The book being returned.
     * @param foundMember The member returning the book.
     */
    public void returnBook(Book foundBook, Member foundMember) {
        // Defensive null checks
        if (foundBook == null || foundMember == null) {
            System.out.println("Invalid book or member information.");
            return;
        }

        // Check if the book is currently checked out (status UNAVAILABLE)
        if (foundBook.getStatus() != Status.UNAVAILABLE) {
            System.out.println("Book not found or is not checked out.");
            return;
        }

        // Find the active loan (no return date) for this book and member
        Loan activeLoan = findActiveLoan(foundBook, foundMember);
        if (activeLoan == null) {
            System.out.println("No active loan found for this book.");
            return;
        }

        // Calculate debt based on late days and update member debt
        long debt = calculateDebt(activeLoan);
        if (debt > 0) {
            foundMember.setDebt(debt);
            System.out.println("You are late by " + (debt / LATE_FEE_PER_DAY) + " days. Your debt is: " + debt);
        }

        // Update book status to AVAILABLE and record the return date as today
        foundBook.setStatus(Status.AVAILABLE);
        activeLoan.setReturnDate(LocalDate.now());

        System.out.println("Book returned successfully!");
    }

    /**
     * Finds an active (not yet returned) loan for the specified book and member.
     *
     * @param foundBook   The book to find the loan for.
     * @param foundMember The member who borrowed the book.
     * @return The active Loan object if found, otherwise null.
     */
    private Loan findActiveLoan(Book foundBook, Member foundMember) {
        for (Loan loan : foundMember.getLoanList()) {
            if (loan.getBook().equals(foundBook) && loan.getReturnDate() == null) {
                return loan;
            }
        }
        return null;
    }

    /**
     * Calculates the debt (late fee) for a given loan.
     * Debt is computed as the number of days past the due date multiplied by the daily late fee.
     *
     * @param loan The loan to calculate debt for.
     * @return Total debt amount; returns 0 if the book is returned on or before the due date.
     */
    private long calculateDebt(Loan loan) {
        long lateDays = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        if (lateDays > 0) {
            return lateDays * LATE_FEE_PER_DAY;
        }
        return 0;
    }
}