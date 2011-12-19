/*
 * Copyright (c) 2011 Interbit T & C - all rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intertech.tests;

import java.util.List;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.intertech.domain.Contact;
import com.intertech.domain.ContactList;
import com.intertech.service.ContactService;

/**
 * The Class ServiceTest.
 */
public class ServiceTest {

    private static ExpressionParser parser = new SpelExpressionParser();
    private static StandardEvaluationContext evalContext = new StandardEvaluationContext();

    /**
     * The Class ContactParserContext.
     */
    private static class ContactParserContext implements ParserContext {

        @Override
        public String getExpressionPrefix() {
            return "[";
        }

        @Override
        public String getExpressionSuffix() {
            return "]";
        }

        @Override
        public boolean isTemplate() {
            return true;
        }
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println("Begin test");
        testContactService();
        System.out.println("Test complete");
    }

    /**
     * Married contacts.
     *
     * @param contacts the contacts
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static List<Contact> marriedContacts(List<Contact> contacts) {
        evalContext.setVariable("contacts", contacts);
        return (List<Contact>) parser.parseExpression("#contacts.?[married]").getValue(evalContext);
    }

    /**
     * Gets the first names.
     *
     * @param contacts the contacts
     * @return the first names
     */
    @SuppressWarnings("unchecked")
    public static List<String> getFirstNames(List<Contact> contacts) {
        return (List<String>) parser.parseExpression("#contacts.![firstName]").getValue(evalContext);
    }

    /**
     * Contact display string.
     *
     * @param contact the contact
     * @return the string
     */
    public static String contactDisplayString(Contact contact) {
        evalContext.setVariable("contact", contact);
        return (String) parser.parseExpression("[#contact.lastName],[#contact.firstName] was born [(new java.text.SimpleDateFormat('MM/dd/yyyy')).format(#contact.dateOfBirth)] and has [#contact.children] kids.", new ContactParserContext()).getValue(evalContext);
    }

    /**
     * Test contact service.
     */
    public static void testContactService() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(new String[] { "spring-beans.xml", "test-beans.xml" });
        context.registerShutdownHook();
        ContactList list = context.getBean("testList", ContactList.class);
        ContactService service = context.getBean("contactService", ContactService.class);
        List<Contact> contacts = list.getContacts();
        for (Contact contact : contacts) {
            service.addContact(contact);
            System.out.println("Test: " + contact + " was added to the Contact DB");
        }
        System.out.println("Test: Married contacts:  " + marriedContacts(contacts));
    }
}
