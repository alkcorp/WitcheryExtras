package alkalus.main.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import alkalus.main.core.proxy.Proxy_Common;
import alkalus.main.core.util.Logger;

@Mod(modid = WitcheryExtras.MODID, name = WitcheryExtras.NAME, version = WitcheryExtras.VERSION, dependencies = "required-after:Forge; after:witchery;")
public class WitcheryExtras
{
	public static final String MODID = "WitcheryExtras";
	public static final String NAME = "Witches Cauldron";
	public static final String VERSION = "0.1";
	private static final Logger log4j = new Logger();
		
	@Mod.Instance(MODID)
	public static WitcheryExtras instance;
	
	@SidedProxy(clientSide = "alkalus.main.core.proxy.Proxy_Client", serverSide = "alkalus.main.core.proxy.Proxy_Server")
	public static Proxy_Common proxy;

	@Mod.EventHandler
	public synchronized void preInit(final FMLPreInitializationEvent e) {
    	log(0, "Loading "+NAME+" - v"+VERSION);
		proxy.preInit(e);
	}

	@Mod.EventHandler
	public synchronized void init(final FMLInitializationEvent e) {
		proxy.init(e);		
	}	

	@Mod.EventHandler
	public synchronized void postInit(final FMLPostInitializationEvent e) {
		proxy.postInit(e);		
	}
	
	public static final void log(int level, String text) {
		if (level<=0) {
			log4j.INFO(text);
		}
		else if (level==1) {
			log4j.WARNING(text);
		}
		else {
			log4j.ERROR(text);
		}
	}
	
}
