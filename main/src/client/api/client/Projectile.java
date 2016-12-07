package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class Projectile {

    private Object raw;

    public Projectile(Object raw) {
        this.raw = raw;
    }

    public Object getHeightOffset() {
        return Reflection.value("Projectile#getHeightOffset", raw);
    }
    
    public Object getMoving() {
        return Reflection.value("Projectile#getMoving", raw);
    }

    public Object getRotationX() {
        return Reflection.value("Projectile#getRotationX", raw);
    }

    public Object getRotationY() {
        return Reflection.value("Projectile#getRotationY", raw);
    }

    public Object getSpeedX() {
        return Reflection.value("Projectile#getSpeedX", raw);
    }

    public Object getSpeedY() {
        return Reflection.value("Projectile#getSpeedY", raw);
    }

    public Object getSpeedZ() {
        return Reflection.value("Projectile#getSpeedZ", raw);
    }

    public Object getX() {
        return Reflection.value("Projectile#getX", raw);
    }

    public Object getY() {
        return Reflection.value("Projectile#getY", raw);
    }

}
