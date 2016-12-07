package client.updater;

import client.updater.asm.MethodData;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.util.HashMap;

public class DummyParameterFinder {
	public static int findDummy(MethodData method) {
		HashMap<String, Integer> dummies = new HashMap<>();
		//MethodData method = Updater.clientMethods.get(clazz+"."+methodName+methodDesc);
		if (method == null) return -1;
		
		for (MethodData node : method.referencedFrom) {
			for (AbstractInsnNode insn : node.bytecodeMethod.instructions.toArray()) {
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode methodInsn = (MethodInsnNode)insn;
					if (methodInsn.owner.equals(method.CLASS_NAME) && methodInsn.name.equals(method.METHOD_NAME) && methodInsn.desc.equals(method.bytecodeMethod.desc)) {
						AbstractInsnNode previous = insn.getPrevious();
						if (previous.getOpcode()== Opcodes.LDC) {
							LdcInsnNode ldcInsn = (LdcInsnNode)previous;
							int value = (Integer)ldcInsn.cst;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.BIPUSH) {
							IntInsnNode ldcInsn = (IntInsnNode)previous;
							int value = ldcInsn.operand;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_0) {
							int value = 4;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_1) {
							int value = 1;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_2) {
							int value = 2;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_3) {
							int value = 3;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_4) {
							int value = 4;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
						if (previous.getOpcode()== Opcodes.ICONST_5) {
							int value = 5;
							if (value != 0) {
								if (dummies.containsKey(value+""))
									dummies.put(value+"", dummies.get(value+"")+1);
								else
									dummies.put(value+"", 1);
							}
							dummies.put("", 1);
						}
					}
				}
			}
		}

		int max = 0;
		int dummy=-1;
		for (String s : dummies.keySet()) {
			try {
				if (dummies.get(s) > max) {
					dummy= Integer.parseInt(s);
					max = dummies.get(s);
				}
			} catch(Exception e) {}
		}
		return dummy;
	}
}
