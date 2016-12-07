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

public class RenderableNode extends ClassIdentifier {
    private static MethodDescription renderableNodeClassMethods[] = new MethodDescription[]{};

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        access = 1057;
        superclass = identifiedClientClasses.get("CacheNode").name;
        fields.add(new FieldDescription(1, "I"));
        for (ClassData cd : clientClasses.values()) {
            ClassNode cn = cd.bytecodeClass;
            if (!cn.superName.equals(superclass) || cn.access != access)
                continue;

            int nonstatic = 0;
            for (FieldNode fn : cn.fields) {
                if (!fn.isStatic())
                    nonstatic++;
                for (FieldDescription fs : fields) {
                    if (!fs.found && fs.isField(fn)) {
                        fs.found = true;
                        break;
                    }
                }
            }
            if (nonstatic == 1 && fieldsIdentified() && isMatch(cn, renderableNodeClassMethods)) {
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
            FieldSigniture height[] = new FieldSigniture[]{
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("I") && fn.access == 1) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(height)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getHeight");
                            identifiedFields.put("height", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 1) {
                if (!identifiedFields.containsKey("getHeight"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getHeight" + "!");
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
                    if (mn.access == 0 && new Wildcard("(IIIIIIII?)V").matches(mn.desc) && md.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + mn.name + mn.desc + " identified as renderAt");
                        identifiedMethods.put("renderAt", new MethodData(identifiedClass2.name, mn));
                        continue;
                    }
                }
            }
            if (identifiedMethods.size() < 1) {
                if (!identifiedMethods.containsKey("renderAt"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "renderAt" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
