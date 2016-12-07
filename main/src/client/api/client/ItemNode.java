package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class ItemNode {

    private Object raw;

    public ItemNode(Object raw) {
        this.raw = raw;
    }

    public Object getIds() {
        return Reflection.value("ItemNode#getIds", raw);
    }

    public Object getStackSizes() {
        return Reflection.value("ItemNode#getStackSizes", raw);
    }
}
