package com.owners.gravitas.dto;

import java.util.List;

import com.hubzu.notification.dto.client.email.data.EmailData;

public class NotificationResponseObject {

    private List< EmailData > content;

    public List< EmailData > getContent() {
        return content;
    }

    public void setContent( List< EmailData > content ) {
        this.content = content;
    }

}
