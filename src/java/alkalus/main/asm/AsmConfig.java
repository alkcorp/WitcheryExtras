package alkalus.main.asm;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.util.ArrayList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

public class AsmConfig {
	
	public static class BlankClassData {
		
	}
	
	public static boolean loaded;
	public static Configuration config;
	
	public static boolean enablePatchNEI;
	

	public AsmConfig(File file) {
		if (!loaded) {
			config = new Configuration(file);
			syncConfig(true);
		}

	}

	public static void syncConfig(boolean load) {
		ArrayList<String> propOrder = new ArrayList<String>();
		ArrayList<String> propOrderDebug = new ArrayList<String>();
		try {
			if (!config.isChild && load) {
				config.load();
			}
			Property prop;			

			prop = config.get("general", "enabledLwjglKeybindingFix", true);
			prop.comment = "Prevents the game crashing from having invalid keybinds. https://github.com/alkcorp/GTplusplus/issues/544";
			prop.setLanguageKey("gtpp.enabledLwjglKeybindingFix").setRequiresMcRestart(true);
			enablePatchNEI = prop.getBoolean(true);
			propOrder.add(prop.getName());

			config.setCategoryPropertyOrder("general", propOrder);
			config.setCategoryPropertyOrder("debug", propOrderDebug);
			if (config.hasChanged()) {
				config.save();
			}
			
		} catch (Exception var3) {
			FMLLog.log(Level.ERROR, var3, "Witchery++ ASM had a problem loading it's config", new Object[0]);
		}

	}
}