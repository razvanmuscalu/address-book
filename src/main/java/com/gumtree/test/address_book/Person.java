package com.gumtree.test.address_book;

import java.time.LocalDate;
import java.util.function.Predicate;

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

    public static Predicate<Person> hasGender(String gender) {
        return p -> p.getGender().equals(gender);
    }

    public static Predicate<Person> hasName(String name) {
        return p -> p.getName().equals(name);
    }
}
