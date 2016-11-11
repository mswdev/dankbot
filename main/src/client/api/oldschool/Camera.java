package client.api.oldschool;

import client.api.client.Client;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Camera {

    /**
     * Gets the getCurrent X position of the camera.
     *
     * @return The X position of the camera.
     */
    public static int getCameraX() {
        return Client.getCameraX();
    }

    /**
     * Gets the getCurrent Y position of the camera.
     *
     * @return The Y position of the camera.
     */
    public static int getCameraY() {
        return Client.getCameraY();
    }

    /**
     * Gets the getCurrent Z position of the camera.
     *
     * @return The Z position of the camera.
     */
    public static int getCameraZ() {
        return Client.getCameraZ();
    }

    /**
     * Gets the getCurrent yaw of the camera.
     *
     * @return The getCurrent yaw of the camera.
     */
    public static int getCameraYaw() {
        return Client.getCameraYaw();
    }

    /**
     * Gets the getCurrent pitch of the camera.
     *
     * @return The getCurrent pitch of the camera.
     */
    public static int getCameraAngle() {
        int pitch = (int) ((Client.getCameraPitch() - 128) / 2.83);
        return pitch < 0 ? 0 : pitch;
    }

    public static int getCameraPitch() {
        return Client.getCameraPitch();
    }

    public static int getCameraRotation() {
        return Math.abs((int) (getCameraYaw() / 5.68) - 360);
    }

    public static int getViewPortWidth() {
        return Client.getViewPortWidth();
    }

    public static int getViewPortHeight() {
        return Client.getViewPortHeight();
    }

    public static int getViewPortScale() {
        return Client.getViewPortScale();
    }

}
