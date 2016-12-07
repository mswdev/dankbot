package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class BoundaryObject {

    private Object raw;

    public BoundaryObject(Object raw) {
        this.raw = raw;
    }

    public int getBackUpOrientation() {
        return (int) Reflection.value("BoundaryObject#getBackUpOrientation", raw);
    }

    public Object getBackUpRenderable() {
        return Reflection.value("BoundaryObject#getBackUpRenderable", raw);
    }

    public Object getFlags() {
        return Reflection.value("BoundaryObject#getFlags", raw);
    }

    public int getId() {
        return (int) Reflection.value("BoundaryObject#getId", raw);
    }

    public int getOrientation() {
        return (int) Reflection.value("BoundaryObject#getOrientation", raw);
    }

    public int getPlane() {
        return (int) Reflection.value("BoundaryObject#getPlane", raw);
    }

    public Object getRenderable() {
        return Reflection.value("BoundaryObject#getRenderable", raw);
    }

    public int getX() {
        return (int) Reflection.value("BoundaryObject#getX", raw);
    }

    public int getY() {
        return (int) Reflection.value("BoundaryObject#getY", raw);
    }
}
