import model.Book;
import model.Member;
import service.BookService;
import service.Library;
import service.LoanService;
import service.MemberService;

import java.util.Scanner;

/**
 * Main entry point for the Library Management System.
 * Handles user interaction through a text-based menu interface.
 */

public class LibraryManagementSystem {
    private static final Library myLibrary = new BookService();
    private static final MemberService memberService = new MemberService();
    private static final LoanService loanService = new LoanService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!");

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline left-over

            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> displayBooks();
                case 3 -> searchBookByTitle(scanner);
                case 4 -> searchBookByIsbn(scanner);
                case 5 -> addMember(scanner);
                case 6 -> displayMembers();
                case 7 -> searchMemberByEmail(scanner);
                case 8 -> checkOutBook(scanner);
                case 9 -> returnBook(scanner);
                case 10 -> {
                    System.out.println("Thank you for using the Library Management System!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid selection. Please enter a number between 1 and 10.");
            }
        }
    }

    /**
     * Prints the main menu options for user interaction.
     */
    private static void printMenu() {
        System.out.print("\nPlease select an option: " +
                "\n1. Add a new book" +
                "\n2. Display all books" +
                "\n3. Search for a book by title" +
                "\n4. Search for a book by ISBN" +
                "\n5. Add a new member" +
                "\n6. Display all members" +
                "\n7. Search for a member by email" +
                "\n8. Check out a book" +
                "\n9. Return a book" +
                "\n10. Exit" +
                "\nEnter your choice: ");
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().toUpperCase();

        // Validate ISBN format
        if (!ISBNValidator.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format!");
        }
        // Check for duplicate ISBN in the system
        else if (!myLibrary.addIsbn(isbn)) {
            System.out.println("This ISBN is already registered!");
        }
        // Add book if validations pass
        else {
            myLibrary.create(title, author, isbn);
        }
    }

    private static void displayBooks() {
        System.out.println("\n ******** All Books ********");
        System.out.println("-----------------------------");
        myLibrary.list();
    }

    private static void searchBookByTitle(Scanner scanner) {
        System.out.print("Enter book title to search: ");
        String title = scanner.nextLine();

        System.out.println("\nFound Books: ");
        System.out.println("-----------------------------");
        myLibrary.findBookByTitle(title);
    }

    private static void searchBookByIsbn(Scanner scanner) {
        System.out.print("Enter the ISBN of the book to search: ");
        String isbn = scanner.nextLine().toUpperCase();

        if (!ISBNValidator.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format!");
        } else {
            System.out.println("\nFound Books: ");
            System.out.println("-----------------------------");

            myLibrary.findBookByIsbn(isbn, true);
        }
    }

    private static void addMember(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter mobile phone number: ");
        String mpNo = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        memberService.create(name, surname, email, mpNo, address);

    }

    private static void displayMembers() {
        System.out.println("\n******** All Members ********");
        System.out.println("-----------------------------");
        memberService.list();
    }

    private static void searchMemberByEmail(Scanner scanner) {
        System.out.print("Enter email to search: ");
        String email = scanner.nextLine();

        System.out.println("\nFound Member: ");
        System.out.println("-----------------------------");
        memberService.findMemberByEmail(email, true);
    }

    private static void checkOutBook(Scanner scanner) {
        System.out.print("Enter the email of member: ");
        String email = scanner.nextLine();

        System.out.print("Enter the ISBN of the book you want to check out: ");
        String isbn = scanner.nextLine().toUpperCase();

        if (!ISBNValidator.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format!");
        } else {
            Book foundBook = myLibrary.findBookByIsbn(isbn, false);
            Member foundMember = memberService.findMemberByEmail(email, false);
            loanService.checkOut(foundMember, foundBook);
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter the email of member: ");
        String email = scanner.nextLine();

        System.out.print("Enter the ISBN of the book you want to return: ");
        String isbn = scanner.nextLine().toUpperCase();

        if (!ISBNValidator.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format!");
        } else {
            Book foundBook = myLibrary.findBookByIsbn(isbn, false);
            Member foundMember = memberService.findMemberByEmail(email, false);
            loanService.returnBook(foundBook, foundMember);
        }
    }

}

/**
 * A utility class to validate ISBN-10 and ISBN-13 formats.
 */
class ISBNValidator {
    /**
     * Validates the ISBN string, removing hyphens and spaces first.
     * Supports ISBN-10 and ISBN-13 formats.
     *
     * @param isbn Raw ISBN string.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidISBN(String isbn) {
        isbn = isbn.replace("-", "").replace(" ", "");
        return isbn.length() == 10 ? isValidISBN10(isbn) : isbn.length() == 13 && isValidISBN13(isbn);
    }

    // Validates ISBN-10 format and checksum
    private static boolean isValidISBN10(String isbn) {
        if (!isbn.matches("\\d{9}[\\dXx]")) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }

        char lastChar = isbn.charAt(9);
        int lastValue = (lastChar == 'X' || lastChar == 'x') ? 10 : (lastChar - '0');
        sum += lastValue;

        return sum % 11 == 0;
    }

    // Validates ISBN-13 format and checksum
    private static boolean isValidISBN13(String isbn) {
        if (!isbn.matches("\\d{13}")) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        int lastDigit = isbn.charAt(12) - '0';

        return checkDigit == lastDigit;
    }
}