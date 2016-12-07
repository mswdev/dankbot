package client.api.oldschool.wrappers;

import client.api.client.Client;
import client.api.oldschool.Game;
import client.api.oldschool.interfaces.Positionable;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class RSTile implements Positionable {

    private int x;
    private int y;
    private int plane;
    private TYPES type;

    public enum TYPES {
        NORMAL,
        LOCAL,
        WORLD,
        ANIMATED
    }

    public RSTile(int x, int y, int plane, TYPES type) {
        this.x = x;
        this.y = y;
        this.plane = plane;
        this.type = type;
    }

    public RSTile(int x, int y, TYPES type) {
        this.x = x;
        this.y = y;
        this.plane = Game.getPlane();
        this.type = type;
    }

    public RSTile(int x, int y, int plane) {
        this.x = x;
        this.y = y;
        this.plane = plane;
        this.type = TYPES.NORMAL;
    }

    public RSTile(int x, int y) {
        this.x = x;
        this.y = y;
        this.plane = Game.getPlane();
        this.type = TYPES.NORMAL;
    }

    /**
     * Gets a String containing the RSTile positions.
     *
     * @return A String containing the RSTile positions.
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + plane + "]";
    }

    /**
     * Gets the position of the RSTile.
     *
     * @return The position of the RSTile.
     * */
    @Override
    public RSTile getPosition() {
        return this;
    }

    /**
     * Gets the RSTile's plane.
     *
     * @return The RSTile's plane.
     */
    public int getPlane() {
        return plane;
    }

    /**
     * Gets the RSTile's X position.
     *
     * @return The RSTile's X position.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the RSTile's Y position.
     *
     * @return The RSTile's Y position.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the world tile of the RSTile.
     *
     * @return The world tile of the RSTile.
     * */
    public RSTile getWorldTile() {
        switch (type) {
            case ANIMATED:
                return new RSTile(x - 64, y - 64, plane, TYPES.WORLD);
            case WORLD:
                return this;
            case LOCAL:
                return new RSTile((int) (x) * 128, (int) (y) * 128, plane, TYPES.WORLD);
            default:
                return new RSTile((int) (x - Client.getBaseX()) * 128, (int) (y - Client.getBaseY()) * 128, plane, TYPES.WORLD);
        }
    }

    /**
     * Gets the animated tile of the RSTile.
     *
     * @return The animated tile of the RSTile.
     * */
    public RSTile getAnimatedTile() {
        switch (type) {
            case ANIMATED:
                return this;
            case WORLD:
                return new RSTile(x + 64, y + 64, plane, TYPES.ANIMATED);
            case LOCAL:
                return new RSTile((int) (x + 0.5) * 128, (int) (y + 0.5) * 128, plane, TYPES.WORLD);
            default:
                return new RSTile((int) ((x - Client.getBaseX() + 0.5) * 128), (int) ((y - Client.getBaseY() + 0.5) * 128), plane, TYPES.WORLD);
        }
    }

    /**
     * Gets the local tile of the RSTile.
     *
     * @return The local tile of the RSTile.
     * */
    public RSTile getLocalTile() {
        switch (type) {
            case ANIMATED:
                return new RSTile(((x - 64) / 128), ((y - 64) / 128), plane, TYPES.LOCAL);
            case WORLD:
                return new RSTile((x / 128), (y / 128), plane, TYPES.LOCAL);
            case NORMAL:
                return new RSTile(x - Client.getBaseX(), y - Client.getBaseY(), plane, TYPES.LOCAL);
            default:
                return this;
        }
    }

    /**
     * Gets the type of RSTile.
     * Types can consist of: "NORMAL, LOCAL, WORLD, ANIMATED"
     *
     * @return The type of RSTile.
     * */
    public TYPES getType() {
        return type;
    }
}