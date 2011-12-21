package com.intertech.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public class Contact implements Serializable {

    private static final long serialVersionUID = 5814771722315839951L;
    private Long id = 0L;
    @Value("#{'Pat'}")
    private String firstName;
    @Value("#{'Doe'}")
    private String lastName;
    @Value("#{'1900/1/1'}")
    private Date dateOfBirth = new Date();
    @Value("#{'false'}")
    private boolean married = false;
    @Value("#{0}")
    private int children;

    public Contact(String firstName, String lastName, Date dateOfBirth,
                    boolean married, int children) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.married = married;
        this.children = children;
    }

    public Contact(Long id, String firstName, String lastName,
                    Date dateOfBirth, boolean married, int children) {
        this(firstName, lastName, dateOfBirth, married, children);
        this.id = id;
    }

    public Contact() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public double getAge() {
        if (dateOfBirth != null) {
            long difference = System.currentTimeMillis() - dateOfBirth.getTime();
            return difference / 1000 / 60 / 60 / 24 / 365;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Contact " + firstName + " " + lastName + " (id: " + id + ") ";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + children;
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + (married ? 1231 : 1237);
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
        Contact other = (Contact) obj;
        if (children != other.children)
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (married != other.married)
            return false;
        return true;
    }
}
