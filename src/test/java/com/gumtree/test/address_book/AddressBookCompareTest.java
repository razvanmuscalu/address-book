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
public class AddressBookCompareTest {

    @RunWith(Parameterized.class)
    public static class ParameterizedTest {

        @Parameterized.Parameters(name = "in {0}, {1} is {3} days older than {2}")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"AddressBook", "BillMcKnight", "PaulRobinson", 2862L},
                    {"AddressBook", "PaulRobinson", "BillMcKnight", -2862L},
                    {"AddressBook_empty_name", "", "PaulRobinson", 2862L},
                    {"AddressBook_empty_name", "PaulRobinson", "", -2862L},
                    {"AddressBook_empty_gender", "BillMcKnight", "PaulRobinson", 2862L},
                    {"AddressBook_empty_gender", "PaulRobinson", "BillMcKnight", -2862L}
            });
        }

        private final String fileName;
        private final String person1Name;
        private final String person2Name;
        private final long difference;

        public ParameterizedTest(String fileName,
                                 String person1Name,
                                 String person2Name,
                                 long difference) {
            this.fileName = fileName;
            this.person1Name = person1Name;
            this.person2Name = person2Name;
            this.difference = difference;
        }

        @Test
        public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
            AddressBook sut = new AddressBook("src/test/resources/" + fileName);

            assertThat("should compare correctly difference in days between two dates of birth", sut.compare(person1Name, person2Name), is(difference));
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

            sut.compare("BillMcKnight", "PaulRobinson");
        }

        @Test
        public void shouldThrowExceptionWhenMissingDateOfBirth() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to calculate difference between the dates of birth of BillMcKnight and PaulRobinson");

            sut = new AddressBook("src/test/resources/AddressBook_missing_dob");

            sut.compare("BillMcKnight", "PaulRobinson");
        }

        @Test
        public void shouldThrowExceptionWhenMissingDateOfName() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to calculate difference between the dates of birth of BillMcKnight and PaulRobinson");

            sut = new AddressBook("src/test/resources/AddressBook_missing_name");

            sut.compare("BillMcKnight", "PaulRobinson");
        }

        @Test
        public void shouldThrowExceptionWhenMissingGender() throws Exception {
            thrown.expect(AddressBookException.class);
            thrown.expectMessage("Unable to calculate difference between the dates of birth of BillMcKnight and PaulRobinson");

            sut = new AddressBook("src/test/resources/AddressBook_missing_gender");

            sut.compare("BillMcKnight", "PaulRobinson");
        }
    }
}
