package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/6/2016.
 */
public class NPC extends Character {

    private Object raw;

    public NPC(Object raw) {
        super(raw);
        this.raw = raw;
    }

    public NPCDefinition getDefinition() {
        return new NPCDefinition(Reflection.value("ak", "k"));
    }

}
