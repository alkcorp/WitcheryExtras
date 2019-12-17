package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class ClassTransformer_Witchery_NEIWitcheryConfig extends BaseTransformer {

	public ClassTransformer_Witchery_NEIWitcheryConfig(byte[] basicClass) {
		super(basicClass);
	}

	public boolean injectMethod(String aMethodName, ClassWriter cw) {
		MethodVisitor mv;
		boolean didInject = false;
		if (aMethodName.equals("loadConfig")) {
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_SYNCHRONIZED, "getKeyName", "(I)Ljava/lang/String;", null,
					null);
			mv.visitCode();
			mv = cw.visitMethod(ACC_PUBLIC, "loadConfig", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(21, l0);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lcom/emoniph/witchery/integration/NEIWitcheryConfig;", null, l0, l1, 0);
			mv.visitMaxs(0, 1);
			mv.visitEnd();
			didInject = true;
		}

		return didInject;
	}

	@Override
	public String[] getMethodNamesToStrip() {
		return new String[] {"loadConfig"};
	}

	@Override
	public String[] getMethodDescriptorsToStrip() {
		return null;
	}

	@Override
	public String getTransformerName() {
		return "Witchery++ NEI Fix";
	}

}
