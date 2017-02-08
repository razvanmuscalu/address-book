package com.gumtree.test.address_book;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.time.LocalDate.parse;
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
                .map(parts -> new Person(parts[0], parts[1], parse(parts[2], DATE_FORMATTER)))
                .min(comparing(Person::getDob));

        if (oldestPerson.isPresent())
            return oldestPerson.get().getName();

        return "";
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
