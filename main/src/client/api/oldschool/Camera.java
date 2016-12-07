package client.api.oldschool;

import client.api.client.Client;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Camera {

    /**
     * Gets the X position of the camera.
     *
     * @return The X position of the camera.
     */
    public static int getCameraX() {
        return Client.getCameraX();
    }

    /**
     * Gets the Y position of the camera.
     *
     * @return The Y position of the camera.
     */
    public static int getCameraY() {
        return Client.getCameraY();
    }

    /**
     * Gets the Z position of the camera.
     *
     * @return The Z position of the camera.
     */
    public static int getCameraZ() {
        return Client.getCameraZ();
    }

    /**
     * Gets the yaw of the camera.
     *
     * @return The yaw of the camera.
     */
    public static int getCameraYaw() {
        return Client.getCameraYaw();
    }

    /**
     * Gets the pitch of the camera.
     *
     * @return The pitch of the camera.
     */
    public static int getCameraAngle() {
        final int PITCH = (int) ((Client.getCameraPitch() - 128) / 2.83);
        return PITCH < 0 ? 0 : PITCH;
    }

    /**
     * Gets the pitch of the camera.
     *
     * @return The pitch of the camera.
     */
    public static int getCameraPitch() {
        return Client.getCameraPitch();
    }

    /**
     * Gets the rotation of the camera.
     *
     * @return The rotation of the camera.
     */
    public static int getCameraRotation() {
        return Math.abs((int) (getCameraYaw() / 5.68) - 360);
    }

    /**
     * Gets the view port width.
     *
     * @return The width of the view port.
     */
    public static int getViewPortWidth() {
        return Client.getViewPortWidth();
    }

    /**
     * Gets the view port height.
     *
     * @return The height of the view port.
     */
    public static int getViewPortHeight() {
        return Client.getViewPortHeight();
    }

    /**
     * Gets the view port scale.
     *
     * @return The scale of the view port.
     */
    public static int getViewPortScale() {
        return Client.getViewPortScale();
    }

}
