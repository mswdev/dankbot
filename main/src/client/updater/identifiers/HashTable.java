package client.updater.identifiers;

import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.asm.Wildcard;
import client.updater.asm.assembly.Assembly;
import client.updater.asm.assembly.Mask;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.FieldSigniture;
import client.updater.MethodDescription;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.List;

public class HashTable extends ClassIdentifier {
    private static MethodDescription hashTableClassMethods[] = new MethodDescription[]{
            new MethodDescription("V", new String[]{"I"}),
            new MethodDescription("?", new String[]{}),
            new MethodDescription("?", new String[]{"J"}),
            new MethodDescription("?", new String[]{"?", "J"}),
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        try {
            access = 49;
            superclass = "java/lang/Object";
            fields.add(new FieldDescription(0, "I"));
            fields.add(new FieldDescription(0, "[L" + identifiedClientClasses.get("Node").name + ";"));
            fields.add(new FieldDescription(0, "L" + identifiedClientClasses.get("Node").name + ";"));
            for (ClassData cd : clientClasses.values()) {
                ClassNode cn = cd.bytecodeClass;
                if (!cn.superName.equals(superclass))
                    continue;
                if (cn.access != access)
                    continue;
                for (FieldNode fn : cn.fields) {
                    for (FieldDescription fs : fields) {
                        if (!fs.found && fs.isField(fn)) {
                            fs.found = true;
                            break;
                        }
                    }
                }
                if (cn.fields.size() == 5 && fieldsIdentified() && isMatch(cn, hashTableClassMethods)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void identifyFields(HashMap<String, FieldData> references) {
        try {
            if (identifiedClass2 == null) {
                System.out.println("Identified class is null");
                return;
            }
            FieldSigniture buckets[] = new FieldSigniture[]{
            };
            FieldSigniture tail[] = new FieldSigniture[]{
                    new FieldSigniture("L" + identifiedClientClasses.get("Node").name + ";", new String[]{}, "")
            };
            FieldSigniture head[] = new FieldSigniture[]{
                    new FieldSigniture("L" + identifiedClientClasses.get("Node").name + ";", new String[]{"J"}, "")
            };
            FieldSigniture size[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{}, ""),
                    new FieldSigniture("V", new String[]{"L" + identifiedClientClasses.get("Node").name + ";", "J"}, ""),
                    new FieldSigniture("L" + identifiedClientClasses.get("Node").name + ";", new String[]{}, ""),
                    new FieldSigniture("L" + identifiedClientClasses.get("Node").name + ";", new String[]{"J"}, "")
            };
            FieldSigniture index[] = new FieldSigniture[]{
                    new FieldSigniture("L" + identifiedClientClasses.get("Node").name + ";", new String[]{}, "")
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("[L" + identifiedClientClasses.get("Node").name + ";") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(buckets)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getBuckets");
                            identifiedFields.put("getBuckets", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("L" + identifiedClientClasses.get("Node").name + ";") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(head)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getHead");
                            identifiedFields.put("getHead", fd);
                            continue;
                        }
                        if (fd.isMatch(tail)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getTail");
                            identifiedFields.put("getTail", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("I") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(size)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getSize");
                            identifiedFields.put("getSize", fd);
                            continue;
                        }
                        if (fd.isMatch(index)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getIndex");
                            identifiedFields.put("getIndex", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("J") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {

                    }
                }
            }
            if (identifiedFields.size() < 5) {
                if (!identifiedFields.containsKey("getBuckets"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getBuckets" + "!");
                if (!identifiedFields.containsKey("getSize"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getSize" + "!");
                if (!identifiedFields.containsKey("getHead"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getHead" + "!");
                if (!identifiedFields.containsKey("getTail"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getTail" + "!");
                if (!identifiedFields.containsKey("getIndex"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getIndex" + "!");
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
            for (MethodNode mn : identifiedClass2.methods) {
                MethodData md = referenceInfo.get(identifiedClass2.name + "." + mn.name + mn.desc);
                if (md != null) {
                    if (mn.access == 1 && new Wildcard("(L" + identifiedClientClasses.get("Node").name + ";J)V").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as put");
                        identifiedMethods.put("put", new MethodData(identifiedClass2.name, mn));
                        continue;
                    }
                    if (mn.access == 1 && new Wildcard("(J)L" + identifiedClientClasses.get("Node").name + ";").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as get");
                        identifiedMethods.put("get", new MethodData(identifiedClass2.name, mn));
                        continue;
                    }
                    if (mn.access == 1 && new Wildcard("()L" + identifiedClientClasses.get("Node").name + ";").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        List<AbstractInsnNode> pattern = Assembly.find(mn,
                                Mask.GETFIELD
                        );
                        if (pattern != null) {
                            System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as next");
                            identifiedMethods.put("next", new MethodData(identifiedClass2.name, mn));
                            continue;
                        }
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as resetIndex");
                        identifiedMethods.put("resetIndex", new MethodData(identifiedClass2.name, mn));
                        continue;
                    }
                    if (mn.access == 0 && new Wildcard("()V").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as clear");
                        identifiedMethods.put("clear", new MethodData(identifiedClass2.name, mn));
                        continue;
                    }
                }
            }
            if (identifiedMethods.size() < 5) {
                if (!identifiedMethods.containsKey("clear"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "clear" + "!");
                if (!identifiedMethods.containsKey("resetIndex"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "resetIndex" + "!");
                if (!identifiedMethods.containsKey("get"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "get" + "!");
                if (!identifiedMethods.containsKey("put"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "put" + "!");
                if (!identifiedMethods.containsKey("next"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "next" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
