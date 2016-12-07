package client.api.client;

import client.reflection.Reflection;

import java.awt.*;
import java.util.stream.Stream;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Client {

    public static void setCanvas(Canvas canvas) {
        try {
            Reflection.field("Client#getCanvas").field.set(null, canvas);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static int getBaseX() {
        return (int) Reflection.value("Client#getBaseX");
    }

    public static int getBaseY() {
        return (int) Reflection.value("Client#getBaseY");
    }

    public static int getCameraPitch() {
        return (int) Reflection.value("Client#getCameraPitch");
    }

    public static int getCameraX() {
        return (int) Reflection.value("Client#getCameraX");
    }

    public static int getCameraY() {
        return (int) Reflection.value("Client#getCameraY");
    }

    public static int getCameraYaw() {
        return (int) Reflection.value("Client#getCameraYaw");
    }

    public static int getCameraZ() {
        return (int) Reflection.value("Client#getCameraZ");
    }

    public static Canvas getCanvas() {
        return (Canvas) Reflection.value("Client#getCanvas");
    }

    public static ClanMember[] getClanMembers() {
        return Stream.of(Reflection.value("Client#getClanMembers")).map(ClanMember::new).toArray(ClanMember[]::new);
    }

    public static int getClanMembersSize() {
        return (int) Reflection.value("Client#getClanMembersSize");
    }

    public static String getClanName() {
        return (String) Reflection.value("Client#getClanName");
    }

    public static String getClanOwner() {
        return (String) Reflection.value("Client#getClanOwner");
    }

    public static Object getClickModifier() {
        return Reflection.value("Client#getClickModifier");
    }

    public static int[][][] getCollisionMaps() {
        return (int[][][]) Reflection.value("Client#getCollisionMaps");
    }

    public static Object getCrosshairColor() {
        return Reflection.value("Client#getCrosshairColor");
    }

    public static int getCurrentWorld() {
        return (int) Reflection.value("Client#getCurrentWorld");
    }

    public static int getDestinationX() {
        return (int) Reflection.value("Client#getDestinationX");
    }

    public static int getDestinationY() {
        return (int) Reflection.value("Client#getDestinationY");
    }

    public static int[] getExperience() {
        return (int[]) Reflection.value("Client#getExperience");
    }

    public static Friend[] getFriends() {
        return Stream.of(Reflection.value("Client#getFriends")).map(Friend::new).toArray(Friend[]::new);
    }

    public static int getFriendsSize() {
        return (int) Reflection.value("Client#getFriendsSize");
    }

    public static int getGameCycle() {
        return (int) Reflection.value("Client#getGameCycle");
    }

    public static int[] getGameSettings() {
        return (int[]) Reflection.value("Client#getGameSettings");
    }

    public static Object getGameSocket() {
        return Reflection.value("Client#getGameSocket");
    }

    public static int getGameState() {
        return (int) Reflection.value("Client#getGameState");
    }

    public static GrandExchangeItem[] getGrandExchangeItems() {
        return Stream.of(Reflection.value("Client#getGrandExchangeItems")).map(GrandExchangeItem::new).toArray(GrandExchangeItem[]::new);
    }

    public static Object getGroundItemList() {
        return Reflection.value("Client#getGroundItemList");
    }

    public static Cache getGroundItemModelCache() {
        return new Cache(Reflection.value("Client#getGroundItemModelCache"));
    }

    public static Object getHost() {
        return Reflection.value("Client#getHost");
    }

    public static long getIdleTime() {
        return (long) Reflection.value("Client#getIdleTime");
    }

    public static boolean getIsSpellSelected() {
        return (boolean) Reflection.value("Client#getIsSpellSelected");
    }

    public static boolean getIsWorldSelectorOpen() {
        return (boolean) Reflection.value("Client#getIsWorldSelectorOpen");
    }

    public static Object getItemTable() {
        return Reflection.value("Client#getItemTable");
    }

    public static Object getKeyboard() {
        return Reflection.value("Client#getKeyboard");
    }

    public static int[] getLevel() {
        return (int[]) Reflection.value("Client#getLevel");
    }

    public static NPC[] getNPCs() {
        return Stream.of(Reflection.value("Client#getNPCs")).map(NPC::new).toArray(NPC[]::new);
    }

    public static Player getLocalPlayer() {
        return new Player(Reflection.value("Client#getLocalPlayer"));
    }

    public static int getLoginState() {
        return (int) Reflection.value("Client#getLoginState");
    }

    public static int getLowestAvailableCameraPitch() {
        return (int) Reflection.value("Client#getLowestAvailableCameraPitch");
    }

    public static int getMapAngle() {
        return (int) Reflection.value("Client#getMapAngle");
    }

    public static int getMapOffset() {
        return (int) Reflection.value("Client#getMapOffset");
    }

    public static int getMapScale() {
        return (int) Reflection.value("Client#getMapScale");
    }

    public static String[] getMenuActionNames() {
        return (String[]) Reflection.value("Client#getMenuActionNames");
    }

    public static String[] getMenuActions() {
        return (String[]) Reflection.value("Client#getMenuActions");
    }

    public static int getMenuCount() {
        return (int) Reflection.value("Client#getMenuCount");
    }

    public static int getMenuHeight() {
        return (int) Reflection.value("Client#getMenuHeight");
    }

    public static Object getMenuOpcodes() {
        return Reflection.value("Client#getMenuOpcodes");
    }

    public static String[] getMenuOptions() {
        return (String[]) Reflection.value("Client#getMenuOptions");
    }

    public static Object getMenuVariable() {
        return Reflection.value("Client#getMenuVariable");
    }

    public static boolean getMenuVisible() {
        return (boolean) Reflection.value("Client#getMenuVisible");
    }

    public static int getMenuWidth() {
        return (int) Reflection.value("Client#getMenuWidth");
    }

    public static int getMenuX() {
        return (int) Reflection.value("Client#getMenuX");
    }

    public static int[] getMenuXInteractions() {
        return (int[]) Reflection.value("Client#getMenuXInteractions");
    }

    public static int getMenuY() {
        return (int) Reflection.value("Client#getMenuY");
    }

    public static int[] getMenuYInteractions() {
        return (int[]) Reflection.value("Client#getMenuYInteractions");
    }

    public static String getMessage0() {
        return (String) Reflection.value("Client#getMessage0");
    }

    public static String getMessage1() {
        return (String) Reflection.value("Client#getMessage1");
    }

    public static String getMessage2() {
        return (String) Reflection.value("Client#getMessage2");
    }

    public static Object getMessageContainer() {
        return Reflection.value("Client#getMessageContainer");
    }

    public static Object getMouse() {
        return Reflection.value("Client#getMouse");
    }

    public static Cache getNpcDefinitionCache() {
        return new Cache(Reflection.value("Client#getNpcDefinitionCache"));
    }

    public static Cache getNpcModelCache() {
        return new Cache(Reflection.value("Client#getNpcModelCache"));
    }

    public static Cache getObjectDefinitionCache() {
        return new Cache(Reflection.value("Client#getObjectDefinitionCache"));
    }

    public static Cache getObjectModelCache() {
        return new Cache(Reflection.value("Client#getObjectModelCache"));
    }

    public static String getPassword() {
        return (String) Reflection.value("Client#getPassword");
    }

    public static int getPlane() {
        return (int) Reflection.value("Client#getPlane");
    }

    public static int getPlayerIndex() {
        return (int) Reflection.value("Client#getPlayerIndex");
    }

    public static Cache getPlayerModelCache() {
        return new Cache(Reflection.value("Client#getPlayerModelCache"));
    }

    public static Player[] getPlayers() {
        return Stream.of(Reflection.value("Client#getPlayers")).map(Player::new).toArray(Player[]::new);
    }

    public static Projectile[] getProjectiles() {
        return Stream.of(Reflection.value("Client#getProjectiles")).map(Projectile::new).toArray(Projectile[]::new);
    }

    public static int[] getRealLevel() {
        return (int[]) Reflection.value("Client#getRealLevel");
    }

    public static Region getRegion() {
        return new Region(Reflection.value("Client#getRegion"));
    }

    public static int getSelectedItemId() {
        return (int) Reflection.value("Client#getSelectedItemId");
    }

    public static int getSelectedItemIndex() {
        return (int) Reflection.value("Client#getSelectedItemIndex");
    }

    public static String getSelectedItemName() {
        return (String) Reflection.value("Client#getSelectedItemName");
    }

    public static String getSelectedSpellName() {
        return (String) Reflection.value("Client#getSelectedSpellName");
    }

    public static int getSelectionState() {
        return (int) Reflection.value("Client#getSelectionState");
    }

    public static int[] getSettings() {
        return (int[]) Reflection.value("Client#getSettings");
    }

    public static SettingsClass getSettingsObject() {
        return new SettingsClass(Reflection.value("Client#getSettingsObject"));
    }

    public static int[][][] getTileHeights() {
        return (int[][][]) Reflection.value("Client#getTileHeights");
    }

    public static byte[][][] getTileSettings() {
        return (byte[][][]) Reflection.value("Client#getTileSettings");
    }

    public static String getUsername() {
        return (String) Reflection.value("Client#getUsername");
    }

    public static int getWidgetBoundsX() {
        return (int) Reflection.value("Client#getWidgetBoundsX");
    }

    public static int getWidgetBoundsY() {
        return (int) Reflection.value("Client#getWidgetBoundsY");
    }

    public static int[] getWidgetHeights() {
        return (int[]) Reflection.value("Client#getWidgetHeights");
    }

    public static Cache getWidgetModelCache() {
        return (Cache) Reflection.value("Client#getWidgetModelCache");
    }

    public static WidgetNode[] getWidgetNodes() {
        return Stream.of(Reflection.value("Client#getWidgetNodes")).map(WidgetNode::new).toArray(WidgetNode[]::new);
    }

    public static int[] getWidgetWidths() {
        return (int[]) Reflection.value("Client#getWidgetWidths");
    }

    public static Widget[] getWidgets() {
        return Stream.of(Reflection.value("Client#getWidgets")).map(Widget::new).toArray(Widget[]::new);
    }

    public static World[] getWorlds() {
        return Stream.of(Reflection.value("Client#getWorlds")).map(World::new).toArray(World[]::new);
    }

    public static int getViewPortScale() {
        return (int) Reflection.value("Client#getViewPortScale");
    }

    public static int getViewPortHeight() {
        return (int) Reflection.value("Client#getViewPortHeight");
    }

    public static int getViewPortWidth() {
        return (int) Reflection.value("Client#getViewPortWidth");
    }

    public static boolean isResizable() {
        return (boolean) Reflection.value("Client#isResizable");
    }

    public static int getAppletHeight() {
        return (int) Reflection.value("Client#getAppletHeight");
    }

    public static int getAppletWidth() {
        return (int) Reflection.value("Client#getAppletWidth");
    }
}
