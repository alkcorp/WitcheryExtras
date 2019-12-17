package alkalus.main.asm;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

@SuppressWarnings("static-access")
public class WE_CORE_Handler implements IClassTransformer {

	
	public static final AsmConfig mConfig;

	static {
		mConfig = new AsmConfig(new File("config/WitcheryExtras/asm.cfg"));
		System.out.println("[Witchery++][ASM] Asm Config Location: "+mConfig.config.getConfigFile().getAbsolutePath());
	}
	
	private static Boolean mObf = null;
	
	public boolean checkObfuscated() {
		if (mObf != null) {
			return mObf;
		}		
		boolean obfuscated = false;
		try {
			obfuscated = !(boolean) ReflectionHelper.findField(CoreModManager.class, "deobfuscatedEnvironment").get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			byte[] bs;
			try {
				bs = Launch.classLoader.getClassBytes("net.minecraft.world.World");
				if (bs != null) {
					obfuscated = false;
				} else {
					obfuscated = true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				obfuscated = false;
			}
		}	
		mObf = obfuscated;
		return obfuscated;
	}

	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		// Is this environment obfuscated? (Extra checks just in case some weird shit happens during the check)
		boolean obfuscated = checkObfuscated();

		// Fix Bad NEI Handling
		if (transformedName.equals("com.emoniph.witchery.integration.NEIWitcheryConfig") && mConfig.enablePatchNEI) {	
			//FMLRelaunchLog.log("[GT++ ASM] LWJGL Keybinding index out of bounds fix", Level.INFO, "Transforming %s", transformedName);
			//return new ClassTransformer_Witchery_NEIWitcheryConfig(basicClass).getWriter().toByteArray();
			return AsmUtils.getClassBytes("alkalus.main.asm.AsmConfig.BlankClassData");
		}		
		
		return basicClass;
	}

	
	
}
