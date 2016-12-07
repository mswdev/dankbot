/******************************************************
 * Created by Marneus901                                *
 * � 2012-2014                                          *
 * **************************************************** *
 * Access to this source is unauthorized without prior  *
 * authorization from its appropriate author(s).        *
 * You are not permitted to release, nor distribute this* 
 * work without appropriate author(s) authorization.    *
 ********************************************************/
package client.updater;

import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;

public class MultiplierFinder {


	public static int findMultiplier(String clazz, String field, FieldData fd) {
		HashMap<String, Integer> multipliers = new HashMap<String, Integer>();
		//FieldData fd = Updater.clientFields.get(clazz+"."+field);
		if (fd != null) {
			for (MethodData md : fd.referencedFrom) {
				MethodNode mn = md.bytecodeMethod;
				for (AbstractInsnNode instr : mn.instructions.toArray()) {
					if (instr.getOpcode() == Opcodes.GETFIELD || instr.getOpcode() == Opcodes.GETSTATIC) {
						FieldInsnNode fieldInsn = (FieldInsnNode)instr;
						if (fieldInsn.owner.equals(clazz) && fieldInsn.name.equals(field)) {
							AbstractInsnNode multiInsn = instr;
							for (int i = (fieldInsn.getOpcode() == Opcodes.GETSTATIC ? 2 : 5); i > 0; --i) {
								if (multiInsn.getOpcode() == Opcodes.IMUL || multiInsn.getNext() == null)
									break;
								multiInsn = multiInsn.getNext();
							}

							if (multiInsn.getOpcode() != Opcodes.IMUL)
								continue;

							for (int i = (fieldInsn.getOpcode() == Opcodes.GETSTATIC ? 2 : 5); i > 0; --i) {
								if (multiInsn.getOpcode() == Opcodes.LDC || multiInsn.getPrevious() == null)
									break;

								if (multiInsn.getOpcode() == Opcodes.AALOAD) {
									multiInsn.getPrevious();
									multiInsn.getPrevious();
									multiInsn.getPrevious();
								}

								multiInsn=multiInsn.getPrevious();
							}

							if (multiInsn.getOpcode() == Opcodes.LDC) {
								LdcInsnNode insn = (LdcInsnNode)multiInsn;
								int value = (Integer) insn.cst;
								if (value != 0 && value % 2 != 0) {
									if (multipliers.containsKey(value+""))
										multipliers.put(value+"", multipliers.get(value+"")+1);
									else
										multipliers.put(value+"", 1);
								}
							}
						}
					}
				}
			}
		}		
		int max = 0;
		int multi=1;

		for (String s : multipliers.keySet()) {
			if (multipliers.get(s)>max) {
				multi= Integer.parseInt(s);
				max = multipliers.get(s);
			}
		}
		return multi;
	}

	public static long findLongMultiplier(String clazz, String field, FieldData fd) {
		HashMap<String, Long> multipliers = new HashMap<>();
		//FieldData fd = Updater.clientFields.get(clazz+"."+field);
		if (fd!=null) {
			for (MethodData md : fd.referencedFrom) {
				MethodNode mn = md.bytecodeMethod;
				for (AbstractInsnNode instr : mn.instructions.toArray()) {
					if (instr.getOpcode() == Opcodes.GETFIELD || instr.getOpcode() == Opcodes.GETSTATIC) {
						FieldInsnNode fieldInsn = (FieldInsnNode)instr;
						if (fieldInsn.owner.equals(clazz) && fieldInsn.name.equals(field)) {
							AbstractInsnNode multiInsn = instr;
							for (int i = (fieldInsn.getOpcode() == Opcodes.GETSTATIC ? 2 : 5); i > 0; --i) {
								if (multiInsn.getOpcode() == Opcodes.LMUL || multiInsn.getNext() == null)
									break;
								multiInsn=multiInsn.getNext();
							}

							if (multiInsn.getOpcode() != Opcodes.LMUL)
								continue;

							for (int i = (fieldInsn.getOpcode() == Opcodes.GETSTATIC ? 2 : 5); i > 0; --i) {
								if (multiInsn.getOpcode() == Opcodes.LDC || multiInsn.getPrevious() == null)
									break;

								if (multiInsn.getOpcode() == Opcodes.AALOAD) {
									multiInsn.getPrevious();
									multiInsn.getPrevious();
									multiInsn.getPrevious();
								}

								multiInsn = multiInsn.getPrevious();
							}

							if (multiInsn.getOpcode() == Opcodes.LDC) {
								LdcInsnNode insn = (LdcInsnNode)multiInsn;
								long value = (Long) insn.cst;
								if (value !=0 && value % 2 != 0) {
									if (multipliers.containsKey(value+""))
										multipliers.put(value+"", multipliers.get(value+"")+1);
									else
										multipliers.put(value+"", 1L);
								}
							}
						}
					}
				}
			}
		}		
		long max = 0;
		long multi=1;

		for (String s : multipliers.keySet()) {
			if (multipliers.get(s)>max) {
				multi= Long.parseLong(s);
				max = multipliers.get(s);
			}
		}
		return multi;
	}
}
