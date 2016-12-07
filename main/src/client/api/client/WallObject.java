package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class WallObject {

    private Object raw;

    public WallObject(Object raw) {
        this.raw = raw;
    }

    public Object getBackUpRenderable() {
        return Reflection.value("WallObject#getBackUpRenderable", raw);
    }

    public Object getFlags() {
        return Reflection.value("WallObject#getFlags", raw);
    }

    public Object getId() {
        return Reflection.value("WallObject#getId", raw);
    }

    public Object getOrientation() {
        return Reflection.value("WallObject#getOrientation", raw);
    }

    public Object getPlane() {
        return Reflection.value("WallObject#getPlane", raw);
    }

    public Object getRelativeX() {
        return Reflection.value("WallObject#getRelativeX", raw);
    }

    public Object getRelativeY() {
        return Reflection.value("WallObject#getRelativeY", raw);
    }

    public Object getRenderable() {
        return Reflection.value("WallObject#getRenderable", raw);
    }

    public Object getX() {
        return Reflection.value("WallObject#getX", raw);
    }

    public Object getY() {
        return Reflection.value("WallObject#getY", raw);
    }

}
