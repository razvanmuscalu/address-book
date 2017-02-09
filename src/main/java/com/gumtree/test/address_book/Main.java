package com.gumtree.test.address_book;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("src/main/resources/AddressBook");

        out.println("========= Address Book ==========\n");

        out.println("======== Count by gender ========");
        out.println("Male: " + addressBook.countByGender("Male"));
        out.println("Female: " + addressBook.countByGender("Female"));

        out.println("\n========== Find oldest ==========");
        out.println("Oldest: " + addressBook.oldest());

        out.println("\n========== Compare age ==========");
        out.println("Bill is " + addressBook.compare("BillMcKnight", "PaulRobinson") + " days older than Paul");
    }
}
