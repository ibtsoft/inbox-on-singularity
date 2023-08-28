package com.ibtsoft.inbox.model.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.ImmutableList;
import com.ibtsoft.singularity.core.repository.entity.EntityRef;

public class Organization {

    private String name;
    private List<EntityRef<MailBox>> mailBoxes;

    public Organization(String name) {
        this.name = name;
        this.mailBoxes = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityRef<MailBox>> getMailBoxes() {
        return ImmutableList.copyOf(mailBoxes);
    }

    public void addMailBox(EntityRef<MailBox> mailBox) {
        mailBoxes.add(mailBox);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Organization{");
        sb.append("name='").append(name).append('\'');
        sb.append(", mailBoxes=").append(mailBoxes);
        sb.append('}');
        return sb.toString();
    }
}
