package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.Action;
import com.ibtsoft.singularity.core.EntityValue;
import com.ibtsoft.singularity.core.IRepository;

import static java.lang.String.format;

public class MailActions {

    private final IRepository<Mail> mailRepository;
    private final IRepository<Client> clientRepository;

    public MailActions(IRepository<Mail> mailRepository, IRepository<Client> clientRepository) {
        this.mailRepository = mailRepository;
        this.clientRepository = clientRepository;
    }

    @Action
    public void sendMail(String fromUsername, String toUsername, String subject, String body) {
        EntityValue<Client> fromClient = clientRepository.findAll().stream()
            .filter(clientEntityValue -> clientEntityValue.getValue().getUser().getValue().getUsername().equals(fromUsername))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(format("Cannot find user with username %s", fromUsername)));

        EntityValue<Client> toClient = clientRepository.findAll().stream()
            .filter(clientEntityValue -> clientEntityValue.getValue().getUser().getValue().getUsername().equals(toUsername))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(format("Cannot find user with username %s", toUsername)));

        mailRepository.save(new Mail(fromClient, toClient, subject, body));
    }
}
