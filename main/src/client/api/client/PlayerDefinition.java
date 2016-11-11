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
        return (long) Reflection.value("gs", "v", raw) * 4771388128559774409L;
    }

    public int[] getAppearance() {
        return (int[]) Reflection.value("gs", "k", raw);
    }

    public int[] getBodyColors() {
        return (int[]) Reflection.value("gs", "q", raw);
    }

    public boolean isFemale() {
        return (boolean) Reflection.value("gs", "f", raw);
    }

    /*public int getNPCId() {
        return (int) Reflection.value("PlayerComposite#npcID", raw);
    }*/

    public long getStaticModelId() {
        return (long) Reflection.value("gs", "j", raw) * -1658536306076960475L;
    }

    public boolean isValid() {
        return raw != null;
    }

}
