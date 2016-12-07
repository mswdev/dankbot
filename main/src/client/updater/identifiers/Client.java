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
public class Client extends ClassIdentifier {
    private static MethodDescription clientClassMethods[] = new MethodDescription[]{};
    public HashMap<String, FieldData> staticFields = new HashMap<>();
    public HashMap<String, MethodData> staticMethods = new HashMap<>();

    @Override
    public void identify(HashMap<String, ClassData> clientClasses) {
        for (ClassData classData : clientClasses.values()) {
            ClassNode classNode = classData.bytecodeClass;
            if (!classNode.name.equals("client"))
                continue;

            for (FieldNode fieldNode : classNode.fields) {
                for (FieldDescription fieldDescription : fields) {
                    if (!fieldDescription.found && fieldDescription.isField(fieldNode)) {
                        fieldDescription.found = true;
                        break;
                    }
                }
            }

            if (fieldsIdentified() && isMatch(classNode, clientClassMethods)) {
                Logging.status(" - -* " + classNode.name + " identified as " + this.getClass().getSimpleName());
                identifiedClass2 = classNode;
                identifiedClass = classData;
                return;
            } else {
                for (FieldDescription fieldDescription : fields) {
                    fieldDescription.found = false;
                }
            }
        }

        Logging.error(" - -! Failed to identify " + this.getClass().getSimpleName() + "");
    }

    @Override
    public void identifyFields(HashMap<String, FieldData> references) {

    }

    @Override
    public void identifyMethods(HashMap<String, MethodData> referenceInfo) {

    }
}
