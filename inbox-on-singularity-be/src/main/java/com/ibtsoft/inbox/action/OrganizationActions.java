package com.ibtsoft.inbox.action;

import java.util.UUID;

import com.ibtsoft.inbox.model.client.MailBox;
import com.ibtsoft.inbox.model.client.Organization;
import com.ibtsoft.singularity.core.Action;
import com.ibtsoft.singularity.core.EntityValue;
import com.ibtsoft.singularity.core.Id;
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
        SecuredRepository<Organization> organizationRepository =  securityManager.getRepository(Organization.class, user.getUserId());
        return organizationRepository.save(new Organization(name));
    }

    @Action
    public void joinOrganization(UserAwareActionExecutionContext user, UUID organizationId) {
        SecuredRepository<Organization> organizationRepository =  securityManager.getRepository(Organization.class, user.getUserId());
        EntityValue<Organization> organization = organizationRepository.findById(organizationId);

        SecuredRepository<MailBox> mailBoxRepository = securityManager.getRepository(MailBox.class, user.getUserId());
        EntityValue<MailBox> mailBox = mailBoxRepository.save(new MailBox());

        organization.getValue().addMailBox(mailBox);
        organizationRepository.update(organization);
    }
}
