package client.updater.identifiers;

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

import java.util.HashMap;

public class Cache extends ClassIdentifier {
    private static MethodDescription cacheClassMethods[] = new MethodDescription[]{
    };

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        access = 49;
        superclass = "java/lang/Object";
        fields.add(new FieldDescription(0, "I"));
        fields.add(new FieldDescription(0, "L" + identifiedClientClasses.get("HashTable").name + ";"));
        fields.add(new FieldDescription(0, "L" + identifiedClientClasses.get("Queue").name + ";"));
        fields.add(new FieldDescription(0, "L" + identifiedClientClasses.get("CacheNode").name + ";"));
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
            if (fieldsIdentified() && isMatch(classNode, cacheClassMethods)) {
                System.out.println("^ " + classNode.name + " identified as " + this.getClass().getSimpleName());
                identifiedClass2 = classNode;
                identifiedClass = classData;
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
            FieldSigniture hashTable[] = new FieldSigniture[]{
            };
            FieldSigniture queue[] = new FieldSigniture[]{
            };
            FieldSigniture node[] = new FieldSigniture[]{
            };
            FieldSigniture size[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{}, "getfield")
            };
            FieldSigniture remaining[] = new FieldSigniture[]{
                    new FieldSigniture("V", new String[]{}, "putfield")
            };
            for (FieldNode fieldNode : identifiedClass2.fields) {
                if (fieldNode.desc.equals("L" + identifiedClientClasses.get("HashTable").name + ";") && fieldNode.access == 0) {
                    FieldData fieldData = references.get(identifiedClass2.name + "." + fieldNode.name);
                    if (fieldData != null) {
                        if (fieldData.isMatch(hashTable)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getHashTable");
                            identifiedFields.put("getHashTable", fieldData);
                            continue;
                        }
                    }
                }
                if (fieldNode.desc.equals("L" + identifiedClientClasses.get("Queue").name + ";") && fieldNode.access == 0) {
                    FieldData fieldData = references.get(identifiedClass2.name + "." + fieldNode.name);
                    if (fieldData != null) {
                        if (fieldData.isMatch(queue)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getQueue");
                            identifiedFields.put("getQueue", fieldData);
                            continue;
                        }
                    }
                }
                if (fieldNode.desc.equals("L" + identifiedClientClasses.get("CacheNode").name + ";") && fieldNode.access == 0) {
                    FieldData fieldData = references.get(identifiedClass2.name + "." + fieldNode.name);
                    if (fieldData != null) {
                        if (fieldData.isMatch(node)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getCacheNode");
                            identifiedFields.put("getCacheNode", fieldData);
                            continue;
                        }
                    }
                }
                if (fieldNode.desc.equals("I") && fieldNode.access == 0) {
                    FieldData fieldData = references.get(identifiedClass2.name + "." + fieldNode.name);
                    if (fieldData != null) {
                        if (fieldData.isMatch(size)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getSize");
                            identifiedFields.put("getSize", fieldData);
                            continue;
                        }

                        if (fieldData.isMatch(remaining)) {
                            System.out.println("-^* " + identifiedClass2.name + "." + fieldNode.name + " identified as getRemaining");
                            identifiedFields.put("getRemaining", fieldData);
                            continue;
                        }
                    }
                }
            }

            if (identifiedFields.size() < 5) {
                if (!identifiedFields.containsKey("getRemaining"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getRemaining" + "!");
                if (!identifiedFields.containsKey("getSize"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getSize" + "!");
                if (!identifiedFields.containsKey("getCacheNode"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getCacheNode" + "!");
                if (!identifiedFields.containsKey("getQueue"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getQueue" + "!");
                if (!identifiedFields.containsKey("getHashTable"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "getHashTable" + "!");
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
                if (md.ACCESS == 1 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(J)V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as remove");
                    identifiedMethods.put("remove", md);
                    continue;
                }
                if (md.ACCESS == 1 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(J)L" + identifiedClientClasses.get("CacheNode").name + ";").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as get");
                    identifiedMethods.put("get", md);
                    continue;
                }
                if (md.ACCESS == 1 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("()V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as clear");
                    identifiedMethods.put("clear", md);
                    continue;
                }
                if (md.ACCESS == 1 && md.CLASS_NAME.equals(identifiedClass2.name) && new Wildcard("(L" + identifiedClientClasses.get("CacheNode").name + ";J)V").matches(md.METHOD_DESC) && md.referencedFrom.size() > 0) {
                    System.out.println("-^& " + md.real_attributes() + " identified as put");
                    identifiedMethods.put("put", md);
                    continue;
                }
            }
            if (identifiedMethods.size() < 4) {
                if (!identifiedMethods.containsKey("put"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "put" + "!");
                if (!identifiedMethods.containsKey("clear"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "clear" + "!");
                if (!identifiedMethods.containsKey("get"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "get" + "!");
                if (!identifiedMethods.containsKey("remove"))
                    System.out.println("Failed to identify " + this.getClass().getSimpleName() + "." + "remove" + "!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
