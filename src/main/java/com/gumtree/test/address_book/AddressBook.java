package com.gumtree.test.address_book;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

public class AddressBook {

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
