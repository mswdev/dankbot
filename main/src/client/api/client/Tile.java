package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class Tile {

    private Object raw;

    public Tile(Object raw) {
        this.raw = raw;
    }

    public Object getBoundary() {
        return Reflection.value("Tile#getBoundary", raw);
    }

    public Object getFloor() {
        return Reflection.value("Tile#getFloor", raw);
    }

    public Object getItemLayer() {
        return Reflection.value("Tile#getItemLayer", raw);
    }

    public Object getObjects() {
        return Reflection.value("Tile#getObjects", raw);
    }

    public Object getPlane() {
        return Reflection.value("Tile#getPlane", raw);
    }

    public Object getWall() {
        return Reflection.value("Tile#getWall", raw);
    }

    public Object getX() {
        return Reflection.value("Tile#getX", raw);
    }

    public Object getY() {
        return Reflection.value("Tile#getY", raw);
    }

}
