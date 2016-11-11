package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 10/15/2016.
 */
public class Queue {

    private Object raw;

    public Queue(Object raw) {
        this.raw = raw;
    }

    public CacheNode getNacheNode() {
        return new CacheNode(Reflection.value("Queue#getCacheNode", raw));
    }
}
