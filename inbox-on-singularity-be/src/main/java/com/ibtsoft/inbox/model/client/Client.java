package com.ibtsoft.inbox.model.client;

import com.ibtsoft.singularity.core.Entity;
import com.singularity.security.User;

public class Client {

    private String name;
    private Entity<User> user;

    public Client(String name, Entity<User> user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public Entity<User> getUser() {
        return user;
    }
}
