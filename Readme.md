# Library Management System

A simple console-based Library Management System written in Java that supports managing books, members, and loan operations such as check-out and return.

---

## Features

- Add, list, and search books by title or ISBN.
- Register and list library members.
- Search members by email.
- Check out books to members if available.
- Return books and calculate late fees.
- Validates ISBN-10 and ISBN-13 formats.
- Tracks loan due dates and return dates.

---

## Technologies

- Java SE 8+
- Uses `java.time` API for date handling.
- Console-based user interface with menu-driven commands.

---

## Project Structure

- `model` package: Contains data models for `Book`, `Member`, `Loan`, and enum `Status`.
- `service` package: Contains services managing business logic:
    - `BookService` for book operations.
    - `MemberService` for member operations.
    - `LoanService` for loan/check-out/return operations.
    - `Library` interface implemented by `BookService`.
- `LibraryManagementSystem`: Main entry point and CLI interface.
- `ISBNValidator`: Utility class for validating ISBN formats.

---

## How to Run

1. Compile all source files under `model` and `service` packages along with the main class.
2. Run the `LibraryManagementSystem` class.
3. Interact with the program using the console menu.

---

## Usage Example

- Add a new book by selecting option 1.
- List all books with option 2.
- Search books by title or ISBN with options 3 and 4.
- Add members and list them with options 5 and 6.
- Search members by email using option 7.
- Check out and return books with options 8 and 9.
- Exit the program with option 10.

---

## Late Fee Policy

- Late fee is calculated as 5 currency units per day after the due date.
- Loan period is 1 month from the loan date.

---

## Notes

- Email uniqueness is enforced for members.
- ISBN uniqueness is enforced for books.
- Books default to `AVAILABLE` status when added.
- Loan records track loan date, due date (1 month), and return date.

---

## License

This project is licensed under the MIT License.

---

## Author

Created by [Hacer Nur Yavaş](https://github.com/haceryavas)



