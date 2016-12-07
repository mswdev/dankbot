package client.api.client;

import client.api.oldschool.interfaces.Validatable;
import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class AnimatedObject implements Validatable {

    private Object raw;

    public AnimatedObject(Object raw) {
        this.raw = raw;
    }

    public int getAnimationDelay() {
        return (int) Reflection.value("AnimatedObject#getAnimationDelay", raw);
    }

    public int getAnimationFrame() {
        return (int) Reflection.value("AnimatedObject#getAnimationFrame", raw);
    }

    public Object getClickType() {
        return Reflection.value("AnimatedObject#getClickType", raw);
    }

    public int getId() {
        return (int) Reflection.value("AnimatedObject#getId", raw);
    }

    public int getOrientation() {
        return (int) Reflection.value("AnimatedObject#getOrientation", raw);
    }

    public int getPlane() {
        return (int) Reflection.value("AnimatedObject#getPlane", raw);
    }

    public Object getSequence() {
        return Reflection.value("AnimatedObject#getSequence", raw);
    }

    public int getX() {
        return (int) Reflection.value("AnimatedObject#getX", raw) >> 7;
    }

    public int getY() {
        return (int) Reflection.value("AnimatedObject#getY", raw) >> 7;
    }

    @Override
    public boolean isValid() {
        return raw != null;
    }
}
