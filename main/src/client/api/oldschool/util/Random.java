package client.api.oldschool.util;

/**
 * Created by Spencer on 11/9/2016.
 */
public class Random {

    public static int nextInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
