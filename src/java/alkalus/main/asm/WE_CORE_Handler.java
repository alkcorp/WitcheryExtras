package alkalus.main.asm;

import java.io.File;
import java.io.IOException;

import alkalus.main.asm.transformer.ClassTransformer_Witchery_NEIWitcheryConfig;
import alkalus.main.asm.transformer.ClassTransformer_Witchery_PacketPipeline;
import alkalus.main.asm.transformer.ClassTransformer_Witchery_TileEntityPoppetShelf;
import alkalus.main.asm.transformer.ClassTransformer_Witchery_TileEntityWitchesCauldron;
import alkalus.main.asm.transformer.ClassTransformer_Witchery_TileEntityWitchesOven;
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

		// Fix Bad NEI Handling, by patching it out entirely.
		if (transformedName.equals("com.emoniph.witchery.integration.NEIWitcheryConfig") && mConfig.enablePatchNEI) {
			return new ClassTransformer_Witchery_NEIWitcheryConfig(transformedName, basicClass, obfuscated).getWriter().toByteArray();
			//return AsmUtils.getClassBytes("com.emoniph.witchery.integration.NEIWitcheryConfig");
		}
		
		// Patch witches oven to support recipe maps.
		if (transformedName.equals("com.emoniph.witchery.blocks.BlockWitchesOven$TileEntityWitchesOven")) {
			return new ClassTransformer_Witchery_TileEntityWitchesOven(transformedName, basicClass, obfuscated).getWriter().toByteArray();
		}	
		
		// Patch Poppet Shelf to enable/disable chunk loading
		if (transformedName.equals("com.emoniph.witchery.blocks.BlockPoppetShelf$TileEntityPoppetShelf")) {
			return new ClassTransformer_Witchery_TileEntityPoppetShelf(transformedName, basicClass, obfuscated).getWriter().toByteArray();
		}	
		
		// Patch Witches Cauldron to allow other heating blocks
		if (transformedName.equals("com.emoniph.witchery.brewing.TileEntityCauldron")) {
			return new ClassTransformer_Witchery_TileEntityWitchesCauldron(transformedName, basicClass, obfuscated).getWriter().toByteArray();
		}
		
		// Patch potential bad packet handling
		if (transformedName.equals("com.emoniph.witchery.network.PacketPipeline")) {
			return new ClassTransformer_Witchery_PacketPipeline(transformedName, basicClass, obfuscated).getWriter().toByteArray();
		}
		
		return basicClass;
	}

	
	
}
