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
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;

public class CacheNode extends ClassIdentifier {
    private static MethodDescription cacheableNodeClassMethods[] = new MethodDescription[]{
            new MethodDescription("V", new String[]{}),
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        try {
            access = 33;
            superclass = identifiedClientClasses.get("Node").name;
            fields.add(new FieldDescription(1, "?"));
            for (ClassData classData : clientClasses.values()) {
                ClassNode classNode = classData.bytecodeClass;
                if (!classNode.superName.equals(superclass))
                    continue;
                if (classNode.access != access)
                    continue;
                for (FieldNode fieldNode : classNode.fields) {
                    for (FieldDescription fieldDescription : fields) {
                        if (!fieldDescription.found && fieldDescription.isField(fieldNode)) {
                            fieldDescription.found = true;
                            break;
                        }
                    }
                }

                if (UpdaterMain.getUpdater().findInstanceCount("L" + classNode.name + ";") == 7 && fieldsIdentified() && isMatch(classNode, cacheableNodeClassMethods)) {
                    System.out.println("^ " + classNode.name + " identified as " + this.getClass().getSimpleName());
                    identifiedClass2 = classNode;
                    identifiedClass = classData;
                    return;
                } else {
                    for (FieldDescription fieldDescription : fields)
                        fieldDescription.found = false;
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

            FieldSigniture next[] = new FieldSigniture[]{};
            FieldSigniture previous[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{}, "aload getfield ifnonnull"),
            };
            for (FieldNode fieldNode : identifiedClass2.fields) {
                if (fieldNode.desc.equals("L" + identifiedClass2.name + ";") && fieldNode.access == 1) {
                    FieldData fieldData = references.get(identifiedClass2.name + "." + fieldNode.name);
                    if (fieldData != null) {
                        if (fieldData.isMatch(previous)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getPrevious");
                            identifiedFields.put("getPrevious", fieldData);
                            continue;
                        } else if (fieldData.isMatch(next)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getNext");
                            identifiedFields.put("getNext", fieldData);
                            continue;
                        }
                    }
                }
            }
            if (identifiedFields.size() < 2) {
                if (!identifiedFields.containsKey("getNext"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getNext" + "!");
                if (!identifiedFields.containsKey("getPrevious"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getPrevious" + "!");
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

            for (MethodNode methodNode : identifiedClass2.methods) {
                MethodData methodData = referenceInfo.get(identifiedClass2.name + "." + methodNode.name + methodNode.desc);
                if (methodData != null) {
                    if (methodNode.access == 1 && new Wildcard("()V").matches(methodNode.desc) && methodData.referencedFrom.size() > 0) {
                        System.out.println("-^& " + identifiedClass2.name + "." + methodNode.name + methodNode.desc + " identified as unlink");
                        identifiedMethods.put("unlink", new MethodData(identifiedClass2.name, methodNode));
                        continue;
                    }
                }
            }
            if (identifiedMethods.size() < 1) {
                if (!identifiedMethods.containsKey("unlink"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "unlink" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
