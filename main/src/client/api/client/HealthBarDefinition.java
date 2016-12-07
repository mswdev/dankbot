package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class HealthBarDefinition {

    private Object raw;

    public HealthBarDefinition(Object raw) {
        this.raw = raw;
    }

    public int getMaxHealth() {
        return (int) Reflection.value("HealthBarDefinition#getMaxHealth", raw);
    }

}
