package com.intertech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intertech.domain.Contact;

// Looks like the '@Aspect' annotation is not enough for Spring to pick up the class
// and create a bean out of it. For this reason, we add the @Component annotation too
// and then, we can remove the bean declaration from the bean configuration file
@Component("auditor")
@Aspect
public class AuditorImpl implements Auditor {


    private DataSource datasource;

    @Autowired
    public AuditorImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    @After("execution(* com.intertech.service.ContactServiceImpl.*(..)) && args(contact)")
    public void audit(JoinPoint jp, Contact contact) {
        System.out.println("AUDIT: after execution -> auditing insertion of contact id " + contact.getId());
        insertAudit(jp.getSignature().getName(), contact.getId());
    }

    @Override
    @AfterReturning(pointcut = "execution(* com.intertech.service.ContactServiceImpl.*(String,..))",
                    returning = "contact")
    public void auditAfterReturning(JoinPoint jp, Contact contact) {
        System.out.println("AUDIT: after returning -> auditing insertion of contact id " + contact.getId());
        insertAudit(jp.getSignature().getName(), contact.getId());
    }

    private void insertAudit(String method, long contactId) {
        try {
            Connection conn = datasource.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO contact_audit (contact_id, operation, effective) VALUES (?,?,?)");
            st.setLong(1, contactId);
            st.setString(2, method);
            st.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            st.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        }
    }
}
