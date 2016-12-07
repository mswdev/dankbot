package client.api.debuggers;

import client.api.oldschool.interfaces.Painting;
import client.api.oldschool.interfaces.Validatable;

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

    private final String[] validReturnTypes = new String[] {int.class.getName(), String.class.getName(), boolean.class.getName(), long.class.getName()};

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
                    if (Validatable.class.isInstance(clazz)) {
                        Validatable validation = Validatable.class.cast(clazz);
                        if (validation != null) {
                            if (!validation.isValid()) {
                                continue;
                            }
                        }
                    }

                    for (Method method : clazz.getDeclaredMethods()) {
                        if (Arrays.asList(validReturnTypes).contains(method.getReturnType().getName())) {
                            String[] classNames = clazz.getName().split("\\.");

                            Object invoke = method.invoke(instance);
                            String display = invoke == null ? "Null" : invoke.toString();
                            graphics.drawString(classNames[classNames.length - 1] + "." + method.getName().replace("get", "") + ": " + display, x, y += 15);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        return y;
    }
}
