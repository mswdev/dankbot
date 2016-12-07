package client.updater.identifiers;

import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.asm.Wildcard;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.FieldSigniture;
import client.updater.MethodDescription;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;

public class Node extends ClassIdentifier {
    private static MethodDescription nodeClassMethods[] = new MethodDescription[]{
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        try {
            access = 33;
            superclass = "java/lang/Object";
            fields.add(new FieldDescription(0, "?"));
            fields.add(new FieldDescription(1, "J"));
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

                if (fieldsIdentified() && isMatch(cn, nodeClassMethods)) {
                    System.out.println("^ " + cn.name + " identified as " + this.getClass().getSimpleName());
                    identifiedClass2 = cn;
                    identifiedClass = cd;
                    return;
                }
                for (FieldDescription fs : fields)
                    fs.found = false;
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

            FieldSigniture previous[] = new FieldSigniture[]{};
            FieldSigniture next[] = new FieldSigniture[]{};
            FieldSigniture uid[] = new FieldSigniture[]{};
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("L" + identifiedClass2.name + ";")) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        MethodData md = identifiedMethods.get("isParent");
                        if ((md != null && md.isFieldReferenced(fd)) && fd.isMatch(previous)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getPrevious");
                            identifiedFields.put("getPrevious", fd);
                            continue;
                        } else if (fd.isMatch(next)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getNext");
                            identifiedFields.put("getNext", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("J") && fn.access == 1) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(uid)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getId");
                            identifiedFields.put("getId", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 3) {
                if (!identifiedFields.containsKey("getPrevious"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getPrevious" + "!");
                if (!identifiedFields.containsKey("getNext"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getNext" + "!");
                if (!identifiedFields.containsKey("getId"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getId" + "!");
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
                    if (!identifiedMethods.containsKey("isParent") && mn.access == 1 && new Wildcard("()Z").matches(mn.desc)) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as isParent");
                        identifiedMethods.put("isParent", md);
                        continue;
                    }
                    if (mn.access == 1 && new Wildcard("()V").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as remove");
                        identifiedMethods.put("remove", md);
                        continue;
                    }
                }
            }
            if (identifiedMethods.size() < 2) {
                if (!identifiedMethods.containsKey("isParent"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "isParent" + "!");
                if (!identifiedMethods.containsKey("remove"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "remove" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
