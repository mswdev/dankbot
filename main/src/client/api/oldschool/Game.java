package client.api.oldschool;

import client.api.client.Client;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class Game {

    /**
     * Gets the map region's base X coordinate.
     *
     * @return The map region's base X coordinate.
     */
    public static int getBaseX() {
        return Client.getBaseX();
    }

    /**
     * Gets the map region's base Y coordinate.
     *
     * @return The map region's base Y coordinate.
     */
    public static int getBaseY() {
        return Client.getBaseY();
    }

    /**
     * Gets the getCurrent client game state.
     *
     * @return The client game state.
     */
    public static int getGameState() {
        return Client.getGameState();
    }

    /**
     * Gets the getCurrent client plane.
     *
     * @return The client plane.
     */
    public static int getPlane() {
        return Client.getPlane();
    }

    /**
     * Checks if the RSPlayer is logged in.
     *
     * @return True if the RSPlayer is logged in; false otherwise.
     * */
    public static boolean isLoggedIn() {
        return getGameState() == 30;
    }

}

