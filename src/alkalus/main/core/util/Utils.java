package alkalus.main.core.util;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

public class Utils {	

	public synchronized static ItemStack getSimpleStack(final Item x, final int i){
		return getSimpleStack(new ItemStack(x), i);
	}

	public synchronized static ItemStack getSimpleStack(final ItemStack x, final int i){
		try {
			final ItemStack r = x.copy();
			r.stackSize = i;
			return r;
		} catch(final Throwable e){
			return null;
		}
	}
	
	public static void registerEvent(Object o){
			MinecraftForge.EVENT_BUS.register(o);
			FMLCommonHandler.instance().bus().register(o);
	}
}
