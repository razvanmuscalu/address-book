package com.gumtree.test.address_book;

import java.io.IOException;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws IOException {
        AddressBook addressBook = new AddressBook("src/main/resources/AddressBook");

        out.println("==== Address Book =====");
        out.println("=======================\n");

        out.println("=== Count by gender ===");
        out.println("Male: " + addressBook.countByGender("Male"));
        out.println("Female: " + addressBook.countByGender("Female"));
    }
}
