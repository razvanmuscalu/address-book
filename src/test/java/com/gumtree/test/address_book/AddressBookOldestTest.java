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
public class AddressBookOldestTest {

    @RunWith(Parameterized.class)
    public static class ParameterizedTest {

        @Parameterized.Parameters(name = "oldest person in {0} is {1}")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"AddressBook", "WesJackson"},
                    {"AddressBook_empty_name", ""},
                    {"AddressBook_empty_gender", "BillMcKnight"}
            });
        }

        private final String fileName;
        private final String oldest;

        public ParameterizedTest(String fileName, String oldest) {
            this.fileName = fileName;
            this.oldest = oldest;
        }

        @Test
        public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
            AddressBook sut = new AddressBook("src/test/resources/" + fileName);

            assertThat("should return oldest person's name", sut.oldest(), is(oldest));
        }
    }

    public static class NonParameterizedTest {

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        private AddressBook sut;

        @Test
        public void shouldThrowExceptionWhenEmptyDateOfBirth() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Error occured while parsing date of birth");

            sut = new AddressBook("src/test/resources/AddressBook_empty_dob");

            sut.oldest();
        }

        @Test
        public void shouldThrowExceptionWhenMissingDateOfBirth() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to find oldest person");

            sut = new AddressBook("src/test/resources/AddressBook_missing_dob");

            sut.oldest();
        }

        @Test
        public void shouldThrowExceptionWhenMissingDateOfName() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to find oldest person");

            sut = new AddressBook("src/test/resources/AddressBook_missing_name");

            sut.oldest();
        }

        @Test
        public void shouldThrowExceptionWhenMissingGender() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to find oldest person");

            sut = new AddressBook("src/test/resources/AddressBook_missing_gender");

            sut.oldest();
        }
    }
}
