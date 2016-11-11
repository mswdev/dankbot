package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Player extends Character {

    private Object raw;

    public Player(Object raw) {
        super(raw);
        this.raw = raw;
    }

    public int getCombatLevel() {
        return (int) Reflection.value("Player#getCombatLevel", raw);
    }

    public int getSkullIcon() {
        return (int) Reflection.value("Player#getSkullIcon", raw);
    }

    public PlayerDefinition getDefinition() {
        return new PlayerDefinition(Reflection.value("Player#getDefinition", raw));
    }

}
