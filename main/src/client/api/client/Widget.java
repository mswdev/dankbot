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
        return (int) Reflection.value("Widget#getAbsoluteY");
    }

    public int getBoundsIndex() {
        return (int) Reflection.value("Widget#getBoundsIndex");
    }

    public Widget[] getChildren() {
        return (Widget[]) Reflection.value("Widget#getChildren");
    }

    public int getHeight() {
        return (int) Reflection.value("Widget#getHeight");
    }

    public boolean isHidden() {
        return (boolean) Reflection.value("Widget#isHidden");
    }

    public int getId() {
        return (int) Reflection.value("Widget#getId");
    }

    public int getItemId() {
        return (int) Reflection.value("Widget#getItemId");
    }

    public int[] getItemIds() {
        return (int[]) Reflection.value("Widget#getItemIds");
    }

    public int[] getItemQuantities() {
        return (int[]) Reflection.value("Widget#getItemQuantities");
    }

    public int getItemQuantity() {
        return (int) Reflection.value("Widget#getItemQuantity");
    }

    public String getName() {
        return (String) Reflection.value("Widget#getName");
    }

    public Widget getParent() {
        return (Widget) Reflection.value("Widget#getParent");
    }

    public int getParentId() {
        return (int) Reflection.value("Widget#getParentId");
    }

    public int getRelativeX() {
        return (int) Reflection.value("Widget#getRelativeX");
    }

    public int getRelativeY() {
        return (int) Reflection.value("Widget#getRelativeY");
    }

    public String getText() {
        return (String) Reflection.value("Widget#getText");
    }

    public int getTextureId() {
        return (int) Reflection.value("Widget#getTextureId");
    }

    public int getWidth() {
        return (int) Reflection.value("Widget#getWidth");
    }
}
