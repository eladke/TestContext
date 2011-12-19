package com.intertech.dao;

import org.aspectj.lang.JoinPoint;

import com.intertech.domain.Contact;

public interface Auditor {

    public void audit(JoinPoint jp, Contact contact);

    public void auditAfterReturning(JoinPoint jp, Contact contact);
}
