package service;

import model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages library members: adding, listing, and searching by email.
 */
public class MemberService {

    // Holds all members registered in the system
    private final List<Member> members = new ArrayList<>();

    /**
     * Creates and adds a new member if email is unique.
     *
     * @param name    Member's first name.
     * @param surname Member's surname.
     * @param email   Member's email (must be unique).
     * @param mpNo    Mobile phone number.
     * @param address Member's address.
     */
    public void create(String name, String surname, String email, String mpNo, String address) {

        // Check if email already exists (case-insensitive)
        for (Member member : members){
            if (member.getEmail().equals(email)) {
                System.out.println("Email already registered!");
                return;
            }
        }

        Member member = new Member(name, surname, email, mpNo, address);
        members.add(member);
        System.out.println("Member successfully added!");
    }

    /**
     * Lists all registered members.
     * Prints message if no members are found.
     */
    public void list() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (Member member : members) {
            System.out.println(member);
        }
    }

    /**
     * Finds a member by email.
     * Optionally prints the member details.
     *
     * @param email Email to search for.
     * @param print If true, prints the found member.
     * @return Found Member or null if none found.
     */
    public Member findMemberByEmail(String email, boolean print) {
        Member foundMember = null;

        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                foundMember = member;
                if (print) {
                    System.out.println(member);
                }
                break;
            }
        }

        if (foundMember == null) {
            System.out.println("No members found with email: " + email);
        }
        return foundMember;
    }
}
