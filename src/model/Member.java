package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member with personal info and their loan records.
 */
public class Member {

    private String name;
    private String surname;
    private String email;
    private String mobilePhone;
    private String address;
    private final List<Loan> loanList = new ArrayList<>();
    private double debt;

    public Member(String name, String surname, String email, String mobilePhone, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.address = address;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public double getDebt() {
        return debt;
    }

    //Setters
    public void setDebt(double debt) {
        this.debt = debt;
    }

    @Override
    public String toString() {
        return " Name: " + name +
                "\nSurname: " + surname +
                "\nEmail: " + email +
                "\nMobile Phone Number: " + mobilePhone +
                "\nAddress: " + address +
                "\nDebt: " + debt +
                "\n-----------------------------";
    }

}
