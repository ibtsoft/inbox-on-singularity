package com.ibtsoft.inbox.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.ibtsoft.singularity.core.repository.entity.EntityRef;

public class Folder {

    private String name;
    private List<EntityRef<Mail>> mails;

    public Folder() {
        mails = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityRef<Mail>> getMails() {
        return ImmutableList.copyOf(mails);
    }

    public void addMail(EntityRef<Mail> mail) {
        this.mails.add(mail);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Folder{");
        sb.append("name='").append(name).append('\'');
        sb.append(", mails=").append(mails);
        sb.append('}');
        return sb.toString();
    }
}


