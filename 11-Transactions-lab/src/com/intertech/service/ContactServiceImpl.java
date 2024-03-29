package com.intertech.service;

import java.util.Date;
import java.util.List;

//import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.intertech.dao.ContactDao;
import com.intertech.domain.Contact;

@Service("contactService")
public class ContactServiceImpl implements ContactService,
                ApplicationContextAware {

    private ApplicationContext ctx;
    // Spring Annotations
    /*
     * @Autowired
     *
     * @Qualifier("contactDao")
     */
    // JSR-250 Annotations
    /* @Resource(name="contactDao") */
    // JSR-330 Annotations - requires com.springsource.javax.inject-1.0.0.jar
    @Inject
    @Named("contactDao")
    private ContactDao dao;

    private void publishContactAddEvent(Contact contact) {
        ContactAddedEvent event = new ContactAddedEvent(this);
        event.setContact(contact);
        ctx.publishEvent(event);
    }

    public ContactDao getDao() {
        return dao;
    }

    public void setDao(ContactDao dao) {
        this.dao = dao;
    }

    @Override
    public Contact addContact(String first, String last, Date dob,
                    boolean married, int children) {
        Contact contact = new Contact(first, last, dob, married, children);
        long id = dao.saveContact(contact);
        contact.setId(id);
        publishContactAddEvent(contact);
        return contact;
    }

    @Override
    public void addContact(Contact contact) {
        long id = dao.saveContact(contact);
        contact.setId(id);
        publishContactAddEvent(contact);
        /**
         * TODO: Remember to remove this line when done
         */
        causeFailure();
    }

    @Override
    public void removeContact(Contact contact) {
        removeContact(contact.getId());
    }

    @Override
    public void removeContact(long id) {
        dao.deleteContact(id);
    }

    @Override
    public void updateContact(Contact contact) {
        dao.updateContact(contact);
    }

    @Override
    public Contact getContact(long id) {
        return dao.getContact(id);
    }

    @Override
    public List<Contact> getContacts() {
        return dao.getContacts();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    private void causeFailure() {
        throw new RuntimeException("Kill it now!");
    }
}
