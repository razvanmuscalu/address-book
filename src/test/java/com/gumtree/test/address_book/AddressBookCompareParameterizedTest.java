package com.gumtree.test.address_book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class AddressBookCompareParameterizedTest {

    @Parameterized.Parameters(name = "in {0}, {1} is {3} days older than {2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"AddressBook", "BillMcKnight", "PaulRobinson", 2862L},
                {"AddressBook", "PaulRobinson", "BillMcKnight", -2862L},
                {"AddressBook_missing_name", "GemmaLane", "PaulRobinson", -2500L},
                {"AddressBook_missing_name", "PaulRobinson", "GemmaLane", 2500L},
                {"AddressBook_missing_gender", "GemmaLane", "PaulRobinson", -2500L},
                {"AddressBook_missing_gender", "PaulRobinson", "GemmaLane", 2500L},
                {"AddressBook_missing_dob", "GemmaLane", "PaulRobinson", -2500L},
                {"AddressBook_missing_dob", "PaulRobinson", "GemmaLane", 2500L},
                {"AddressBook_empty_name", "GemmaLane", "PaulRobinson", -2500L},
                {"AddressBook_empty_name", "PaulRobinson", "GemmaLane", 2500L},
                {"AddressBook_empty_gender", "GemmaLane", "PaulRobinson", -2500L},
                {"AddressBook_empty_gender", "PaulRobinson", "GemmaLane", 2500L}
        });
    }

    private final String fileName;
    private final String person1Name;
    private final String person2Name;
    private final long difference;

    public AddressBookCompareParameterizedTest(String fileName,
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
