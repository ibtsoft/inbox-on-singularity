package com.ibtsoft.inbox.config;

import com.google.common.collect.ImmutableMap;
import com.ibtsoft.inbox.action.OrganizationActions;
import com.ibtsoft.inbox.model.client.Client;
import com.ibtsoft.inbox.model.client.Mail;
import com.ibtsoft.inbox.model.client.MailActions;
import com.ibtsoft.inbox.model.client.MailBox;
import com.ibtsoft.inbox.model.client.Organization;
import com.ibtsoft.singularity.core.ActionsRepository;
import com.ibtsoft.singularity.core.Entity;
import com.ibtsoft.singularity.core.EntityValue;
import com.ibtsoft.singularity.core.Repository;
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
            .build());

        Repository<Organization> organizationRepository = singularity.createRepository(Organization.class);
        singularity.createRepository(MailBox.class);
        singularity.createRepository(Mail.class);

        securityManager = (SecurityManager) singularity.getRepositoriesManager();

        EntityValue<User> userJohn = securityManager.addUser(new User("john", "john-password"));
        EntityValue<User> userJane = securityManager.addUser(new User("jane", "jane-password"));

        Repository<Client> clientRepository = singularity.createRepository(Client.class);

        UserRepository userRepository = ((SecurityManager) singularity.getRepositoriesManager()).getUserRepository();

        Entity<Client> clientJohn = clientRepository.save(new Client("John Doe", userJohn.getRef(userRepository)));
        Entity<Client> clientJane = clientRepository.save(new Client("Kane Doe", userJane.getRef(userRepository)));

        SecuredRepository<Mail> securedMailRepository = (SecuredRepository<Mail>) securityManager.getRepository(Mail.class, UserId.forUUID(userJohn.getId()));

        securedMailRepository.save(new Mail(clientJohn, clientJane, "Test mail", "Test mail body"));
        securedMailRepository.save(new Mail(clientJane, clientJohn, "Test mail 2", "Test mail body 2"));

        actionsRepository = new ActionsRepository();
        actionsRepository.addActions(new MailActions(securedMailRepository, clientRepository));
        actionsRepository.addActions(new OrganizationActions(securityManager));

        EntityValue<Organization> organization = (EntityValue<Organization>) actionsRepository.executeAction(
            new UserAwareActionExecutionContext(UserId.forUUID(userJohn.getId())), "createOrganization", ImmutableMap.of("name", "MyOrganization"));

        actionsRepository.executeAction(new UserAwareActionExecutionContext(UserId.forUUID(userJohn.getId())), "joinOrganization",
            ImmutableMap.of("organization", organization.getId()));

        actionsRepository.executeAction(new UserAwareActionExecutionContext(UserId.forUUID(userJane.getId())), "joinOrganization",
            ImmutableMap.of("organization", organization.getId()));

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
