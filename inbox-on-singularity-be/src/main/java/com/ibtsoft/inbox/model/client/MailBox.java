package com.ibtsoft.inbox.model.client;

import java.util.List;

import com.ibtsoft.singularity.core.Entity;

public class MailBox {

    private Entity<Organization> organization;
    private Entity<Folder> inbox;
    private Entity<Folder> sent;

    public Entity<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(Entity<Organization> organization) {
        this.organization = organization;
    }

    public Entity<Folder> getInbox() {
        return inbox;
    }

    public void setInbox(Entity<Folder> inbox) {
        this.inbox = inbox;
    }

    public Entity<Folder> getSent() {
        return sent;
    }

    public void setSent(Entity<Folder> sent) {
        this.sent = sent;
    }

    public static class Folder {

        private String name;
        private List<Entity<Mail>> mails;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Entity<Mail>> getMails() {
            return mails;
        }

        public void setMails(List<Entity<Mail>> mails) {
            this.mails = mails;
        }
    }
}
