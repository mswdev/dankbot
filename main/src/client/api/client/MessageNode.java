package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class MessageNode {

    private Object raw;

    public MessageNode(Object raw) {
        this.raw = raw;
    }

    public Object getClan() {
        return Reflection.value("MessageNode#getClan", raw);
    }

    public Object getSender() {
        return Reflection.value("MessageNode#getSender", raw);
    }

    public Object getText() {
        return Reflection.value("MessageNode#getText", raw);
    }

    public Object getTime() {
        return Reflection.value("MessageNode#getTime", raw);
    }

    public Object getType() {
        return Reflection.value("MessageNode#getType", raw);
    }

}
