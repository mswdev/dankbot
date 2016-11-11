package client.reflection;

import client.JarClassLoader;
import client.api.util.Logging;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Reflection {

    private static JarClassLoader loader;

    private static HashMap<String, FieldHook> fields = new HashMap<>();
    private static HashMap<String, MethodHook> methods = new HashMap<>();

    public static void init() {
        Path reflectionPath = Paths.get("reflection.dat");
        if (Files.exists(reflectionPath)) {
            try {
                List<String> lines = Files.readAllLines(reflectionPath);

                for (String line : lines) {
                    String[] split = line.split(" ");

                    Class<?> clazz = loadClass(split[1]);
                    Field field = clazz.getDeclaredField(split[2]);
                    field.setAccessible(true);

                    if (split.length > 3) {
                        fields.put(split[0], new FieldHook(field));
                    } else {
                        fields.put(split[0], new FieldHook(split[4], field));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static Class<?> loadClass(final String class_name) {
        if (loader == null) {
            Logging.error("Class loader is null.");
            return null;
        }

        if (!loader.getClasses().containsKey(class_name)) {
            try {
                return loader.loadClass(class_name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return loader.getClasses().get(class_name);
    }

    public static void setLoader(String JAR_FILE) {
        try {
            loader = new JarClassLoader(Paths.get(JAR_FILE).toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static Object value(String identifier) {
        return value(identifier, null);
    }

    public static Object value(String identifier, Object instance) {
        FieldHook hook = field(identifier);
        if (hook != null) {
            try {
                if (hook.longMultiplier != 0) {
                    return (long) hook.field.get(instance) * hook.longMultiplier;
                } else if (hook.intMultiplier != 0){
                    return (int) hook.field.get(instance) * hook.intMultiplier;
                } else {
                    return hook.field.get(instance);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static FieldHook field(String identifier) {
        return fields.containsKey(identifier) ? fields.get(identifier) : null;
    }
}
