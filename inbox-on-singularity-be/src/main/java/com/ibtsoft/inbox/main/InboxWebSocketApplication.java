package com.ibtsoft.inbox.main;

import com.ibtsoft.inbox.config.InboxConfiguration;
import com.ibtsoft.singularity.web.websocket.WebSocketJettyServer;

public class InboxWebSocketApplication {

    private static WebSocketJettyServer server;

    public static void main(String[] args) throws Exception {
        InboxConfiguration inboxConfiguration = new InboxConfiguration();
        inboxConfiguration.configure();

        server = new WebSocketJettyServer();
        server.start(inboxConfiguration.getSecurityManager(), inboxConfiguration.getActionsRepository());
    }

    public static void stop() throws Exception {
        server.stop();
    }
}
