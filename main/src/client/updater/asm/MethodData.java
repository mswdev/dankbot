package client.updater.asm;

import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;

/**
 * Created by Greg on 12/29/2014.
 */
public class MethodData {

    public String CLASS_NAME;
    public String METHOD_NAME;
    public String REFACTORED_NAME;
    public String METHOD_DESC;
    public int ACCESS;
    public MethodNode bytecodeMethod;
    public int dummyParameter=-1;
    public ArrayList<MethodData> referencedFrom = new ArrayList<MethodData>();
    public ArrayList<MethodData> methodReferences = new ArrayList<MethodData>();
    public ArrayList<FieldData> fieldReferences = new ArrayList<FieldData>();

    public MethodData(String clazz, MethodNode node) {
        CLASS_NAME = clazz;
        METHOD_NAME = node.name;
        REFACTORED_NAME = node.name;
        METHOD_DESC = node.desc;
        bytecodeMethod = node;
        ACCESS = node.access;
    }
    public void addReferenceFrom(MethodData mn) {
        if (!referencedFrom.contains(mn))
            referencedFrom.add(mn);
    }
    public void addFieldReference(FieldData fn) {
    	if (!fieldReferences.contains(fn))
    		fieldReferences.add(fn);
    }
    public void addMethodReference(MethodData md) {
    	if (!methodReferences.contains(md))
    		methodReferences.add(md);
    }
    public int getDummyParameter() {
    	return dummyParameter;
    }
    public String getReturnType() {
    	return METHOD_DESC.substring(METHOD_DESC.indexOf(")")+1, METHOD_DESC.length());
    }
    public String[] parseArguments() {
        ArrayList<String> args = new ArrayList<String>();
        String signiture = METHOD_DESC;
        signiture = signiture.substring(signiture.indexOf("(") + 1, signiture.indexOf(")"));
        while (signiture != null && !signiture.equals("") && signiture.length() > 0) {
            if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                args.add(signiture.charAt(0) + "");
                signiture = signiture.substring(1, signiture.length());
            } else if (signiture.startsWith("[")) {
                String arg = "[";
                signiture = signiture.substring(1, signiture.length());
                while (signiture.startsWith("[")) {
                    arg += "[";
                    signiture = signiture.substring(1, signiture.length());
                }
                if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                    args.add(arg + signiture.charAt(0) + "");
                    signiture = signiture.substring(1, signiture.length());
                } else {
                    args.add(arg + signiture.substring(0, signiture.indexOf(";") + 1));
                    signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
                }
            } else {
                args.add(signiture.substring(0, signiture.indexOf(";") + 1));
                signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
            }
        }
        return args.toArray(new String[]{});
    }
    public void setDummyParameter(int dummy) {
    	dummyParameter=dummy;
    }
    public boolean isFieldReferenced(FieldData field) {
    	for (FieldData fd : fieldReferences)
    		if (fd.CLASS_NAME.equals(field.CLASS_NAME) && fd.FIELD_NAME.equals(field.FIELD_NAME))
    			return true;
    	return false;
    }
    public boolean isMethodReferenced(MethodData method) {
    	for (MethodData md : methodReferences)
    		if (md.CLASS_NAME.equals(method.CLASS_NAME) && real_attributes().equals(method.real_attributes()))
    			return true;
    	return false;
    }
    public String real_attributes() {
        return CLASS_NAME+"."+METHOD_NAME+METHOD_DESC;
    }
    public void setRefactoredName(String s) {
    	REFACTORED_NAME=s;
    }
    @Override
    public boolean equals(Object o) {
    	if (o instanceof MethodData)
    		return this.real_attributes().equals(((MethodData)o).real_attributes());
    	return false;
    }
}
