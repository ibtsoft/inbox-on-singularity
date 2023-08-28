package com.ibtsoft.inbox.action;

import java.util.UUID;

import com.ibtsoft.inbox.model.client.Folder;
import com.ibtsoft.inbox.model.client.MailBox;
import com.ibtsoft.inbox.model.client.Organization;
import com.ibtsoft.singularity.core.repository.entity.EntityValue;
import com.ibtsoft.singularity.core.action.Action;
import com.singularity.security.SecuredRepository;
import com.singularity.security.SecurityManager;
import com.singularity.security.UserAwareActionExecutionContext;

public class OrganizationActions {

    private final SecurityManager securityManager;

    public OrganizationActions(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Action
    public EntityValue<Organization> createOrganization(UserAwareActionExecutionContext user, String name) {
        SecuredRepository<Organization> organizationRepository = securityManager.getRepository(Organization.class, user.getUserId());
        return organizationRepository.save(new Organization(name));
    }

    @Action
    public void joinOrganization(UserAwareActionExecutionContext user, UUID organizationId) {
        SecuredRepository<Organization> organizationRepository = securityManager.getRepository(Organization.class, user.getUserId());
        EntityValue<Organization> organization = organizationRepository.findById(organizationId);

        SecuredRepository<MailBox> mailBoxRepository = securityManager.getRepository(MailBox.class, user.getUserId());

        SecuredRepository<Folder> folderRepository = securityManager.getRepository(Folder.class, user.getUserId());

        Folder inboxFolder = new Folder();
        inboxFolder.setName("Inbox");
        EntityValue<Folder> inboxFolderEntity = folderRepository.save(inboxFolder);

        Folder sentFolder = new Folder();
        sentFolder.setName("Sent");
        EntityValue<Folder> sentFolderEntity = folderRepository.save(sentFolder);

        MailBox mailBox = new MailBox();
        mailBox.setInbox(inboxFolderEntity.getRef());
        mailBox.setSent(sentFolderEntity.getRef());
        EntityValue<MailBox> mailBoxEntity = mailBoxRepository.save(mailBox);

        organization.getValue().addMailBox(mailBoxEntity.getRef());
        //organizationRepository.update(organization);
    }
}
