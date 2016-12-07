package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class GameObject {

    private Object raw;

    public GameObject(Object raw) {
        this.raw = raw;
    }

    public Object getFlags() {
        return Reflection.value("GameObject#getFlags", raw);
    }

    public Object getHeight() {
        return Reflection.value("GameObject#getHeight", raw);
    }

    public Object getId() {
        return Reflection.value("GameObject#getId", raw);
    }

    public Object getOffsetX() {
        return Reflection.value("GameObject#getOffsetX", raw);
    }

    public Object getOffsetY() {
        return Reflection.value("GameObject#getOffsetY", raw);
    }

    public Object getOrientation() {
        return Reflection.value("GameObject#getOrientation", raw);
    }

    public Object getPlane() {
        return Reflection.value("GameObject#getPlane", raw);
    }

    public Object getRelativeX() {
        return Reflection.value("GameObject#getRelativeX", raw);
    }

    public Object getRelativeY() {
        return Reflection.value("GameObject#getRelativeY", raw);
    }

    public Object getRenderable() {
        return Reflection.value("GameObject#getRenderable", raw);
    }

    public Object getX() {
        return Reflection.value("GameObject#getX", raw);
    }

    public Object getY() {
        return Reflection.value("GameObject#getY", raw);
    }

}
