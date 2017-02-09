package com.gumtree.test.address_book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class AddressBookReadTest {

    @RunWith(Parameterized.class)
    public static class ParameterizedTest {

        @Parameterized.Parameters(name = "{0} contains {1} valid lines")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"AddressBook", 5},
                    {"AddressBook_missing_name", 0},
                    {"AddressBook_missing_gender", 0},
                    {"AddressBook_missing_dob", 0},
                    {"AddressBook_empty_name", 2},
                    {"AddressBook_empty_gender", 2},
                    {"AddressBook_empty_dob", 2}
            });
        }

        private final String fileName;
        private final int lineCount;

        public ParameterizedTest(String fileName, int lineCount) {
            this.fileName = fileName;
            this.lineCount = lineCount;
        }

        @Test
        public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
            AddressBook sut = new AddressBook("src/test/resources/" + fileName);

            assertThat("should include or ignore line accordingly", sut.read().size(), is(lineCount));
        }
    }

    public static class NonParameterizedTest {

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        private AddressBook sut;

        @Test
        public void shouldThrowExceptionIfUnableToReadFile() {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Error occured while reading address book");

            sut = new AddressBook("invalid");

            sut.read();
        }
    }
}
