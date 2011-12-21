package com.intertech.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ContactList {

    @Autowired
    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
