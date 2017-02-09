package com.gumtree.test.address_book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class AddressBookCountParameterizedTest {

    @Parameterized.Parameters(name = "{0} contains {1} males and {2} females")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"AddressBook", 3, 2},
                {"AddressBook_missing_name", 0, 0},
                {"AddressBook_missing_gender", 0, 0},
                {"AddressBook_missing_dob", 0, 0},
                {"AddressBook_empty_name", 2, 0},
                {"AddressBook_empty_gender", 1, 0},
                {"AddressBook_empty_dob", 2, 0}
        });
    }

    private final String fileName;
    private final long maleCount;
    private final long femaleCount;

    public AddressBookCountParameterizedTest(String fileName, long maleCount, long femaleCount) {
        this.fileName = fileName;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    @Test
    public void shouldIgnoreRowWithMissingDateOfBirth() throws Exception {
        AddressBook sut = new AddressBook("src/test/resources/" + fileName);

        assertThat("should count male members correctly", sut.countByGender("Male"), is(maleCount));
        assertThat("should count female members correctly", sut.countByGender("Female"), is(femaleCount));
    }

}
