package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class MessageChannel {

    private Object raw;

    public MessageChannel(Object raw) {
        this.raw = raw;
    }

    public Object getMessageIndex() {
        return Reflection.value("MessageChannel#getMessageIndex", raw);
    }

    public String[] getMessages() {
        return (String[]) Reflection.value("MessageChannel#getMessages", raw);
    }
}
