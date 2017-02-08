package com.gumtree.test.address_book;

import java.time.LocalDate;

public class Person {

    private final String name;
    private final String gender;
    private final LocalDate dob;

    public Person(String name, String gender, LocalDate dob) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }
}
