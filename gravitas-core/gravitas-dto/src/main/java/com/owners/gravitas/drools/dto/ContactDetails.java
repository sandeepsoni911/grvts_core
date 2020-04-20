package com.owners.gravitas.drools.dto;

public class ContactDetails {
    private String phoneNumber;

    private boolean status;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus( boolean status ) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContactDetails [phoneNumber=" + phoneNumber + ", status=" + status + "]";
    }

}
