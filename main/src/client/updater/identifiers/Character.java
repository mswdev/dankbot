package client.updater.identifiers;

import client.api.util.Logging;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.MethodDescription;
import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.HashMap;

/**
 * Created by Spencer on 11/12/2016.
 */
public class Character extends ClassIdentifier {
    private static MethodDescription characterClassMethods[] = new MethodDescription[]{};

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        access = 1057;
        superclass = identifiedClientClasses.get("RenderableNode").name;
        fields.add(new FieldDescription(0, "I"));
        fields.add(new FieldDescription(0, "[I"));
        fields.add(new FieldDescription(0, "Z"));
        fields.add(new FieldDescription(0, "Ljava/lang/String;"));

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
            if (fieldsIdentified() && isMatch(classNode, characterClassMethods)) {
                Logging.status(" - -* " + classNode.name + " identified as " + this.getClass().getSimpleName());
                identifiedClass2 = classNode;
                identifiedClass = classData;
                return;
            } else {
                for (FieldDescription fieldDescription : fields)
                    fieldDescription.found = false;
            }
        }
        Logging.status(" - -! Failed to identify " + this.getClass().getSimpleName() + "!");
    }

    @Override
    public void identifyFields(HashMap<String, FieldData> references) {

    }

    @Override
    public void identifyMethods(HashMap<String, MethodData> referenceInfo) {

    }
}
