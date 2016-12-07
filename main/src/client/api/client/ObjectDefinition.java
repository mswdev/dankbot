package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class ObjectDefinition {

    private Object raw;

    public ObjectDefinition(Object raw) {
        this.raw = raw;
    }

    public String[] getActions() {
        return (String[]) Reflection.value("ObjectDefinition#getName", raw);
    }

    public String getName() {
        return (String) Reflection.value("ObjectDefinition#getName", raw);
    }
}
