package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Character {

    private Object raw;

    public Character(Object raw) {
        this.raw = raw;
    }

    public int getAnimiation() {
        return (int) Reflection.value("Character#getAnimation", raw);
    }

    public int getX() {
        return (int) Reflection.value("Character#getX", raw) >> 7;
    }

    public int getY() {
        return (int) Reflection.value("Character#getY", raw) >> 7;
    }

    public int getInteractingIndex() {
        return (int) Reflection.value("Character#getInteractingIndex", raw);
    }

    public boolean isValid() {
        return raw != null;
    }

    public int getOrientation() {
        return (int) Reflection.value("Character#getOrientation", raw);
    }

}
