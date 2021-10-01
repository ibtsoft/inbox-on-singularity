package com.ibtsoft.inbox.main;

import com.ibtsoft.inbox.config.InboxConfiguration;
import com.ibtsoft.singularity.web.socketio.SocketIoJettyServer;

public class InboxSocketIoApplication {

    public static void main(String[] args) throws Exception {
        InboxConfiguration inboxConfiguration = new InboxConfiguration();
        inboxConfiguration.configure();

        SocketIoJettyServer server = new SocketIoJettyServer();
        server.start(inboxConfiguration.getSecurityManager(), inboxConfiguration.getActionsRepository());
    }
}

