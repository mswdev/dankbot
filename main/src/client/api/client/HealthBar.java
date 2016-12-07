package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class HealthBar {

    private Object raw;

    public HealthBar(Object raw) {
        this.raw = raw;
    }

    public HealthBarDefinition getDefinition() {
        return new HealthBarDefinition(Reflection.value("HealthBar#getDefinition", raw));
    }

    public HealthBarData getData() {
        return new HealthBarData(Reflection.value("HealthBar#getData", raw));
    }
}
