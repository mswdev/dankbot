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

    public Object getActionType() {
        return Reflection.value("Widget#getActionType", raw);
    }

    public Object getActions() {
        return Reflection.value("Widget#getActions", raw);
    }

    public Object getBorderThickness() {
        return Reflection.value("Widget#getBorderThickness", raw);
    }

    public Object getBoundsIndex() {
        return Reflection.value("Widget#getBoundsIndex", raw);
    }

    public Object getChildTextureId() {
        return Reflection.value("Widget#getChildTextureId", raw);
    }

    public Object getChildren() {
        return Reflection.value("Widget#getChildren", raw);
    }

    public Object getComponentIndex() {
        return Reflection.value("Widget#getComponentIndex", raw);
    }

    public Object getDisabledMediaId() {
        return Reflection.value("Widget#getDisabledMediaId", raw);
    }

    public Object getDisabledMediaType() {
        return Reflection.value("Widget#getDisabledMediaType", raw);
    }

    public Object getDynamicValue() {
        return Reflection.value("Widget#getDynamicValue", raw);
    }

    public Object getEnabledMediaId() {
        return Reflection.value("Widget#getEnabledMediaId", raw);
    }

    public Object getEnabledMediaType() {
        return Reflection.value("Widget#getEnabledMediaType", raw);
    }

    public Object getHeight() {
        return Reflection.value("Widget#getHeight", raw);
    }

    public Object isHidden() {
        return Reflection.value("Widget#isHidden", raw);
    }

    public Object getId() {
        return Reflection.value("Widget#getId", raw);
    }

    public Object getItemId() {
        return Reflection.value("Widget#getItemId", raw);
    }

    public Object getItemStackSize() {
        return Reflection.value("Widget#getItemStackSize", raw);
    }

    public Object getLoopCycle() {
        return Reflection.value("Widget#getLoopCycle", raw);
    }

    public Object getName() {
        return Reflection.value("Widget#getName", raw);
    }

    public Object getParent() {
        return Reflection.value("Widget#getParent", raw);
    }

    public Object getParentId() {
        return Reflection.value("Widget#getParentId", raw);
    }

    public Object getScrollMax() {
        return Reflection.value("Widget#getScrollMax", raw);
    }

    public Object getScrollX() {
        return Reflection.value("Widget#getScrollX", raw);
    }

    public Object getScrollY() {
        return Reflection.value("Widget#getScrollY", raw);
    }

    public Object getSelectedAction() {
        return Reflection.value("Widget#getSelectedAction", raw);
    }

    public Object getShadowColor() {
        return Reflection.value("Widget#getShadowColor", raw);
    }

    public Object getSlotIds() {
        return Reflection.value("Widget#getSlotIds", raw);
    }

    public Object getSlotStackSizes() {
        return Reflection.value("Widget#getSlotStackSizes", raw);
    }

    public Object getSpell() {
        return Reflection.value("Widget#getSpell", raw);
    }

    public Object getSpriteId() {
        return Reflection.value("Widget#getSpriteId", raw);
    }

    public Object getText() {
        return Reflection.value("Widget#getText", raw);
    }

    public Object getTextColor() {
        return Reflection.value("Widget#getTextColor", raw);
    }

    public Object getTextureId() {
        return Reflection.value("Widget#getTextureId", raw);
    }

    public Object getTooltip() {
        return Reflection.value("Widget#getTooltip", raw);
    }

    public Object getType() {
        return Reflection.value("Widget#getType", raw);
    }

    public Object getWidth() {
        return Reflection.value("Widget#getWidth", raw);
    }

    public Object getAbsoluteX() {
        return Reflection.value("Widget#getAbsoluteX", raw);
    }

    public Object getAbsoluteY() {
        return Reflection.value("Widget#getAbsoluteY", raw);
    }

}
