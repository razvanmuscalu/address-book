package com.gumtree.test.address_book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class AddressBookTest {

    private AddressBook sut = new AddressBook("src/test/resources/AddressBook");

    @Test
    public void shouldCountCorrectly() throws Exception {
        assertThat("should count correctly", sut.countByGender("Male"), is(3L));
        assertThat("should count correctly", sut.countByGender("Female"), is(2L));
    }
}
