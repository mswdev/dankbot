package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class Friend {

    private Object raw;

    public Friend(Object raw) {
        this.raw = raw;
    }

    public String getName() {
        return (String) Reflection.value("Friend#getName", raw);
    }

    public String getPreviousName() {
        return (String) Reflection.value("Friend#getPreviousName", raw);
    }

    public int getWorld() {
        return (int) Reflection.value("Friend#getWorld", raw);
    }
}
