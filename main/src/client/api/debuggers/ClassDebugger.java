package client.api.debuggers;

import client.api.oldschool.interfaces.Painting;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Spencer on 11/6/2016.
 */
public class ClassDebugger implements Painting {

    private Class[] clazzes;
    private int y;

    public ClassDebugger(Class... clazzes) {
        this.clazzes = clazzes;
    }

    @Override
    public int onPaint(Graphics graphics, int x, int y) {
        graphics.setColor(Color.GREEN);
        try {
            for (Class clazz : clazzes) {
                for (Method method : clazz.getDeclaredMethods()) {
                    method.setAccessible(true);
                    graphics.drawString(method.getName().replace("get", "") + ": " + method.invoke(null), 10, y += 15);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return y;
    }
}
