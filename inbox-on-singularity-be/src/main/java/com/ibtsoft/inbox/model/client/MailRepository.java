package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.Persistence;
import com.ibtsoft.singularity.core.Repository;

public class MailRepository extends Repository<Mail> {

    public MailRepository(Class<Mail> repositoryClass, Persistence<Mail> persistence) {
        super(repositoryClass, persistence);
    }


}
