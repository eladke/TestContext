package com.intertech.service;

import org.springframework.context.ApplicationEvent;

import com.intertech.domain.Contact;

public class ContactAddedEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8845660396956362297L;
    private Contact contact;

    public ContactAddedEvent(Object source) {
        super(source);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contact == null) ? 0 : contact.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactAddedEvent other = (ContactAddedEvent) obj;
        if (contact == null) {
            if (other.contact != null)
                return false;
        } else if (!contact.equals(other.contact))
            return false;
        return true;
    }
}
