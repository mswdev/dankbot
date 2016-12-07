package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class Region {

    private Object raw;

    public Region(Object raw) {
        this.raw = raw;
    }


    public Object getFocusedX() {
        return Reflection.value("Region#getFocusedX", raw);
    }

    public Object getFocusedY() {
        return Reflection.value("Region#getFocusedY", raw);
    }

    public Object getGameObjects() {
        return Reflection.value("Region#getGameObjects", raw);
    }

    public Object getTiles() {
        return Reflection.value("Region#getTiles", raw);
    }

    public Object getVisibilityTilesMap() {
        return Reflection.value("Region#getVisibilityTilesMap", raw);
    }

    public Object getVisibleTiles() {
        return Reflection.value("Region#getVisibleTiles", raw);
    }

}
