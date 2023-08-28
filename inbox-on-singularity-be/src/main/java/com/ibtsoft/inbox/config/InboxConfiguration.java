package com.ibtsoft.inbox.config;

import com.google.common.collect.ImmutableMap;
import com.ibtsoft.inbox.action.OrganizationActions;
import com.ibtsoft.inbox.model.client.Client;
import com.ibtsoft.inbox.model.client.Folder;
import com.ibtsoft.inbox.model.client.Mail;
import com.ibtsoft.inbox.model.client.MailActions;
import com.ibtsoft.inbox.model.client.MailBox;
import com.ibtsoft.inbox.model.client.Organization;
import com.ibtsoft.singularity.core.repository.RepositoryDescriptor;
import com.ibtsoft.singularity.core.repository.entity.EntityValue;
import com.ibtsoft.singularity.core.action.ActionsRepository;
import com.ibtsoft.singularity.core.repository.Repository;
import com.ibtsoft.singularity.core.Singularity;
import com.ibtsoft.singularity.core.SingularityConfiguration;
import com.singularity.security.SecuredRepository;
import com.singularity.security.SecuredRepositoryManagerFactory;
import com.singularity.security.SecurityManager;
import com.singularity.security.User;
import com.singularity.security.UserAwareActionExecutionContext;
import com.singularity.security.UserId;
import com.singularity.security.UserRepository;

public class InboxConfiguration {

    private SecurityManager securityManager;
    private ActionsRepository actionsRepository;

    public void configure() {
        Singularity singularity = new Singularity(SingularityConfiguration.builder()
            .repositoryManagerFactory(new SecuredRepositoryManagerFactory())
            .withEntityType(Organization.class)
            .withEntityType(MailBox.class)
            .withEntityType(Folder.class)
            .withEntityType(Mail.class)
            .withEntityType(Client.class)
            .build());

        securityManager = (SecurityManager) singularity.getRepositoriesManager();

        EntityValue<User> userJohn = securityManager.addUser(new User("john", "john-password"));
        EntityValue<User> userJane = securityManager.addUser(new User("jane", "jane-password"));

        UserRepository userRepository = ((SecurityManager) singularity.getRepositoriesManager()).getUserRepository();

        EntityValue<Client> clientJohn = securityManager.getRepository(Client.class, UserId.forUUID(userJohn.getId()))
            .save(new Client("John Doe", userJohn.getRef()));
        EntityValue<Client> clientJane = securityManager.getRepository(Client.class, UserId.forUUID(userJane.getId()))
            .save(new Client("Jane Doe", userJane.getRef()));

        SecuredRepository<Mail> securedMailRepository = securityManager.getRepository(Mail.class, UserId.forUUID(userJohn.getId()));

        actionsRepository = new ActionsRepository();
        actionsRepository.addActions(new MailActions(securityManager));
        actionsRepository.addActions(new OrganizationActions(securityManager));

        EntityValue<Organization> organization = (EntityValue<Organization>) actionsRepository.executeAction(
            new UserAwareActionExecutionContext(UserId.forUUID(userJohn.getId())), "createOrganization", ImmutableMap.of("name", "MyOrganization"));

        actionsRepository.executeAction(new UserAwareActionExecutionContext(UserId.forUUID(userJohn.getId())), "joinOrganization",
            ImmutableMap.of("organization", organization.getId()));

        actionsRepository.executeAction(new UserAwareActionExecutionContext(UserId.forUUID(userJane.getId())), "joinOrganization",
            ImmutableMap.of("organization", organization.getId()));

        actionsRepository.executeAction(
            new UserAwareActionExecutionContext(UserId.forUUID(userJohn.getId())),
            "sendMail",
            ImmutableMap.of(
                "fromUsername", userJohn.getValue().getUsername(),
                "toUsername", userJane.getValue().getUsername(),
                "subject", "Test mail from John to Jane",
                "body", "Hi Jane! \n\n This is a test mail from John.\n\n With regards, \n John Doe"));

        actionsRepository.executeAction(
            new UserAwareActionExecutionContext(UserId.forUUID(userJane.getId())),
            "sendMail",
            ImmutableMap.of(
                "fromUsername", userJane.getValue().getUsername(),
                "toUsername", userJohn.getValue().getUsername(),
                "subject", "Test mail from Jane to John",
                "body", "Hi John! \n\n This is a test mail from Jane.\n\n With regards, \n Jane Doe"));

    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public ActionsRepository getActionsRepository() {
        return actionsRepository;
    }

    public void setActionsRepository(ActionsRepository actionsRepository) {
        this.actionsRepository = actionsRepository;
    }
}
