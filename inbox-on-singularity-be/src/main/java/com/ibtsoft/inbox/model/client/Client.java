package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.repository.entity.EntityRef;
import com.singularity.security.User;

public class Client {

    private String name;
    private EntityRef<User> user;

    public Client(String name, EntityRef<User> user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public EntityRef<User> getUser() {
        return user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
