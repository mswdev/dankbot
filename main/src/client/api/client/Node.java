package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 10/15/2016.
 */
public class Node {

    protected Object raw;

    public Node(Object raw) {
        this.raw = raw;
    }

    public long getId() {
        return (long) Reflection.value("hk", "ez", raw);
    }

    public Object getNext() {
        return Reflection.value("hk", "es", raw);
    }

    public Object getPrevious() {
        return Reflection.value("hk", "eb", raw);
    }
}
