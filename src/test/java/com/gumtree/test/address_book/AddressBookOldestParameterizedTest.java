package com.gumtree.test.address_book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class AddressBookOldestParameterizedTest {

    @Parameterized.Parameters(name = "oldest person in {0} is {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"AddressBook", "WesJackson"},
                {"AddressBook_missing_name", "WesJackson"},
                {"AddressBook_missing_gender", "WesJackson"},
                {"AddressBook_missing_dob", "WesJackson"},
                {"AddressBook_empty_name", "WesJackson"},
                {"AddressBook_empty_gender", "WesJackson"},
//                {"AddressBook_empty_dob", "WesJackson"}
        });
    }

    private final String fileName;
    private final String oldest;

    public AddressBookOldestParameterizedTest(String fileName, String oldest) {
        this.fileName = fileName;
        this.oldest = oldest;
    }

    @Test
    public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
        AddressBook sut = new AddressBook("src/test/resources/" + fileName);

        assertThat("should return oldest person's name", sut.oldest(), is(oldest));
    }

}
