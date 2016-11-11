package client.api.client;

import client.api.util.Logging;
import client.reflection.Reflection;

import java.applet.Applet;
import java.awt.*;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Client {

    public static Applet getInstance() {
        try {
            return (Applet) Reflection.loadClass("client").newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Logging.error(e.getMessage());
            return null;
        }
    }

    public static Canvas getCanvas() {
        return (Canvas) Reflection.value("Client#getCanvas");
    }

    public static void setCanvas(Canvas canvas) {
        try {
            Reflection.field("Client#getCanvas").field.set(null, canvas);
        } catch (IllegalAccessException e) {
            Logging.error(e.getMessage());
        }
    }

    public static int getGameState() {
        return (int) Reflection.value("Client#getGameState");
    }

    public static int getCameraX() {
        return (int) Reflection.value("Client#getCameraX");
    }

    public static int getCameraY() {
        return (int) Reflection.value("Client#getCameraY");
    }

    public static int getCameraZ() {
        return (int) Reflection.value("Client#getCameraZ");
    }

    public static int getBaseX() {
        return (int) Reflection.value("Client#getBaseX");
    }

    public static int getBaseY() {
        return (int) Reflection.value("Client#getBaseY");
    }

    public static int getCameraYaw() {
        return (int) Reflection.value("Client#getCameraYaw");
    }

    public static int getCameraPitch() {
        return (int) Reflection.value("Client#getCameraPitch");
    }

    public static int getPlane() {
        return (int) Reflection.value("Client#getPlane");
    }

    public static int[] getCurrentLevels() {
        return (int[]) Reflection.value("Client#getCurrentLevels");
    }

    public static int[] getActualLevels() {
        return (int[]) Reflection.value("Client#getActualLevels");
    }

    public static Player getLocalPlayer() {
        return new Player(Reflection.value("Client#getLocalPlayer"));
    }

    public static Player[] getPlayers() {
        return Stream.of(Reflection.value("Client#getPlayers")).map(Player::new).toArray(Player[]::new);
    }

    public static LinkedList getGroundItems() {
        return (LinkedList) Reflection.value("Client#getGroundItems", null);
    }

    public static NPC[] getNPCs() {
        return Stream.of(Reflection.value("Client#getNPCs", null)).map(NPC::new).toArray(NPC[]::new);
    }

    public static int getViewPortScale() {
        return (int) Reflection.value("Client#getViewPortScale", null);
    }

    public static int getViewPortHeight() {
        return (int) Reflection.value("Client#getViewPortHeight", null);
    }

    public static int getViewPortWidth() {
        return (int) Reflection.value("Client#getViewPortWidth", null);
    }

    public static int[][][] getTileHeights() {
        return (int[][][]) Reflection.value("Client#getTileHeights", null);
    }

    public static byte[][][] getTileSettings() {
        return (byte[][][]) Reflection.value("Client#getTileSettings", null);
    }

    public static int getMapAngle() {
        return (int) Reflection.value("Client#getMapAngle", null);
    }

    public static int getMapOffset() {
        return (int) Reflection.value("Client#getMapOffset", null);
    }

    public static int getMapScale() {
        return (int) Reflection.value("Client#getMapScale", null);
    }

    public static boolean isResizable() {
        return (boolean) Reflection.value("Client#isResizable", null);
    }

    public static int getAppletWidth() {
        return (int) Reflection.value("Client#getAppletWidth", null);
    }

    public static int getAppletHeight() {
        return (int) Reflection.value("Client#", null);
    }

    public static Cache getPlayerModelCache() {
        return new Cache(Reflection.value("gs", "b", null));
    }
}
