package com.gumtree.test.address_book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class AddressBookTest {

    private AddressBook sut;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCountCorrectly() throws Exception {
        sut = new AddressBook("src/test/resources/AddressBook");

        assertThat("should count correctly", sut.countByGender("Male"), is(3L));
        assertThat("should count correctly", sut.countByGender("Female"), is(2L));
    }

    @Test
    public void shouldThrowExceptionIfUnableToReadFile() throws Exception {
        thrown.expect(AddressBookException.class);
        thrown.expectMessage("Error occured while reading address book");

        sut = new AddressBook("invalid");

        sut.countByGender("Male");
    }
}
