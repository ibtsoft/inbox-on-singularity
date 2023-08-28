package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.repository.entity.EntityRef;

public class MailBox {

    private EntityRef<Organization> organization;
    private EntityRef<Folder> inbox;
    private EntityRef<Folder> sent;

    public EntityRef<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(EntityRef<Organization> organization) {
        this.organization = organization;
    }

    public EntityRef<Folder> getInbox() {
        return inbox;
    }

    public void setInbox(EntityRef<Folder> inbox) {
        this.inbox = inbox;
    }

    public EntityRef<Folder> getSent() {
        return sent;
    }

    public void setSent(EntityRef<Folder> sent) {
        this.sent = sent;
    }
}
