package client.updater;

import client.api.util.Logging;
import client.api.util.Timer;
import client.updater.asm.ClassData;
import client.updater.asm.FieldData;
import client.updater.asm.MethodData;
import client.updater.asm.assembly.Assembly;
import client.updater.asm.assembly.Mask;
import client.updater.asm.controlflow.RedundantMethod.MethodRemoval;
import client.updater.identifiers.*;
import client.updater.identifiers.Queue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Spencer on 11/12/2016.
 */
public class Updater {

    private HashMap<String, ClassNode> classNodes = new HashMap<>();
    private HashMap<String, ClassData> clientClasses = new HashMap<>();
    private HashMap<String, FieldData> clientFields = new HashMap<>();
    private HashMap<String, MethodData> clientMethods = new HashMap<>();

    private int clientVersion = -1;

    public Updater(final String JAR_LOCATION) {
        HashMap<String, byte[]> classFiles = new HashMap<>();
        try {
            JarFile theJar = new JarFile(JAR_LOCATION);
            Enumeration<?> en = theJar.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = (JarEntry) en.nextElement();
                if (entry.getName().startsWith("META"))
                    continue;

                byte[] buffer = new byte[1024];
                int read;
                InputStream is = theJar.getInputStream(entry);
                byte[] allByteData = new byte[0];
                while ((read = is.read(buffer)) != -1) {
                    byte[] tempBuff = new byte[read + allByteData.length];
                    for (int i = 0; i < allByteData.length; ++i)
                        tempBuff[i] = allByteData[i];
                    for (int i = 0; i < read; ++i)
                        tempBuff[i + allByteData.length] = buffer[i];
                    allByteData = tempBuff;
                }
                classFiles.put(entry.getName(), allByteData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (String classFile : classFiles.keySet()) {
            if (classFile.endsWith(".class")) {
                byte[] bytes = classFiles.get(classFile);
                String name = classFile.substring(0, classFile.indexOf(".class"));

                ClassReader classReader = new ClassReader(bytes);
                ClassNode classNode = new ClassNode();
                classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                classNodes.put(name, classNode);
            }
        }
    }

    public void run() {
        ClassNode clientNode = classNodes.get("client");
        for (MethodNode methodNode : clientNode.methods) {
            if (methodNode.name.equals("init")) {
                List<AbstractInsnNode> pattern = Assembly.find(methodNode,
                        Mask.SIPUSH.operand(765), //Applet width
                        Mask.SIPUSH.operand(503), //Applet height
                        //Client version
                        //Dummy
                        Mask.INVOKEVIRTUAL.distance(3)); //initializeApplet

                if (pattern != null) {
                    IntInsnNode appWidth = (IntInsnNode) pattern.get(0);
                    IntInsnNode appHeight = (IntInsnNode) pattern.get(1);
                    AbstractInsnNode clientVersion = appHeight.getNext();
                    if (clientVersion instanceof IntInsnNode) {
                        this.clientVersion = ((IntInsnNode) clientVersion).operand;
                        break;
                    }
                }
            }
        }

        if (clientVersion == -1) {
            Logging.error("Runescape client version not found.");
            return;
        } else {
            Logging.status("Runescape client version: " + clientVersion);
        }

        //Check if current hooks = this version else update

        Timer timer = new Timer();
        Logging.status(" - Removing redundant methods");
        MethodRemoval methodRemover = new MethodRemoval(classNodes);
        methodRemover.refactor();
        Logging.status(" -* Finished removing redunant methods " + timer.getElapsedAndSet() + "ms");

        Logging.status(" - Parsing client classes");
        for (ClassNode classNode : classNodes.values()) {
            clientClasses.put(classNode.name, new ClassData(classNode));
        }
        Logging.status(" -* Done parsing " + clientClasses.size() + " client classes " + timer.getElapsedAndSet() + "ms");

        Logging.status(" - Building Node Pool");
        int linkCounter = 0;
        Logging.status(" - - Parsing client fields");
        for (ClassNode classNode : classNodes.values()) {
            ClassData classData = clientClasses.get(classNode.name);
            for (FieldNode fieldNode : classNode.fields) {
                FieldData fieldData = new FieldData(classNode.name, fieldNode);
                clientFields.put(classNode.name + "." + fieldNode.name, fieldData);
                classData.addField(fieldData);
                linkCounter++;
            }

            clientClasses.put(classData.CLASS_NAME, classData);
        }

        Logging.status(" - -* Done parsing " + clientFields.size() + " client fields " + timer.getElapsedAndSet() + "ms");

        Logging.status(" - - Parsing client methods");
        for (ClassNode classNode : classNodes.values()) {
            ClassData cd = clientClasses.get(classNode.name);
            for (MethodNode methodNode : classNode.methods) {
                MethodData methodData = new MethodData(classNode.name, methodNode);
                clientMethods.put(classNode.name + "." + methodNode.name + methodNode.desc, methodData);
                cd.addMethod(methodData);
                linkCounter++;
            }
            clientClasses.put(cd.CLASS_NAME, cd);
        }
        Logging.status(" - -* Done parsing " + clientMethods.size() + " client methods " + timer.getElapsedAndSet() + "ms");
        Logging.status(" -* Building node pool complete. Found " + linkCounter + " node links in " + timer.getTotalTime() + "ms");

        Logging.status(" - Building reference tree");
        int fieldCount = 0;
        int invokeCount = 0;
        int checkcastCount = 0;

        HashMap<String, ClassData> refreshClasses = new HashMap<String, ClassData>();
        HashMap<String, MethodData> refreshMethods = new HashMap<String, MethodData>();
        HashMap<String, FieldData> refreshFields = new HashMap<String, FieldData>();
        for (MethodData methodData : clientMethods.values()) {
            for (AbstractInsnNode instr : methodData.bytecodeMethod.instructions.toArray()) {
                if (instr.getOpcode() == Opcodes.GETFIELD || instr.getOpcode() == Opcodes.PUTFIELD || instr.getOpcode() == Opcodes.GETSTATIC || instr.getOpcode() == Opcodes.PUTSTATIC) {
                    FieldInsnNode fins = (FieldInsnNode) instr;
                    if (clientFields.containsKey(fins.owner + "." + fins.name)) {
                        FieldData fieldData = clientFields.get(fins.owner + "." + fins.name);
                        if (fieldData != null) {
                            fieldData.addReferenceFrom(methodData);
                            methodData.addFieldReference(fieldData);
                            refreshFields.put(fins.owner + "." + fins.name, fieldData);
                            fieldCount++;
                        }
                    }
                }
                if (instr.getOpcode() == Opcodes.INVOKEDYNAMIC || instr.getOpcode() == Opcodes.INVOKEINTERFACE
                        || instr.getOpcode() == Opcodes.INVOKESPECIAL || instr.getOpcode() == Opcodes.INVOKESTATIC
                        || instr.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                    MethodInsnNode mins = (MethodInsnNode) instr;
                    if (clientMethods.containsKey(mins.owner + "." + mins.name + mins.desc)) {
                        MethodData methodInvoking = clientMethods.get(mins.owner + "." + mins.name + mins.desc);
                        methodData.addMethodReference(methodInvoking);
                        methodInvoking.addReferenceFrom(methodData);
                        refreshMethods.put(methodInvoking.real_attributes(), methodInvoking);
                        invokeCount++;
                    }
                }
                if (instr.getOpcode() == Opcodes.CHECKCAST) {
                    TypeInsnNode insn = (TypeInsnNode) instr;
                    ClassData classData = clientClasses.get(insn.desc);
                    if (classData != null) {
                        classData.addMethod(methodData);
                        refreshClasses.put(classData.CLASS_NAME, classData);
                        checkcastCount++;
                    }
                }
            }
            refreshMethods.put(methodData.real_attributes(), methodData);
        }

        Logging.status(" -* Built reference tree with " + (fieldCount + invokeCount + checkcastCount) + " total references in " + timer.getElapsedAndSet() + "ms");
        Logging.status(" -# Field References: " + fieldCount + " Invokes: " + invokeCount + " Checkcasts: " + checkcastCount);

        Logging.status(" -* Refreshing Node Pool.");
        int nodeCounter = 0;
        for (ClassData classData : refreshClasses.values()) {
            clientClasses.put(classData.CLASS_NAME, classData);
            nodeCounter++;
        }
        for (MethodData methodData : refreshMethods.values()) {
            clientMethods.put(methodData.real_attributes(), methodData);
            nodeCounter++;
        }
        for (FieldData fieldData : refreshFields.values()) {
            clientFields.put(fieldData.CLASS_NAME + "." + fieldData.FIELD_NAME, fieldData);
            nodeCounter++;
        }
        Logging.status(" -* Refreshed Node Pool. " + nodeCounter + " total nodes." + timer.getElapsedAndSet() + "ms");

        ArrayList<ClassIdentifier> identifiers = new ArrayList<>();
        HashMap<String, ClassNode> identifiedClasses = new HashMap<>();

        Logging.status(" - Loading identifiers");

        Collections.addAll(identifiers, new Node(), new HashTable(), new CacheNode(),
                new RenderableNode(), new Queue(), new Cache(), new ByteBuffer(),
                new ISAACCipher(), new Packet(), new ReferenceTable(),
                new Rasterizer(), new Rasterizer2D(), new Rasterizer3D(),
                new World(), new IdentityKit(), new Client());

        Logging.status(" -* Loaded " + identifiers.size() + " identifiers in " + timer.getElapsedAndSet() + "ms");

        int hookCount = 0;
        int methodCount = 0;
        Logging.status(" - Starting identification");

        try {
            for (ClassIdentifier classIdentifier : identifiers) {
                try {
                    classIdentifier.setIdentifiedClasses(identifiedClasses);
                    classIdentifier.identify(clientClasses);
                    if (classIdentifier.identifiedClass2 != null) {
                        if (classIdentifier.identifiedClass != null) {
                            classIdentifier.identifiedClass.setRefactoredName(classIdentifier.getClass().getSimpleName());
                            clientClasses.put(classIdentifier.identifiedClass.CLASS_NAME, classIdentifier.identifiedClass);
                        }

                        identifiedClasses.put(classIdentifier.getClass().getSimpleName(), classIdentifier.identifiedClass2);
                        try {
                            classIdentifier.identifyMethods(clientMethods);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            classIdentifier.identifyFields(clientFields);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        for (String key : classIdentifier.identifiedFields.keySet()) {
                            FieldData fieldData = classIdentifier.identifiedFields.get(key);
                            fieldData.setRefactoredName(key);
                            classIdentifier.identifiedClass.addField(fieldData);
                        }

                        for (String key : classIdentifier.identifiedMethods.keySet()) {
                            MethodData methodData = classIdentifier.identifiedMethods.get(key);
                            methodData.setRefactoredName(key);
                            classIdentifier.identifiedClass.addMethod(methodData);
                        }

                        if (classIdentifier instanceof Client) {
                            hookCount = hookCount + ((Client) classIdentifier).staticFields.size();
                            methodCount = methodCount + ((Client) classIdentifier).staticMethods.size();
                            for (String key : ((Client) classIdentifier).staticFields.keySet()) {
                                FieldData fieldData = ((Client) classIdentifier).staticFields.get(key);
                                if (fieldData != null) {
                                    fieldData.setRefactoredName(key);
                                    clientFields.put(fieldData.CLASS_NAME + "." + fieldData.FIELD_NAME, fieldData);
                                }
                            }
                            for (String key : ((Client) classIdentifier).staticMethods.keySet()) {
                                MethodData methodData = ((Client) classIdentifier).staticMethods.get(key);
                                if (methodData != null) {
                                    methodData.REFACTORED_NAME = key;
                                    clientMethods.put(methodData.real_attributes(), methodData);
                                }
                            }
                        } else {
                            hookCount = hookCount + classIdentifier.identifiedFields.size();
                            methodCount = methodCount + classIdentifier.identifiedMethods.size();
                            for (String key : classIdentifier.identifiedFields.keySet()) {
                                FieldData fd = classIdentifier.identifiedFields.get(key);
                                clientFields.put(fd.CLASS_NAME + "." + fd.FIELD_NAME, fd);
                                classIdentifier.identifiedClass.addField(fd);
                            }
                            for (String key : classIdentifier.identifiedMethods.keySet()) {
                                MethodData md = classIdentifier.identifiedMethods.get(key);
                                clientMethods.put(md.real_attributes(), md);
                                classIdentifier.identifiedClass.addMethod(md);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logging.status(" -# Identified " + identifiedClasses.size() + " classes");
        Logging.status(" -# Identified " + hookCount + " fields");
        Logging.status(" -# Identified " + methodCount + " methods");
    }

    public int findInstanceCount(String desc) {
        int count = 0;
        for (FieldData fd : clientFields.values())
            if (fd.bytecodeField.desc.equals(desc))
                count++;
        return count;
    }
}
