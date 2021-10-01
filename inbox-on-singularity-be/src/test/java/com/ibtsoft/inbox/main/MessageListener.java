package com.ibtsoft.inbox.main;

import com.ibtsoft.singularity.web.messages.Message;

public interface MessageListener {

    boolean onMessage(Message message);
}
