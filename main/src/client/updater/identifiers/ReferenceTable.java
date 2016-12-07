package client.updater.identifiers;

import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.ClassIdentifier;
import client.updater.FieldDescription;
import client.updater.MethodDescription;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.HashMap;

public class ReferenceTable extends ClassIdentifier {
	private static MethodDescription referenceTableClassMethods[] = new MethodDescription[]{
	};
    @Override
	public void identify(HashMap<String, ClassData> clientClasses) {
		try{
			access=1057;
			superclass="java/lang/Object";
			fields.add(new FieldDescription(0, "[[I"));
			fields.add(new FieldDescription(0, "[I"));
			fields.add(new FieldDescription(0, "I"));
			fields.add(new FieldDescription(0, "Z"));
			fields.add(new FieldDescription(0, "[[Ljava/lang/Object;"));
			fields.add(new FieldDescription(0, "[Ljava/lang/Object;"));
			for (ClassData cd : clientClasses.values()) {
				ClassNode cn = cd.bytecodeClass;
				if (!cn.superName.equals(superclass))
					continue;
				if (cn.access!=access)
					continue;
				for (FieldNode fn : cn.fields) {
					for (FieldDescription fs : fields) {
						if (!fs.found && fs.isField(fn)) {
							fs.found=true;
							break;
						}
					}
				}
				if (fieldsIdentified() && isMatch(cn, referenceTableClassMethods)) {
					System.out.println("^ "+cn.name+" identified as "+this.getClass().getSimpleName());
					identifiedClass2=cn;
					identifiedClass = cd;
					return;
				}
				else {
					for (FieldDescription fs : fields)
						fs.found=false;
				}
			}
			System.out.println("Failed to identify "+this.getClass().getSimpleName()+"!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void identifyFields(HashMap<String, FieldData> references) {
	}
	@Override
	public void identifyMethods(HashMap<String, MethodData> referenceInfo) {
	}
}
