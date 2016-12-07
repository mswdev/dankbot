package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class ItemDefinition {

    private Object raw;

    public ItemDefinition(Object raw) {
        this.raw = raw;
    }

    public Object getGroundActions() {
        return Reflection.value("ItemDefinition#getGroundActions", raw);
    }

    public Object getId() {
        return Reflection.value("ItemDefinition#getId", raw);
    }

    public Object getMember() {
        return Reflection.value("ItemDefinition#getMember", raw);
    }

    public Object getName() {
        return Reflection.value("ItemDefinition#getName", raw);
    }

    public Object getWidgetActions() {
        return Reflection.value("ItemDefinition#getWidgetActions", raw);
    }

}
