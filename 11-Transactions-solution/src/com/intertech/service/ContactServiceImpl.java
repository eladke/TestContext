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
import org.springframework.transaction.annotation.Transactional;

import com.intertech.dao.ContactDao;
import com.intertech.domain.Contact;

@Service("contactService")
@Transactional
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
    @Transactional(readOnly=false)
    public Contact addContact(String first, String last, Date dob,
                    boolean married, int children) {
        Contact contact = new Contact(first, last, dob, married, children);
        long id = dao.saveContact(contact);
        contact.setId(id);
        publishContactAddEvent(contact);
        return contact;
    }

    @Override
    @Transactional(readOnly=false)
    public void addContact(Contact contact) {
        long id = dao.saveContact(contact);
        contact.setId(id);
        publishContactAddEvent(contact);
        //causeFailure();
    }

    @Override
    @Transactional(readOnly=false)
    public void removeContact(Contact contact) {
        removeContact(contact.getId());
    }

    @Override
    @Transactional(readOnly=false)
    public void removeContact(long id) {
        dao.deleteContact(id);
    }

    @Override
    @Transactional(readOnly=false)
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

    @SuppressWarnings("unused")
    private void causeFailure() {
        throw new RuntimeException("Kill it now!");
    }
}
