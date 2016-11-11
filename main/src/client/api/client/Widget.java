package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/7/2016.
 */
public class Widget {

    private Object raw;

    public Widget(Object raw) {
        this.raw = raw;
    }

    public int getAbsoluteX() {
        return (int) Reflection.value("fw", "o") * 586897191;
    }

    public int getAbsoluteY() {
        return (int) Reflection.value("fw", "aq") * -259877851;
    }

    public int getBoundsIndex() {
        return (int) Reflection.value("fw", "ep") * -1763898913;
    }

    public Widget[] getChildren() {
        return (Widget[]) Reflection.value("fw", "et");
    }

    public int getHeight() {
        return (int) Reflection.value("fw", "av") * 967899867;
    }

    public boolean isHidden() {
        return (boolean) Reflection.value("fw", "am");
    }

    public int getId() {
        return (int) Reflection.value("fw", "g") * -241295795;
    }

    public int getItemId() {
        return (int) Reflection.value("fw", "ej") * -2041279403;
    }

    public int[] getItemIds() {
        return (int[]) Reflection.value("fw", "ei");
    }

    public int[] getItemQuantities() {
        return (int[]) Reflection.value("fw", "eg");
    }

    public int getItemQuantity() {
        return (int) Reflection.value("fw", "ed") * 1744754321;
    }

    public String getName() {
        return (String) Reflection.value("fw", "ci");
    }

    public Widget getParent() {
        return (Widget) Reflection.value("fw", "cm");
    }

    public int getParentId() {
        return (int) Reflection.value("fw", "aj") * -363661115;
    }

    public int getRelativeX() {
        return (int) Reflection.value("fw", "ay") * 231741105;
    }

    public int getRelativeY() {
        return (int) Reflection.value("fw", "ag") * -976102605;
    }

    public String getText() {
        return (String) Reflection.value("fw", "ba");
    }

    public int getTextureId() {
        return (int) Reflection.value("fw", "ae") * -1321943861;
    }

    public int getWidth() {
        return (int) Reflection.value("fw", "al") * 571183135;
    }
}
