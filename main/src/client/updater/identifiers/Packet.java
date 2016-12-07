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

public class Packet extends ClassIdentifier {
	private static MethodDescription packetClassMethods[] = new MethodDescription[]{
		new MethodDescription("V", new String[]{}),
		new MethodDescription("V", new String[]{"?"}),
	};
    @Override
	public void identify(HashMap<String, ClassData> clientClasses) {
		try{
			access=49;
			superclass=identifiedClientClasses.get("ByteBuffer").name;
			fields.add(new FieldDescription(0, "I"));
			fields.add(new FieldDescription(0, "L"+identifiedClientClasses.get("ISAACCipher").name+";"));
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
				if (fieldsIdentified() && isMatch(cn, packetClassMethods)) {
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
		try{
			if (identifiedClass2==null) {
				System.out.println("Identified class is null");
				return;
			}
			FieldSigniture cipher[] = new FieldSigniture[]{
			};
			FieldSigniture bitOffset[] = new FieldSigniture[]{
			};
			for (FieldNode fn : identifiedClass2.fields) {
				if (fn.desc.equals("L"+identifiedClientClasses.get("ISAACCipher").name+";") && fn.access==0) {
					FieldData fd = references.get(identifiedClass2.name+"."+fn.name);
					if (fd!=null) {
						if (fd.isMatch(cipher)) {
							System.out.println("-^* "+identifiedClass2.name+"."+fn.name+" identified as getCipher");
							identifiedFields.put("cipher", fd);
							continue;
						}
					}
				}
				if (fn.desc.equals("I") && fn.access==0) {
					FieldData fd = references.get(identifiedClass2.name+"."+fn.name);
					if (fd!=null) {
						if (fd.isMatch(bitOffset)) {
							System.out.println("-^* "+identifiedClass2.name+"."+fn.name+" identified as getBitOffset");
							identifiedFields.put("bitOffset", fd);
							continue;
						}
					}
				}
			}
			if (identifiedFields.size()<2) {
				if (!identifiedFields.containsKey("getBitOffset"))
					System.out.println("Failed to identify "+this.getClass().getSimpleName()+"."+"getBitOffset"+"!");
				if (!identifiedFields.containsKey("getCipher"))
					System.out.println("Failed to identify "+this.getClass().getSimpleName()+"."+"getCipher"+"!");
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
