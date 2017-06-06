package afdw.customport;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.*;

public class ClassTransformer implements IClassTransformer {
    private static final Logger LOGGER = LogManager.getLogger("CustomPort");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        for (boolean mcp : new boolean[] {false, true}) {
            String classGuiShareToLan = mcp ? "net/minecraft/client/gui/GuiShareToLan" : "bhp";

            String classGuiTextField = mcp ? "net/minecraft/client/gui/GuiTextField" : "bfq";
            String classGuiTextFieldMethodTextboxKeyTyped = mcp ? "textboxKeyTyped" : "a";
            String classGuiTextFieldMethodGetText = mcp ? "getText" : "b";
            String classGuiTextFieldMethodSetText = mcp ? "setText" : "a";
            String classGuiTextFieldMethodMouseClicked = mcp ? "mouseClicked" : "a";
            String classGuiTextFieldMethodDrawTextBox = mcp ? "drawTextBox" : "g";

            String classGuiScreen = mcp ? "net/minecraft/client/gui/GuiScreen" : "bho";
            String classGuiScreenFieldFontRenderer = mcp ? "fontRenderer" : "q";
            String classGuiScreenFieldWidth = mcp ? "width" : "l";
            String classGuiScreenMethodMouseClicked = mcp ? "mouseClicked" : "a";
            String classGuiScreenMethodKeyTyped = mcp ? "keyTyped" : "a";
            String classGuiScreenMethodInitGui = mcp ? "initGui" : "b";
            String classGuiScreenMethodDrawScreen = mcp ? "drawScreen" : "a";

            String classHttpUtil = mcp ? "net/minecraft/util/HttpUtil" : "ol";
            String classHttpUtilMethodGetSuitableLanPort = mcp ? "getSuitableLanPort" : "a";

            String classFontRenderer = mcp ? "net/minecraft/client/gui/FontRenderer" : "bfg";

            String classGui = mcp ? "net/minecraft/client/gui/Gui" : "bfi";
            String classGuiMethodDrawCenteredString = mcp ? "drawCenteredString" : "a";

            if (name.equals(classGuiShareToLan.replace("/", "."))) {
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                new ClassReader(basicClass).accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        cv.visit(version, access, name, signature, superName, interfaces);

                        visitField(
                            Opcodes.ACC_PUBLIC,
                            "portField",
                            "L" + classGuiTextField + ";",
                            null,
                            null
                        ).visitEnd();

                        {
                            MethodVisitor mv = visitMethod(
                                Opcodes.ACC_PROTECTED,
                                classGuiScreenMethodKeyTyped,
                                "(CI)V",
                                null,
                                new String[] {"java/io/IOException"}
                            );
                            mv.visitCode();
                            Label returnLabel = new Label();
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(
                                Opcodes.GETFIELD,
                                classGuiShareToLan,
                                "portField",
                                "L" + classGuiTextField + ";"
                            );
                            mv.visitVarInsn(Opcodes.ILOAD, 1);
                            mv.visitVarInsn(Opcodes.ILOAD, 2);
                            mv.visitMethodInsn(
                                Opcodes.INVOKEVIRTUAL,
                                classGuiTextField,
                                classGuiTextFieldMethodTextboxKeyTyped,
                                "(CI)Z",
                                false
                            );
                            mv.visitJumpInsn(Opcodes.IFEQ, returnLabel);
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitVarInsn(Opcodes.ILOAD, 1);
                            mv.visitVarInsn(Opcodes.ILOAD, 2);
                            mv.visitMethodInsn(
                                Opcodes.INVOKESPECIAL,
                                classGuiScreen,
                                classGuiScreenMethodKeyTyped,
                                "(CI)V",
                                false
                            );
                            mv.visitLabel(returnLabel);
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(
                                Opcodes.GETFIELD,
                                classGuiShareToLan,
                                "portField",
                                "L" + classGuiTextField + ";"
                            );
                            mv.visitMethodInsn(
                                Opcodes.INVOKEVIRTUAL,
                                classGuiTextField,
                                classGuiTextFieldMethodGetText,
                                "()Ljava/lang/String;",
                                false
                            );
                            mv.visitMethodInsn(
                                Opcodes.INVOKESTATIC,
                                "afdw/customport/CustomPort",
                                "setPort",
                                "(Ljava/lang/String;)V",
                                false
                            );
                            mv.visitInsn(Opcodes.RETURN);
                            mv.visitMaxs(0, 0);
                            mv.visitEnd();
                        }

                        {
                            MethodVisitor mv = visitMethod(
                                Opcodes.ACC_PROTECTED,
                                classGuiScreenMethodMouseClicked,
                                "(III)V",
                                null,
                                new String[] {"java/io/IOException"}
                            );
                            mv.visitCode();
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitVarInsn(Opcodes.ILOAD, 1);
                            mv.visitVarInsn(Opcodes.ILOAD, 2);
                            mv.visitVarInsn(Opcodes.ILOAD, 3);
                            mv.visitMethodInsn(
                                Opcodes.INVOKESPECIAL,
                                classGuiScreen,
                                classGuiScreenMethodMouseClicked,
                                "(III)V",
                                false
                            );
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(
                                Opcodes.GETFIELD,
                                classGuiShareToLan,
                                "portField",
                                "L" + classGuiTextField + ";"
                            );
                            mv.visitVarInsn(Opcodes.ILOAD, 1);
                            mv.visitVarInsn(Opcodes.ILOAD, 2);
                            mv.visitVarInsn(Opcodes.ILOAD, 3);
                            mv.visitMethodInsn(
                                Opcodes.INVOKEVIRTUAL,
                                classGuiTextField,
                                classGuiTextFieldMethodMouseClicked,
                                "(III)V",
                                false
                            );
                            mv.visitInsn(Opcodes.RETURN);
                            mv.visitMaxs(0, 0);
                            mv.visitEnd();
                        }
                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

                        if (name.equals(classGuiScreenMethodInitGui) && desc.equals("()V")) {
                            methodVisitor = new MethodVisitor(Opcodes.ASM5, methodVisitor) {
                                @Override
                                public void visitInsn(int opcode) {
                                    if (opcode == Opcodes.RETURN) {
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitTypeInsn(
                                            Opcodes.NEW,
                                            classGuiTextField
                                        );
                                        mv.visitInsn(Opcodes.DUP);
                                        mv.visitLdcInsn(0);
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitFieldInsn(
                                            Opcodes.GETFIELD,
                                            classGuiScreen,
                                            classGuiScreenFieldFontRenderer,
                                            "L" + classFontRenderer + ";"
                                        );
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitFieldInsn(
                                            Opcodes.GETFIELD,
                                            classGuiScreen,
                                            classGuiScreenFieldWidth,
                                            "I"
                                        );
                                        mv.visitLdcInsn(2);
                                        mv.visitInsn(Opcodes.IDIV);
                                        mv.visitLdcInsn(150);
                                        mv.visitInsn(Opcodes.ISUB);
                                        mv.visitLdcInsn(150);
                                        mv.visitLdcInsn(300);
                                        mv.visitLdcInsn(20);
                                        mv.visitMethodInsn(
                                            Opcodes.INVOKESPECIAL,
                                            classGuiTextField,
                                            "<init>",
                                            "(IL" + classFontRenderer + ";IIII)V",
                                            false
                                        );
                                        mv.visitInsn(Opcodes.DUP);
                                        mv.visitMethodInsn(
                                            Opcodes.INVOKESTATIC,
                                            "afdw/customport/CustomPort",
                                            "getPort",
                                            "()Ljava/lang/String;",
                                            false
                                        );
                                        mv.visitMethodInsn(
                                            Opcodes.INVOKEVIRTUAL,
                                            classGuiTextField,
                                            classGuiTextFieldMethodSetText,
                                            "(Ljava/lang/String;)V",
                                            false
                                        );
                                        mv.visitFieldInsn(
                                            Opcodes.PUTFIELD,
                                            classGuiShareToLan,
                                            "portField",
                                            "L" + classGuiTextField + ";"
                                        );
                                    }
                                    mv.visitInsn(opcode);
                                }
                            };
                        }

                        if (name.equals(classGuiScreenMethodDrawScreen) && desc.equals("(IIF)V")) {
                            methodVisitor = new MethodVisitor(Opcodes.ASM5, methodVisitor) {
                                @Override
                                public void visitInsn(int opcode) {
                                    if (opcode == Opcodes.RETURN) {
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitFieldInsn(
                                            Opcodes.GETFIELD,
                                            classGuiScreen,
                                            classGuiScreenFieldFontRenderer,
                                            "L" + classFontRenderer + ";"
                                        );
                                        mv.visitLdcInsn("Port:");
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitFieldInsn(
                                            Opcodes.GETFIELD,
                                            classGuiScreen,
                                            classGuiScreenFieldWidth,
                                            "I"
                                        );
                                        mv.visitLdcInsn(2);
                                        mv.visitInsn(Opcodes.IDIV);
                                        mv.visitLdcInsn(132);
                                        mv.visitLdcInsn(0xffffff);
                                        mv.visitMethodInsn(
                                            Opcodes.INVOKEVIRTUAL,
                                            classGui,
                                            classGuiMethodDrawCenteredString,
                                            "(L" + classFontRenderer + ";Ljava/lang/String;III)V",
                                            false
                                        );
                                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                                        mv.visitFieldInsn(
                                            Opcodes.GETFIELD,
                                            classGuiShareToLan,
                                            "portField",
                                            "L" + classGuiTextField + ";"
                                        );
                                        mv.visitMethodInsn(
                                            Opcodes.INVOKEVIRTUAL,
                                            classGuiTextField,
                                            classGuiTextFieldMethodDrawTextBox,
                                            "()V",
                                            false
                                        );
                                    }
                                    mv.visitInsn(opcode);
                                }
                            };
                        }

                        return methodVisitor;
                    }
                }, 0);
                LOGGER.info("Transformed: " + transformedName);
                LOGGER.info("Original name: " + name);
                return classWriter.toByteArray();
            }

            if (name.equals(classHttpUtil.replace("/", "."))) {
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                new ClassReader(basicClass).accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

                        if (name.equals(classHttpUtilMethodGetSuitableLanPort) && desc.equals("()I")) {
                            methodVisitor = new MethodVisitor(Opcodes.ASM5, methodVisitor) {
                                @Override
                                public void visitCode() {
                                    mv.visitCode();
                                    Label startLabel = new Label();
                                    Label endLabel = new Label();
                                    Label handlerLabel = new Label();
                                    mv.visitTryCatchBlock(
                                        startLabel,
                                        endLabel,
                                        handlerLabel,
                                        "java/lang/NumberFormatException"
                                    );
                                    mv.visitLabel(startLabel);
                                    mv.visitMethodInsn(
                                        Opcodes.INVOKESTATIC,
                                        "afdw/customport/CustomPort",
                                        "getPort",
                                        "()Ljava/lang/String;",
                                        false
                                    );
                                    mv.visitMethodInsn(
                                        Opcodes.INVOKESTATIC,
                                        "java/lang/Integer",
                                        "parseInt",
                                        "(Ljava/lang/String;)I",
                                        false
                                    );
                                    mv.visitInsn(Opcodes.DUP);
                                    mv.visitLdcInsn(1);
                                    mv.visitJumpInsn(Opcodes.IF_ICMPLT, handlerLabel);
                                    mv.visitInsn(Opcodes.DUP);
                                    mv.visitLdcInsn(0xFFFF);
                                    mv.visitJumpInsn(Opcodes.IF_ICMPGT, handlerLabel);
                                    mv.visitInsn(Opcodes.IRETURN);
                                    mv.visitLabel(endLabel);
                                    mv.visitLabel(handlerLabel);
                                    mv.visitInsn(Opcodes.POP);
                                }
                            };
                        }

                        return methodVisitor;
                    }
                }, 0);
                LOGGER.info("Transformed: " + transformedName);
                LOGGER.info("Original name: " + name);
                return classWriter.toByteArray();
            }
        }

        return basicClass;
    }
}
