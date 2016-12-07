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

public class IdentityKit extends ClassIdentifier {
	private static MethodDescription identityKitClassMethods[] = new MethodDescription[]{
	};
    @Override
	public void identify(HashMap<String, ClassData> clientClasses) {
		try{
			access=33;
			superclass="java/lang/Object";
			fields.add(new FieldDescription(0, "[I"));
			fields.add(new FieldDescription(0, "I"));
			fields.add(new FieldDescription(0, "Z"));
			for (ClassData cd : clientClasses.values()) {
				ClassNode cn = cd.bytecodeClass;
				if (!cn.superName.equals(superclass))
					continue;
				if (cn.access!=access)
					continue;
				int check = 0;
				for (FieldNode fn : cn.fields) {
					if (fn.isStatic())
						continue;
					if (fn.desc.equals("[I"))
						check++;
					for (FieldDescription fs : fields) {
						if (!fs.found && fs.isField(fn)) {
							fs.found=true;
							break;
						}
					}
				}
				if (check!=4)
					continue;
				if (fieldsIdentified() && isMatch(cn, identityKitClassMethods)) {
					System.out.println("^ "+cn.name+" identified as "+this.getClass().getSimpleName());
					identifiedClass2=cn;
					identifiedClass = cd;
					return;
				}
				for (FieldDescription fs : fields)
					fs.found=false;
			}
			System.out.println("Failed to identify "+this.getClass().getSimpleName()+"!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void identifyFields(HashMap<String, FieldData> references) {
		try{
			if (identifiedClass2==null) {
				System.out.println("Identified class is null");
				return;
			}
			FieldSigniture visible[] = new FieldSigniture[]{
			};
			FieldSigniture bodyID[] = new FieldSigniture[]{
			};
			for (FieldNode fn : identifiedClass2.fields) {
				if (fn.desc.equals("Z") && fn.access==0) {
					FieldData fd = references.get(identifiedClass2.name+"."+fn.name);
					if (fd!=null) {
						if (fd.isMatch(visible)) {
							System.out.println("-^* "+identifiedClass2.name+"."+fn.name+" identified as isVisible");
							identifiedFields.put("isVisible", fd);
							continue;
						}
					}
				}
				if (fn.desc.equals("I") && fn.access==0) {
					FieldData fd = references.get(identifiedClass2.name+"."+fn.name);
					if (fd!=null) {
						if (fd.isMatch(bodyID)) {
							System.out.println("-^* "+identifiedClass2.name+"."+fn.name+" identified as getBodyId");
							identifiedFields.put("getBodyId", fd);
							continue;
						}
					}
				}
			}
			if (identifiedFields.size()<2) {
				if (!identifiedFields.containsKey("isVisible"))
					System.out.println("Failed to identify "+this.getClass().getSimpleName()+"."+"isVisible"+"!");
				if (!identifiedFields.containsKey("getBodyId"))
					System.out.println("Failed to identify "+this.getClass().getSimpleName()+"."+"getBodyId"+"!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void identifyMethods(HashMap<String, MethodData> referenceInfo) {
	}
}
