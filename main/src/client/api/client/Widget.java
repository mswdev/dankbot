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
        return (int) Reflection.value("Widget#getAbsoluteX");
    }

    public int getAbsoluteY() {
        return (int) Reflection.value("Widget#");
    }

    public int getBoundsIndex() {
        return (int) Reflection.value("Widget#");
    }

    public Widget[] getChildren() {
        return (Widget[]) Reflection.value("fw", "et");
    }

    public int getHeight() {
        return (int) Reflection.value("fw", "av");
    }

    public boolean isHidden() {
        return (boolean) Reflection.value("fw", "am");
    }

    public int getId() {
        return (int) Reflection.value("fw", "g");
    }

    public int getItemId() {
        return (int) Reflection.value("fw", "ej");
    }

    public int[] getItemIds() {
        return (int[]) Reflection.value("fw", "ei");
    }

    public int[] getItemQuantities() {
        return (int[]) Reflection.value("fw", "eg");
    }

    public int getItemQuantity() {
        return (int) Reflection.value("fw", "ed");
    }

    public String getName() {
        return (String) Reflection.value("fw", "ci");
    }

    public Widget getParent() {
        return (Widget) Reflection.value("fw", "cm");
    }

    public int getParentId() {
        return (int) Reflection.value("fw", "aj");
    }

    public int getRelativeX() {
        return (int) Reflection.value("fw", "ay");
    }

    public int getRelativeY() {
        return (int) Reflection.value("fw", "ag");
    }

    public String getText() {
        return (String) Reflection.value("fw", "ba");
    }

    public int getTextureId() {
        return (int) Reflection.value("fw", "ae");
    }

    public int getWidth() {
        return (int) Reflection.value("fw", "al");
    }
}
