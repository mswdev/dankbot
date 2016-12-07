package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class ItemLayer {

    private Object raw;

    public ItemLayer(Object raw) {
        this.raw = raw;
    }

    public Object getBottom() {
        return Reflection.value("ItemLayer#getBottom", raw);
    }

    public Object getFlag() {
        return Reflection.value("ItemLayer#getFlag", raw);
    }

    public Object getHeight() {
        return Reflection.value("ItemLayer#getHeight", raw);
    }

    public Object getMiddle() {
        return Reflection.value("ItemLayer#getMiddle", raw);
    }

    public Object getPlane() {
        return Reflection.value("ItemLayer#getPlane", raw);
    }

    public Object getTop() {
        return Reflection.value("ItemLayer#getTop", raw);
    }

    public Object getX() {
        return Reflection.value("ItemLayer#getX", raw);
    }

    public Object getY() {
        return Reflection.value("ItemLayer#getY", raw);
    }

}
