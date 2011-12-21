package com.intertech.service;

import java.util.Date;
import java.util.List;

import com.intertech.domain.Contact;

public interface ContactService {

    public Contact addContact(String first, String last, Date dob,
                    boolean married, int children);

    public void addContact(Contact contact);

    public void updateContact(Contact contact);

    public void removeContact(Contact contact);

    public void removeContact(long id);

    public List<Contact> getContacts();

    public Contact getContact(long id);
}
