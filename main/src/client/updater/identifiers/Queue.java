package client.updater.identifiers;

import client.UpdaterMain;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.FieldSigniture;
import client.updater.MethodDescription;
import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.asm.Wildcard;
import client.updater.asm.assembly.Assembly;
import client.updater.asm.assembly.Mask;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.HashMap;
import java.util.List;

public class Queue extends ClassIdentifier {
    private static MethodDescription nodeSubQueueClassMethods[] = new MethodDescription[]{
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        access = 49;
        superclass = "java/lang/Object";
        fields.add(new FieldDescription(0, "L" + identifiedClientClasses.get("CacheNode").name + ";"));
        for (ClassData cd : clientClasses.values()) {
            ClassNode cn = cd.bytecodeClass;
            if (!cn.superName.equals(superclass))
                continue;
            if (cn.access != access)
                continue;
            if (UpdaterMain.getUpdater().findInstanceCount("L" + cn.name + ";") != 2)
                continue;
            int count = 0;
            for (FieldNode fn : cn.fields) {
                if (fn.desc.equals("L" + identifiedClientClasses.get("CacheNode").name + ";"))
                    count++;
                for (FieldDescription fs : fields) {
                    if (!fs.found && fs.isField(fn)) {
                        fs.found = true;
                        break;
                    }
                }
            }
            if (count != 1)
                continue;
            if (fieldsIdentified() && isMatch(cn, nodeSubQueueClassMethods)) {
                System.out.println("^ " + cn.name + " identified as " + this.getClass().getSimpleName());
                identifiedClass2 = cn;
                identifiedClass = cd;
                return;
            } else {
                for (FieldDescription fs : fields)
                    fs.found = false;
            }
        }
        System.out.println("Failed to identify " + this.getClass().getSimpleName() + "!");
    }

    @Override
    public void identifyFields(HashMap<String, FieldData> references) {
        try {
            if (identifiedClass2 == null) {
                System.out.println("Identified class is null");
                return;
            }
            FieldSigniture head[] = new FieldSigniture[]{
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("L" + identifiedClientClasses.get("CacheNode").name + ";") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(head)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getHead");
                            identifiedFields.put("getHead", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 1) {
                if (!identifiedFields.containsKey("getHead"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getHead" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void identifyMethods(HashMap<String, MethodData> referenceInfo) {
        try {
            if (identifiedClass2 == null) {
                System.out.println("Identified class is null");
                return;
            }
            for (MethodData md : referenceInfo.values()) {
                if (!identifiedMethods.containsKey("insertBack")) {
                    if (md.ACCESS == 1 && new Wildcard("(L" + identifiedClientClasses.get("CacheNode").name + ";)V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                        List<AbstractInsnNode> pattern = Assembly.find(md.bytecodeMethod,
                                Mask.GETFIELD
                        );
                        if (pattern != null) {
                            System.out.println("-^& " + md.real_attributes() + " identified as insertBack");
                            identifiedMethods.put("insertBack", md);
                            continue;
                        }
                    }
                }
                if (md.ACCESS == 0 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("()V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as clear");
                    identifiedMethods.put("clear", md);
                    continue;
                }
                if (md.ACCESS == 0 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("()L" + identifiedClientClasses.get("CacheNode").name + ";").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    if (md.methodReferences.size() > 0) {
                        System.out.println("-^& " + md.real_attributes() + " identified as popFront");
                        identifiedMethods.put("popFront", md);
                        continue;
                    }
                }
            }
            if (identifiedMethods.size() < 3) {
                if (!identifiedMethods.containsKey("insertBack"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "insertBack" + "!");
                if (!identifiedMethods.containsKey("clear"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "clear" + "!");
                if (!identifiedMethods.containsKey("popFront"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "popFront" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
