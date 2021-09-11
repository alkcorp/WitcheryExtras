package alkalus.main.core.util;

import java.util.HashMap;

import cpw.mods.fml.common.Loader;

public class ModCompat {

	private static final HashMap<String, Boolean> LoadedModCache = new HashMap<String, Boolean>();
	
	
	

	public static boolean Thaumcraft = false;
	public static boolean ThaumicTinkerer = false;
	public static boolean ForbiddenMagic = false;
	public static boolean TaintedMagic = false;
	public static boolean WitchingGadgets = false;
	
	public static void checkLoadedMods() {
		if (isModLoaded("Thaumcraft")){
			Thaumcraft = true;
		}
		if (isModLoaded("ThaumicTinkerer")){
			ThaumicTinkerer = true;
		}
		if (isModLoaded("ForbiddenMagic")){
			ForbiddenMagic = true;
		}
		if (isModLoaded("TaintedMagic")){
			TaintedMagic = true;
		}
		if (isModLoaded("WitchingGadgets")){
			WitchingGadgets = true;
		}
	}	

	private static boolean isModLoaded(String aModName) {
		Boolean aResult = LoadedModCache.get(aModName);
		if (aResult == null) {
			boolean aTemp = Loader.isModLoaded(aModName);
			LoadedModCache.put(aModName, aTemp);
			aResult = aTemp;
		}
		return aResult;
	}
	
}
