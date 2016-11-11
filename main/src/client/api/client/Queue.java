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
    public CacheNode cacheNode() {
        return new CacheNode(Reflection.value("gq", "k", raw));
    }
}
