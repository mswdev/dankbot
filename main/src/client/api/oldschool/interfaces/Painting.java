package client.api.oldschool.interfaces;

import java.awt.*;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public interface Painting {

    default int onPaint(Graphics graphics, int x, int y) {
        return y;
    }

    default void onPaint(Graphics graphics) {

    }

    default boolean isValid() {
        return true;
    }

}

