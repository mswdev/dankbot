package client.api.debuggers;

import client.api.oldschool.interfaces.Painting;
import client.api.util.Logging;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Spencer on 11/6/2016.
 */
public class InstanceDebugger<T> implements Painting {

    T instance;
    Callable<T> callable;

    private final Class[] validReturnTypes = new Class[] {Integer.TYPE, String.class, Boolean.TYPE, Long.TYPE, Double.TYPE, Float.TYPE};

    public InstanceDebugger(Callable<T> callable) {
        try {
            this.callable = callable;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InstanceDebugger(T instance) {
        this.instance = instance;
    }

    @Override
    public boolean isValid() {
        return instance != null || callable != null;
    }

    @Override
    public int onPaint(Graphics graphics, int x, int y) {
        try {
            if (instance == null && callable == null)
                return y;

            if (instance == null) {
                instance = callable.call();
            }

            if (instance != null) {
                for (Class<?> clazz = instance.getClass(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (Arrays.asList(validReturnTypes).contains(method.getGenericReturnType())) {
                            String[] classNames = clazz.getName().split("\\.");
                            graphics.drawString(classNames[classNames.length - 1] + "." + method.getName().replace("get", "") + ": " + method.invoke(instance).toString(), x, y += 15);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logging.error(e.getMessage());
        }

        return y;
    }
}
