package client.api.client;

import client.api.oldschool.interfaces.Validatable;
import client.reflection.Reflection;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Character implements Validatable {

    private Object raw;

    public Character(Object raw) {
        this.raw = raw;
    }

    public int getAnimation() {
        return (int) Reflection.value("Character#getAnimation", raw);
    }

    public int getCombatTime() {
        return (int) Reflection.value("Character#getCombatTime", raw);
    }

    public int getFrameOne() {
        return (int) Reflection.value("Character#getFrameOne", raw);
    }

    public int getFrameTwo() {
        return (int) Reflection.value("Character#getFrameTwo", raw);
    }

    public Object getHitCycles() {
        return Reflection.value("Character#getHitCycles", raw);
    }

    public Object getHitDamages() {
        return (int) Reflection.value("Character#getHitDamages", raw);
    }

    public Object getHitTypes() {
        return (int) Reflection.value("Character#getHitTypes", raw);
    }

    public Object getHealthBars() {
        return (int) Reflection.value("Character#getHealthBars", raw);
    }

    public int getInteractingIndex() {
        return (int) Reflection.value("Character#getInteractingIndex", raw);
    }

    public int getX() {
        return (int) Reflection.value("Character#getX", raw) >> 7;
    }

    public int getY() {
        return (int) Reflection.value("Character#getY", raw) >> 7;
    }

    public String getMessage() {
        return (String) Reflection.value("Character#getMessage", raw);
    }

    public int getOrientation() {
        return (int) Reflection.value("Character#getOrientation", raw);
    }

    public int getQueueSize() {
        return (int) Reflection.value("Character#getQueueSize", raw);
    }

    public Object getQueueTraversed() {
        return Reflection.value("Character#getQueueTraversed", raw);
    }

    public int[] getQueueX() {
        return (int[]) Reflection.value("Character#getQueueX", raw);
    }

    public int[] getQueueY() {
        return (int[]) Reflection.value("Character#getQueueY", raw);
    }

    public int getRuntimeAnimation() {
        return (int) Reflection.value("Character#getRuntimeAnimation", raw);
    }

    public int getStandAnimation() {
        return (int) Reflection.value("Character#getStandAnimation", raw);
    }

    public boolean isValid() {
        return raw != null;
    }
}
