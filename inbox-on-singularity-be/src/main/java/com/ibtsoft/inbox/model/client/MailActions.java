package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.repository.entity.EntityValue;
import com.ibtsoft.singularity.core.action.Action;
import com.ibtsoft.singularity.core.repository.IRepository;
import com.singularity.security.SecuredRepositoryDescriptor;
import com.singularity.security.SecurityManager;
import com.singularity.security.User;
import com.singularity.security.UserAwareActionExecutionContext;
import com.singularity.security.UserId;

import static java.lang.String.format;

public class MailActions {

    private final SecurityManager securityManager;

    public MailActions(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Action
    public void sendMail(UserAwareActionExecutionContext user, String fromUsername, String toUsername, String subject, String body) {
        IRepository<Client> clientSenderRepository = securityManager.getRepository(Client.class, UserId.forUUID(user.getUserId().getUuid()));

        EntityValue<Client> fromClient = clientSenderRepository.findAll().stream()
            .filter(clientEntityValue -> clientEntityValue.getValue().getUser().getId().equals(user.getUserId().getUuid()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(format("Cannot find user with username %s", fromUsername)));

        EntityValue<User> toUser = securityManager.getUserByUsername(toUsername);

        IRepository<Client> clientRecipientRepository = securityManager.getRepository(Client.class, UserId.forUUID(toUser.getId()));

        EntityValue<Client> toClient = clientRecipientRepository.findAll().stream()
            .filter(clientEntityValue -> clientEntityValue.getValue().getUser().getId().equals(toUser.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(format("Cannot find user with username %s", toUsername)));

        IRepository<Mail> mailRepository = securityManager.getRepository(SecuredRepositoryDescriptor.forClassAndUserId(Mail.class, user.getUserId()));

        EntityValue<Mail> mail = mailRepository.save(new Mail(fromClient, toClient, subject, body));
        securityManager.addAcl(UserId.forUUID(toUser.getId()), mail.getRef(), false, true, false);

        IRepository<MailBox> mailBoxRecipientRepository = securityManager.getRepository(MailBox.class, UserId.forUUID(toUser.getId()));
        EntityValue<MailBox> mailBoxRecipient = mailBoxRecipientRepository.findAll().stream().findFirst().get();
        IRepository<Folder> folderRecipientRepository = securityManager.getRepository(Folder.class, UserId.forUUID(toUser.getId()));
        EntityValue<Folder> folderRecipient = folderRecipientRepository.findById(mailBoxRecipient.getValue().getInbox().getId());
        folderRecipient.getValue().addMail(mail.getRef());

        IRepository<MailBox> mailBoxSenderRepository = securityManager.getRepository(MailBox.class, UserId.forUUID(user.getUserId().getUuid()));
        EntityValue<MailBox> mailBoxSender = mailBoxSenderRepository.findAll().stream().findFirst().get();
        IRepository<Folder> folderSenderRepository = securityManager.getRepository(Folder.class, UserId.forUUID(user.getUserId().getUuid()));
        EntityValue<Folder> folderSender = folderSenderRepository.findById(mailBoxSender.getValue().getSent().getId());
        folderSender.getValue().addMail(mail.getRef());
    }
}
