package com.gumtree.test.address_book;

import java.io.IOException;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

public class AddressBook {

    private final String inputPath;

    public AddressBook(String inputPath) {
        this.inputPath = inputPath;
    }

    public long countByGender(String gender) throws IOException {
        Stream<String> lines = lines(get(inputPath));

        return lines
                .map(line -> line.replaceAll("\\s+", ""))
                .map(parts -> parts.split(","))
                .filter(parts -> parts[1].equals(gender))
                .count();

    }

}
