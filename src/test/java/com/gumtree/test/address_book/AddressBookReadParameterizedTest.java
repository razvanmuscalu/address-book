package com.gumtree.test.address_book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class AddressBookReadParameterizedTest {

    @Parameterized.Parameters(name = "{0} contains {1} valid lines")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"AddressBook", 5},
                {"AddressBook_missing_name", 4},
                {"AddressBook_missing_gender", 4},
                {"AddressBook_missing_dob", 4},
                {"AddressBook_empty_name", 5},
                {"AddressBook_empty_gender", 5},
                {"AddressBook_empty_dob", 5}
        });
    }

    private final String fileName;
    private final int lineCount;

    public AddressBookReadParameterizedTest(String fileName, int lineCount) {
        this.fileName = fileName;
        this.lineCount = lineCount;
    }

    @Test
    public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
        AddressBook sut = new AddressBook("src/test/resources/" + fileName);

        assertThat("should include or ignore line accordingly", sut.read().size(), is(lineCount));
    }

}
