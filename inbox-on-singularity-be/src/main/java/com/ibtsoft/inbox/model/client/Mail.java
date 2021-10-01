package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.Entity;

public class Mail {

    private final Entity<Client> sender;
    private final Entity<Client> addressee ;
    private final String subject;
    private final String body;

    public Mail(Entity<Client> sender, Entity<Client> addressee, String subject, String body) {
        this.sender = sender;
        this.addressee = addressee;
        this.subject = subject;
        this.body = body;
    }

    public Entity<Client> getSender() {
        return sender;
    }

    public Entity<Client> getAddressee() {
        return addressee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
