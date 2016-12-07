package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class CollisionMap {

    private Object raw;

    public CollisionMap(Object raw) {
        this.raw = raw;
    }

    public Object getFlags() {
        return Reflection.value("CollisionMap#getFlags", raw);
    }

    public Object getHeight() {
        return Reflection.value("CollisionMap#getHeight", raw);
    }

    public Object getOffsetX() {
        return Reflection.value("CollisionMap#getOffsetX", raw);
    }

    public Object getOffsetY() {
        return Reflection.value("CollisionMap#getOffsetY", raw);
    }

    public Object getWidth() {
        return Reflection.value("CollisionMap#getWidth", raw);
    }
}
