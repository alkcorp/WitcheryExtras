package alkalus.main.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import alkalus.main.core.recipe.CustomRecipeLoader;

public class Proxy_Common {
	
	public void preInit(final FMLPreInitializationEvent e) {
		
	}

	public void init(final FMLInitializationEvent e) {	
		new CustomRecipeLoader();
	}	

	public synchronized void postInit(final FMLPostInitializationEvent e) {	

	}
}
