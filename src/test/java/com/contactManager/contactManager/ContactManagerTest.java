package com.contactManager.contactManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

class ContactManagerTest {
    ContactManager contactManager;
    @BeforeAll
    public static void setupAll(){
        System.out.println("Should print before all");
    }
    @BeforeEach
    public  void setup(){
        contactManager = new ContactManager();
    }
    @Test
    public void shouldCreateContact(){
        contactManager.addContact("Harshita","Ojha","0102345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("Harshita") &&
                        contact.getLastName().equals("Ojha") &&
                        contact.getPhoneNumber().equals("0102345678"))
                .findAny()
                .isPresent());
    }
    @Test
    @DisplayName("Should not create contact when last name is null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact("John",null,"0123456789");
        });
    }
    @Test
    @DisplayName("Should Not create contact when phone number is null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()-> {
            contactManager.addContact("Miley", "Cyrus",null);
        });
    }
    //similarly for AfterEach and AfterAll annotations
    @Test
    @DisplayName("Should create contact only on Mac")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
    public void shouldCreateContactOnlyOnMac(){
        contactManager.addContact("Harshita","Ojha","0102345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("Harshita") &&
                        contact.getLastName().equals("Ojha") &&
                        contact.getPhoneNumber().equals("0102345678"))
                .findAny()
                .isPresent());
    }
    @Test
    @DisplayName("Should not create contact on Windows")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldNotCreateContactOnlyOnWindows(){
        contactManager.addContact("Harshita","Ojha","0102345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("Harshita") &&
                        contact.getLastName().equals("Ojha") &&
                        contact.getPhoneNumber().equals("0102345678"))
                .findAny()
                .isPresent());
    }
    @Test
    @DisplayName("Should test contact creation on developer machine")
    public void shouldTestContactCreationOnDev(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("Harshita","Ojha","0102345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
    @DisplayName("Should test contact creation repeatedly")
    @RepeatedTest(value=5)
    public void shouldTestContactCreationRepeatedly(){
        contactManager.addContact("Harshita","Ojha","0102345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
    @DisplayName("Should test contact creation using value source")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "0123456769","010823456789"} )
    public void shouldTestContactCreationUsingValueSource(String phoneNumber){
        contactManager.addContact("Harshita","Ojha",phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
    @DisplayName("Should test contact creation using csv file source")
    @ParameterizedTest
    @CsvFileSource(resources = "/Data.csv")
    public void shouldTestContactCreationUsingCsvFileSource(String phoneNumber){
        contactManager.addContact("Harshita","Ojha",phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
}