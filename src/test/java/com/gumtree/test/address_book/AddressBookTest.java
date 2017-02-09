package com.gumtree.test.address_book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AddressBookTest {

    private AddressBook sut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfUnableToReadFile() {
        thrown.expect(AddressBookException.class);
        thrown.expectMessage("Error occured while reading address book");

        sut = new AddressBook("invalid");

        sut.read();
    }

    @Test
    public void shouldThrowExceptionIfUnableToParseDateOfBirthInOldest() {
        thrown.expect(AddressBookException.class);
        thrown.expectMessage("Error occured while parsing date of birth");

        sut = new AddressBook("src/test/resources/AddressBook_empty_dob");

        sut.oldest();
    }

    @Test
    public void shouldThrowExceptionIfUnableToParseDateOfBirthInCompare() {
        thrown.expect(AddressBookException.class);
        thrown.expectMessage("Error occured while parsing date of birth");

        sut = new AddressBook("src/test/resources/AddressBook_empty_dob");

        sut.compare("BillMcKnight", "PaulRobinson");
    }

    @Test
    public void shouldThrowExceptionIfCannotFindDateOfBirthInCompare() throws Exception {
        thrown.expect(AddressBookException.class);
        thrown.expectMessage("Unable to calculate difference between the dates of birth of invalid and PaulRobinson");

        sut = new AddressBook("src/test/resources/AddressBook");

        sut.compare("invalid", "PaulRobinson");
    }

}
