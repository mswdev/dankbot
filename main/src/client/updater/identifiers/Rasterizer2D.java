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

public class Rasterizer2D extends ClassIdentifier{
	private static MethodDescription rasterizer2DClassMethods[] = new MethodDescription[]{
	};
    @Override
	public void identify(HashMap<String, ClassData> clientClasses) {
		access=49;
		superclass=identifiedClientClasses.get("Rasterizer").name;
		fields.add(new FieldDescription(1, "[I"));
		fields.add(new FieldDescription(1, "I"));
		for (ClassData cd : clientClasses.values()) {
			ClassNode cn = cd.bytecodeClass;
			if (!cn.superName.equals(superclass))
				continue;
			if (cn.access!=access)
				continue;
			int nonstatic=0;
			for (FieldNode fn : cn.fields) {
				if (!fn.isStatic())
					nonstatic++;
				for (FieldDescription fs : fields) {
					if (!fs.found && fs.isField(fn)) {
						fs.found=true;
						break;
					}
				}
			}
			if (nonstatic==7 && fieldsIdentified() && isMatch(cn, rasterizer2DClassMethods)) {
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

	@Override
	public void identifyFields(HashMap<String, FieldData> references) {
		try{
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void identifyMethods(HashMap<String, MethodData> referenceInfo) {
	}
}
