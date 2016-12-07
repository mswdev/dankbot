package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 11/11/2016.
 */
public class ClanMember {

    private Object raw;

    public ClanMember(Object raw) {
        this.raw = raw;
    }

    public String getFormattedName() {
        return (String) Reflection.value("ClanMember#getFormattedName", raw);
    }

    public String getName() {
        return (String) Reflection.value("ClanMember#getName", raw);
    }

    public int getRank() {
        return (int) Reflection.value("ClanMember#getRank", raw);
    }

    public int getWorld() {
        return (int) Reflection.value("ClanMember#getWorld", raw);
    }
}
