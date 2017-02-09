package com.gumtree.test.address_book;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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

    private final Function<String[], Person> lineToPerson = parts -> {
        try {
            return new Person(parts[0], parts[1], parse(parts[2], DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            throw new AddressBookException("Error occured while parsing date of birth");
        }
    };

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
                .map(lineToPerson)
                .min(comparing(Person::getDob));

        return oldestPerson.orElseThrow(() -> new AddressBookException("Unable to find oldest person")).getName();
    }

    public long compare(String person1Name, String person2Name) {
        List<String[]> lineParts = read();

        LocalDate person1DateOfBirth = findPersonDateOfBirth(person1Name, lineParts);
        LocalDate person2DateOfBirth = findPersonDateOfBirth(person2Name, lineParts);

        return DAYS.between(person1DateOfBirth, person2DateOfBirth);
    }

    public List<String[]> read() {
        Stream<String> lines;
        try {
            lines = lines(get(inputPath));
        } catch (IOException e) {
            throw new AddressBookException("Error occured while reading address book");
        }

        return lines
                .map(formatLine())
                .map(split())
                .filter(validateLine())
                .collect(toList());
    }

    private LocalDate findPersonDateOfBirth(String name, List<String[]> lineParts) {
        return lineParts
                .stream()
                .map(lineToPerson)
                .filter(hasName(name))
                .findAny()
                .orElseThrow(() -> new AddressBookException(format("Unable to find %s", name)))
                .getDob();
    }

    private static Function<String, String> formatLine() {
        return line -> line.replaceAll("\\s+", "");
    }

    private static Predicate<String[]> validateLine() {
        return parts -> parts.length == 3;
    }

    private static Function<String, String[]> split() {
        return parts -> parts.split(",", -1);
    }

    private static Predicate<Person> hasName(String name) {
        return p -> p.getName().equals(name);
    }
}
