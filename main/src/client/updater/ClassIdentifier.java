/******************************************************
 * Created by Marneus901                                *
 * � 2012-2014                                          *
 * **************************************************** *
 * Access to this source is unauthorized without prior  *
 * authorization from its appropriate author(s).        *
 * You are not permitted to release, nor distribute this* 
 * work without appropriate author(s) authorization.    *
 ********************************************************/
package client.updater;

import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class ClassIdentifier {
    public abstract void identify(HashMap<String, ClassData> clientClasses);

    public abstract void identifyFields(HashMap<String, FieldData> references);

    public abstract void identifyMethods(HashMap<String, MethodData> referenceInfo);

    protected ArrayList<FieldDescription> fields = new ArrayList<FieldDescription>();
    protected String superclass;
    protected int access;
    protected ArrayList<ClassNode> classes = new ArrayList<ClassNode>();
    public ClassNode identifiedClass2;
    public ClassData identifiedClass;
    protected HashMap<String, ClassNode> identifiedClientClasses;
    public HashMap<String, FieldData> identifiedFields = new HashMap<String, FieldData>();
    public HashMap<String, MethodData> identifiedMethods = new HashMap<String, MethodData>();

    public void setIdentifiedClasses(HashMap<String, ClassNode> map) {
        identifiedClientClasses = map;
    }

    protected boolean fieldsIdentified() {
        for (FieldDescription f : fields)
            if (!f.found)
                return false;
        return true;
    }

    protected int getInstanceCount(String desc) {
        int count = 0;
        for (ClassNode cn : classes) {
            for (FieldNode fn : cn.fields) {
                if (fn.desc.equals(desc))
                    count++;
            }
        }
        return count;
    }

    protected FieldNode[] getInstances(String desc) {
        ArrayList<FieldNode> instances = new ArrayList<FieldNode>();
        for (ClassNode cn : classes) {
            instances.addAll(cn.fields.stream().filter(fn -> fn.desc.equals(desc)).collect(Collectors.toList()));
        }
        return instances.toArray(new FieldNode[]{});
    }

    private boolean doArgsMatch(MethodDescription ms, String[] args) {
        for (int i = 0; i < ms.arguments.length; ++i) {
            if (ms.arguments[i].equals("?")) {
                continue;
            }
            if (!ms.arguments[i].equals(args[i])) {
                if (ms.arguments[i].equals("[") && args[i].startsWith("[")) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    protected String[] parseArguments(MethodNode mn) {
        ArrayList<String> args = new ArrayList<>();
        String signiture = mn.desc;
        signiture = signiture.substring(signiture.indexOf("(") + 1, signiture.indexOf(")"));
        while (signiture != null && !signiture.equals("") && signiture.length() > 0) {
            if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                args.add(signiture.charAt(0) + "");
                signiture = signiture.substring(1, signiture.length());
            } else if (signiture.charAt(0) == '[') {
                String arg = "";
                while (signiture.charAt(0) == '[') {
                    arg = arg + "[";
                    signiture = signiture.substring(1, signiture.length());
                }
                if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                    args.add(arg + signiture.charAt(0) + "");
                    signiture = signiture.substring(1, signiture.length());
                } else {
                    args.add(arg + signiture.substring(0, signiture.indexOf(";") + 1));
                    signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
                }
            } else {
                args.add(signiture.substring(0, signiture.indexOf(";") + 1));
                signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
            }
        }
        return args.toArray(new String[]{});
    }

    protected boolean classContainMethod(ClassNode cg, MethodDescription ms) {
        for (MethodNode ref : cg.methods) {
            String retType = ref.desc.substring(ref.desc.indexOf(")") + 1, ref.desc.length());
            if (ms.returnType.equals("?") || ms.returnType.equals(retType) || (ms.returnType.equals("[") && retType.startsWith("["))) {
                String[] args = parseArguments(ref);
                if (args.length == ms.arguments.length) {
                    if (doArgsMatch(ms, args)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean isMatch(ClassNode cg, MethodDescription[] methods) {
        for (MethodDescription ms : methods) {
            if (!classContainMethod(cg, ms)) {
                return false;
            }
        }
        return true;
    }

    public void updateClasses(HashMap<String, ClassNode> client) {
        classes.clear();
        for (String s : client.keySet()) {
            ClassNode cn = client.get(s);
            if (s.equals(cn.name))
                classes.add(cn);
        }
    }
}
