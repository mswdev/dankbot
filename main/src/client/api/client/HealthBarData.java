package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class HealthBarData {

    private Object raw;

    public HealthBarData(Object raw) {
        this.raw = raw;
    }

    public int getCurrentHealth() {
        return (int) Reflection.value("HealthBarData#getCurrentHealth", raw);
    }
}
