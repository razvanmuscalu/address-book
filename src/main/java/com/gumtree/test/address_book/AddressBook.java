package com.gumtree.test.address_book;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.time.LocalDate.parse;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class AddressBook {

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final String inputPath;

    public AddressBook(String inputPath) {
        this.inputPath = inputPath;
    }

    public long countByGender(String gender) {
        List<String[]> lineParts = read();

        return lineParts
                .stream()
                .filter(parts -> parts[1].equals(gender))
                .count();
    }

    public String oldest() {
        List<String[]> lineParts = read();

        Optional<Person> oldestPerson = lineParts
                .stream()
                .map(parts -> {
                    try {
                        return new Person(parts[0], parts[1], parse(parts[2], DATE_FORMATTER));
                    } catch (DateTimeParseException e) {
                        throw new AddressBookException("Error occured while parsing date of birth");
                    }
                })
                .min(comparing(Person::getDob));

        if (oldestPerson.isPresent())
            return oldestPerson.get().getName();

        return "";
    }

    public long compare(String person1Name, String person2Name) {
        List<String[]> lineParts = read();

        Optional<Person> person1 = findPerson(person1Name, lineParts);
        Optional<Person> person2 = findPerson(person2Name, lineParts);

        if (person1.isPresent() && person2.isPresent()) {
            LocalDate person1Dob = person1.get().getDob();
            LocalDate person2Dob = person2.get().getDob();

            return DAYS.between(person1Dob, person2Dob);
        }

        throw new AddressBookException(format("Unable to calculate difference between the dates of birth of %s and %s", person1Name, person2Name));
    }

    private Optional<Person> findPerson(String person1, List<String[]> lineParts) {
        return lineParts
                .stream()
                .map(parts -> {
                    try {
                        return new Person(parts[0], parts[1], parse(parts[2], DATE_FORMATTER));
                    } catch (DateTimeParseException e) {
                        throw new AddressBookException("Error occured while parsing date of birth");
                    }
                })
                .filter(p -> p.getName().equals(person1))
                .findAny();
    }

    public List<String[]> read() {
        Stream<String> lines;
        try {
            lines = lines(get(inputPath));
        } catch (IOException e) {
            throw new AddressBookException("Error occured while reading address book");
        }

        return lines
                .map(line -> line.replaceAll("\\s+", ""))
                .map(parts -> parts.split(",", -1))
                .filter(parts -> parts.length == 3)
                .collect(toList());
    }

}
