package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class GrandExchangeItem {

    private Object raw;

    public GrandExchangeItem(Object raw) {
        this.raw = raw;
    }

    public Object getAmount() {
        return Reflection.value("GrandExchangeItem#getAmount", raw);
    }

    public Object getByte() {
        return Reflection.value("GrandExchangeItem#getByte", raw);
    }

    public Object getId() {
        return Reflection.value("GrandExchangeItem#getId", raw);
    }

    public Object getPrice() {
        return Reflection.value("GrandExchangeItem#getPrice", raw);
    }
}
