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
        return (long) Reflection.value("Node#getId", raw);
    }

    public Object getNext() {
        return Reflection.value("Node#getNext", raw);
    }

    public Object getPrevious() {
        return Reflection.value("Node#getPrevious", raw);
    }
}
