package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/6/2016.
 */
public class NPCDefinition {

    private Object raw;

    public NPCDefinition(Object raw) {
        this.raw = raw;
    }

    public int getId() {
        return (int) Reflection.value("NPCDefinition#getId", raw);
    }

    public String getName() {
        return (String) Reflection.value("NPCDefinition#getName", raw);
    }

}
