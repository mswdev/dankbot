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

import java.util.HashMap;

public class ISAACCipher extends ClassIdentifier {
    private static MethodDescription isaacCipherClassMethods[] = new MethodDescription[]{
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        access = 49;
        superclass = "java/lang/Object";
        fields.add(new FieldDescription(0, "I"));
        fields.add(new FieldDescription(0, "[I"));
        for (ClassData cd : clientClasses.values()) {
            ClassNode cn = cd.bytecodeClass;
            if (!cn.superName.equals(superclass))
                continue;
            if (cn.access != access)
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
            if (nonstatic == 6 && fieldsIdentified() && isMatch(cn, isaacCipherClassMethods)) {
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
            FieldSigniture memory[] = new FieldSigniture[]{
            };
            FieldSigniture count[] = new FieldSigniture[]{
                    new FieldSigniture("I", new String[]{"?"}, "instruction instruction instruction imul"),
            };
            FieldSigniture results[] = new FieldSigniture[]{
                    new FieldSigniture("I", new String[]{"?"}, ""),
            };
            FieldSigniture accumulator[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{"?"}, "imul putfield"),
            };
            FieldSigniture last[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{"?"}, "aload dup getfield aload dup"),
            };
            FieldSigniture counter[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{"?"}, "aload dup getfield instruction iadd dup_x1 putfield"),
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("[I") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(results)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getResults");
                            identifiedFields.put("getResults", fd);
                            continue;
                        } else if (fd.isMatch(memory)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getMemory");
                            identifiedFields.put("getMemory", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("I") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(count)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getCount");
                            identifiedFields.put("getCount", fd);
                            continue;
                        }
                        if (fd.isMatch(accumulator)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getAccumulator");
                            identifiedFields.put("getAccumulator", fd);
                            continue;
                        }
                        if (fd.isMatch(last)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getLast");
                            identifiedFields.put("getLast", fd);
                            continue;
                        }
                        if (fd.isMatch(counter)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getCounter");
                            identifiedFields.put("getCounter", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 6) {
                if (!identifiedFields.containsKey("getCount"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getCount" + "!");
                if (!identifiedFields.containsKey("getAccumulator"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getAccumulator" + "!");
                if (!identifiedFields.containsKey("getLast"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getLast" + "!");
                if (!identifiedFields.containsKey("getCounter"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getCounter" + "!");
                if (!identifiedFields.containsKey("getMemory"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getMemory" + "!");
                if (!identifiedFields.containsKey("getResults"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getResults" + "!");
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
                if (md.ACCESS == 16 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(?)I").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as next");
                    identifiedMethods.put("next", md);
                    continue;
                }
                if (md.ACCESS == 16 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(?)V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    if (md.referencedFrom.size() == 1) {
                        System.out.println("-^& " + md.real_attributes() + " identified as initializeKeySet");
                        identifiedMethods.put("initializeKeySet", md);
                        continue;
                    }
                    System.out.println("-^& " + md.real_attributes() + " identified as decrypt");
                    identifiedMethods.put("decrypt", md);
                    continue;
                }
            }
            if (identifiedMethods.size() < 3) {
                if (!identifiedMethods.containsKey("next"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "next" + "!");
                if (!identifiedMethods.containsKey("initializeKeySet"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "initializeKeySet" + "!");
                if (!identifiedMethods.containsKey("decrypt"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "decrypt" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}