package alkalus.main.asm;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.util.ArrayList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

public class AsmConfig {
	
	public static boolean loaded;
	public static Configuration config;

	public static boolean enablePatchNEI;
	public static boolean allowPoppetShelfChunkLoading;
	public static double chanceBabaYagaGood;
	public static double chanceBabaYagaBad;
	

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

			// Fixes NEI handling
			prop = config.get("general", "enablePatchNEI", true);
			prop.comment = "Patch NEI for good recipe support.";
			prop.setLanguageKey("enablePatchNEI").setRequiresMcRestart(true);
			enablePatchNEI = prop.getBoolean(true);
			propOrder.add(prop.getName());
			
			// Toggles Chunk Loading for Poppet Shelves
			prop = config.get("general", "allowPoppetShelfChunkLoading", true);
			prop.comment = "Enables Chunk Loading by Poppet Shelves.";
			prop.setLanguageKey("allowPoppetShelfChunkLoading").setRequiresMcRestart(true);
			allowPoppetShelfChunkLoading = prop.getBoolean(true);
			propOrder.add(prop.getName());

			
			// Adjusts chances for Baba Yaga spawns
			prop = config.get("general", "chanceBabaYagaGood", 0.05D);
			prop.comment = "Adjusts chances for 'Good' Baba Yaga spawns";
			prop.setLanguageKey("chanceBabaYagaGood").setRequiresMcRestart(true);
			chanceBabaYagaGood = prop.getDouble(0.05D);
			propOrder.add(prop.getName());
			prop = config.get("general", "chanceBabaYagaBad", 0.05D);
			prop.comment = "Adjusts chances for 'Bad' Baba Yaga spawns";
			prop.setLanguageKey("chanceBabaYagaBad").setRequiresMcRestart(true);
			chanceBabaYagaBad = prop.getDouble(0.05D);
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