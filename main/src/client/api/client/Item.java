package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class Item {

    private Object raw;

    public Item(Object raw) {
        this.raw = raw;
    }

    public Object getId() {
        return Reflection.value("Item#getId", raw);
    }

    public Object getStackSize() {
        return Reflection.value("Item#getStackSize", raw);
    }

}
