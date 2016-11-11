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
        return (int) Reflection.value("f", "m", raw) * 91598637;
    }

    public int getSkullIcon() {
        return (int) Reflection.value("f", "f", raw) * 892851909;
    }

    public PlayerDefinition getDefinition() {
        return new PlayerDefinition(Reflection.value("f", "q", raw));
    }

}
