package com.intertech.dao;

import java.util.List;

import com.intertech.domain.Contact;

public interface ContactDao {

    public Contact getContact(Long id);

    public List<Contact> getContacts();

    public long saveContact(Contact contact);

    public void updateContact(Contact contact);

    public void deleteContact(Long id);
}
