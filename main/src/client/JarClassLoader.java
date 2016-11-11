package client;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by Spencer on 11/5/2016.
 */
public class JarClassLoader extends ClassLoader {

    private final URL JAR_URL;

    private HashMap<String, byte[]> entries = new HashMap<>();
    private HashMap<String, Class<?>> classes = new HashMap<>();

    public JarClassLoader(final URL JAR_URL) {
        this.JAR_URL = JAR_URL;
        load();
    }

    public HashMap<String, Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) {
        try {
            if (entries.containsKey(name)) {
                byte[] value = entries.get(name);
                ClassReader classReader = new ClassReader(value);
                ClassNode classNode = new ClassNode();
                classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES) {
                    @Override
                    protected String getCommonSuperClass(String one, String two) {
                        return "java/lang/Object";
                    }
                };

                classNode.accept(classWriter);
                value = classWriter.toByteArray();
                Class<?> clazz = defineClass(name, value, 0, value.length);
                if (!classes.containsKey(clazz)) {
                    classes.put(name, clazz);
                }

                return clazz;
            }
            return super.loadClass(name, resolve);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void load() {
        try {
            JarInputStream jis = new JarInputStream(JAR_URL.openStream());
            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int read;
                while ((read = jis.read(data, 0, 1024)) > 0) {
                    bos.write(data, 0, read);
                }

                entries.put(entry.getName().replace(".class", ""), bos.toByteArray());
                bos.close();
            }
            jis.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}