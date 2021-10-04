package com.ibtsoft.inbox.model.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.ImmutableList;
import com.ibtsoft.singularity.core.Entity;
import com.ibtsoft.singularity.core.EntityValue;

public class Organization {

    public Organization(String name) {
        this.name = name;
        this.mailBoxes = new CopyOnWriteArrayList<>();
    }

    private String name;
    private List<Entity<MailBox>> mailBoxes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity<MailBox>> getMailBoxes() {
        return ImmutableList.copyOf(mailBoxes);
    }

    public void addMailBox(EntityValue<MailBox> mailBox) {
        mailBoxes.add(mailBox);
    }
}
