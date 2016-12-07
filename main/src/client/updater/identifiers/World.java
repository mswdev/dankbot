package client.updater.identifiers;

import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.FieldSigniture;
import client.updater.MethodDescription;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.HashMap;

public class World extends ClassIdentifier {
    private static MethodDescription worldClassMethods[] = new MethodDescription[]{
            new MethodDescription("V", new String[]{}),
            new MethodDescription("Z", new String[]{"I"}),
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        try {
            access = 33;
            superclass = "java/lang/Object";
            fields.add(new FieldDescription(0, "I"));
            fields.add(new FieldDescription(0, "Ljava/lang/String;"));
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
                if (fieldsIdentified() && isMatch(cn, worldClassMethods)) {
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
            FieldSigniture activity[] = new FieldSigniture[]{
            };
            FieldSigniture domain[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{"L" + identifiedClass2.name + ";", "?"}, ""),
            };
            FieldSigniture mask[] = new FieldSigniture[]{
                    new FieldSigniture("Z", new String[]{"?"}, "instruction instruction instruction instruction instruction instruction ifinstruction"),
            };
            FieldSigniture world[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{"L" + identifiedClass2.name + ";", "?"}, "instruction instruction instruction imul putstatic"),
            };
            FieldSigniture serverLocation[] = new FieldSigniture[]{
                    new FieldSigniture("Z", new String[]{"?"}, "invoke instruction instruction instruction"),
                    new FieldSigniture("Z", new String[]{"?"}, "imul putfield"),
            };
            FieldSigniture playerCount[] = new FieldSigniture[]{
                    new FieldSigniture("I", new String[]{"L" + identifiedClass2.name + ";", "L" + identifiedClass2.name + ";", "I", "Z", "?"}, "instruction instruction instruction imul istore"),
            };
            for (FieldNode fn : identifiedClass2.fields) {
                if (fn.desc.equals("Ljava/lang/String;") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(domain)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getDomain");
                            identifiedFields.put("getDomain", fd);
                            continue;
                        }
                        if (fd.isMatch(activity)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getActivity");
                            identifiedFields.put("getActivity", fd);
                            continue;
                        }
                    }
                }
                if (fn.desc.equals("I") && fn.access == 0) {
                    FieldData fd = references.get(identifiedClass2.name + "." + fn.name);
                    if (fd != null) {
                        if (fd.isMatch(mask)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getMask");
                            identifiedFields.put("getMask", fd);
                            continue;
                        }
                        if (fd.isMatch(world)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getWorld");
                            identifiedFields.put("getWorld", fd);
                            continue;
                        }
                        if (fd.isMatch(playerCount)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getPlayerCount");
                            identifiedFields.put("getPlayerCount", fd);
                            continue;
                        }
                        if (fd.isMatch(serverLocation)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fn.name + " identified as getServerLocation");
                            identifiedFields.put("getServerLocation", fd);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 6) {
                if (!identifiedFields.containsKey("getDomain"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getDomain" + "!");
                if (!identifiedFields.containsKey("getActivity"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getActivity" + "!");
                if (!identifiedFields.containsKey("getMask"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getMask" + "!");
                if (!identifiedFields.containsKey("getWorld"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getWorld" + "!");
                if (!identifiedFields.containsKey("getPlayerCount"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getPlayerCount" + "!");
                if (!identifiedFields.containsKey("getServerLocation"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getServerLocation" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void identifyMethods(HashMap<String, MethodData> referenceInfo) {
    }
}
