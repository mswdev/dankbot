package client.reflection;

import java.lang.reflect.Field;

/**
 * Created by Spencer on 11/10/2016.
 */
public class FieldHook {
    public Field field;
    public int intMultiplier = 0;
    public long longMultiplier = 0;

    public FieldHook(Field field) {
        this.field = field;
        this.intMultiplier = 0;
    }

    public FieldHook(String multiplier, Field field) {
        this.field = field;
        if (!multiplier.contains("L")) {
            this.intMultiplier = Integer.parseInt(multiplier);
        } else {
            this.longMultiplier = Long.parseLong(multiplier.replace("L", ""));
        }
    }

}
