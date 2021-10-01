package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.Entity;

public class Organization {

    private String name;
    private Entity<MailBox> mailBoxes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity<MailBox> getMailBoxes() {
        return mailBoxes;
    }

    public void setMailBoxes(Entity<MailBox> mailBoxes) {
        this.mailBoxes = mailBoxes;
    }
}
