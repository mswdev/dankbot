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

public class ByteBuffer extends ClassIdentifier {
    private static MethodDescription byteBufferClassMethods[] = new MethodDescription[]{
            new MethodDescription("V", new String[]{}),
            new MethodDescription("V", new String[]{"?"}),
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        try {
            access = 33;
            superclass = identifiedClientClasses.get("Node").name;
            fields.add(new FieldDescription(1, "I"));
            fields.add(new FieldDescription(1, "[B"));
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
                if (nonstatic == 2 && fieldsIdentified() && isMatch(cn, byteBufferClassMethods)) {
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
            FieldSigniture offset[] = new FieldSigniture[]{
            };
            FieldSigniture bytes[] = new FieldSigniture[]{
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("I") && fn.access == 1) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(offset)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getOffset");
                            identifiedFields.put("getOffset", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("[B") && fn.access == 1) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(bytes)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getBytes");
                            identifiedFields.put("getBytes", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 2) {
                if (!identifiedFields.containsKey("getOffset"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getOffset" + "!");
                if (!identifiedFields.containsKey("getBytes"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getBytes" + "!");
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
                if (!identifiedMethods.containsKey("applyRSA")) {
                    if (md.ACCESS == 1 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(Ljava/math/BigInteger;Ljava/math/BigInteger;?)V").matches(md.METHOD_DESC)) {
                        System.out.println("-^& " + md.real_attributes() + " identified as applyRSA");
                        identifiedMethods.put("applyRSA", md);
                        continue;
                    }
                }
                if (md.ACCESS == 1 && md.referencedFrom.size() > 0 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(?)B").matches(md.METHOD_DESC)) {
                    System.out.println("-^& " + md.real_attributes() + " identified as get");
                    identifiedMethods.put("get", md);
                    continue;
                }
            }
            if (identifiedMethods.size() < 2) {
                if (!identifiedMethods.containsKey("get"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "get" + "!");
                if (!identifiedMethods.containsKey("applyRSA"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "applyRSA" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
