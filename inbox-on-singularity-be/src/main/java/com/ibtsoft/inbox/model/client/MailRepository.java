package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.persistence.Persistence;
import com.ibtsoft.singularity.core.repository.Repository;
import com.ibtsoft.singularity.core.repository.reflection.entitystructure.EntityStructureCache;

public class MailRepository extends Repository<Mail> {

    public MailRepository(EntityStructureCache entityStructureCache, Persistence<Mail> persistence) {
        super(Mail.class, entityStructureCache, persistence);
    }
}
