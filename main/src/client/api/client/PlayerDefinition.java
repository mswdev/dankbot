package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/9/2016.
 */
public class PlayerDefinition {

    private Object raw;

    public PlayerDefinition(Object raw) {
        this.raw = raw;
    }

    public long getAnimatedModelId() {
        return (long) Reflection.value("PlayerDefinition#getAnimatedModelId", raw);
    }

    public int[] getAppearance() {
        return (int[]) Reflection.value("PlayerDefinition#getAppearance", raw);
    }

    public int[] getBodyColors() {
        return (int[]) Reflection.value("PlayerDefinition#getBodyColors", raw);
    }

    public boolean isFemale() {
        return (boolean) Reflection.value("PlayerDefinition#isFemale", raw);
    }

    /*public int getNPCId() {
        return (int) Reflection.value("PlayerComposite#npcID", raw);
    }*/

    public long getStaticModelId() {
        return (long) Reflection.value("PlayerDefinition#getStaticModelId", raw);
    }

    public boolean isValid() {
        return raw != null;
    }

}
