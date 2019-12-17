package alkalus.main.asm.transformer;

import static org.objectweb.asm.Opcodes.*;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import cpw.mods.fml.relauncher.FMLRelaunchLog;

public abstract class BaseTransformer {

	private final boolean isValid;
	private final ClassReader reader;
	private final ClassWriter writer;

	public BaseTransformer(byte[] basicClass) {
		ClassReader aTempReader = null;
		ClassWriter aTempWriter = null;
		aTempReader = new ClassReader(basicClass);
		aTempWriter = new ClassWriter(aTempReader, ClassWriter.COMPUTE_FRAMES);
		aTempReader.accept(new MethodAdaptor(aTempWriter), 0);
		for (String methodName : getMethodNamesToStrip()) {
			injectMethod(methodName, aTempWriter);			
		}
		if (aTempReader != null && aTempWriter != null) {
			isValid = true;
		} else {
			isValid = false;
		}
		log("Valid? " + isValid + ".");
		reader = aTempReader;
		writer = aTempWriter;
	}

	public final boolean isValidTransformer() {
		return isValid;
	}

	public final ClassReader getReader() {
		return reader;
	}

	public final ClassWriter getWriter() {
		return writer;
	}

	public abstract boolean injectMethod(String aMethodName, ClassWriter cw);

	public abstract String[] getMethodNamesToStrip();
	
	public abstract String[] getMethodDescriptorsToStrip();
	
	public abstract String getTransformerName();

	private void log(String s) {
		FMLRelaunchLog.log("[Witchery++ ASM] "+getTransformerName(), Level.INFO, s);
	}
	
	public class MethodAdaptor extends ClassVisitor {
		
		public MethodAdaptor(ClassVisitor cv) {
			super(ASM5, cv);
			this.cv = cv;
		}


		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			MethodVisitor methodVisitor;
			boolean found = false;
			String[] aNames = getMethodNamesToStrip();
			String[] aDesc = getMethodDescriptorsToStrip();
			boolean checkDesc = aDesc != null && aDesc.length > 0;
			
			for (int i=0;i<getMethodNamesToStrip().length;i++) {
				if (checkDesc) {
					if (desc.equals(aDesc[i])) {
						if (name.equals(aNames[i])) {
							found = true;
							break;
						}
					}
				}
				else {
					if (name.equals(aNames[i])) {
						found = true;
						break;
					}
				}				
			}
			if (!found) {
				methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
			} else {
				methodVisitor = null;
			}
			if (found) {
				log("Found method " + name + ", removing. "+(checkDesc ? "Using matching method desc. '"+checkDesc+"'" : "Did not compare method desc."));
			}
			return methodVisitor;
		}

	}

}
