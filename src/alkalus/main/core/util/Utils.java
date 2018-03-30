package alkalus.main.core.util;

import java.util.HashSet;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

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

	public static ItemStack[] getAllItemsFromOreDictEntry(String oreDictName) {
		return getAllItemsFromOreDictEntry(oreDictName, new ItemStack[] {});
	}

	public static ItemStack[] getAllItemsFromOreDictEntry(String oreDictName, ItemStack[] exclusions) {
		HashSet<ItemStack> Q = new HashSet<ItemStack>();		
		if (OreDictionary.doesOreNameExist(oreDictName)) {
			for (ItemStack i : OreDictionary.getOres(oreDictName)) {
				if ( i != null) {
					if (exclusions.length > 0) {					
						for (int r=0;r<exclusions.length;r++) {
							if (!i.equals(exclusions[r]) && !ItemStack.areItemStacksEqual(i, exclusions[r])) {
								Q.add(i);
							}
							else {
								continue;
							}
						}					
					}
					else {
						Q.add(i);
					}
				}				
			}
		}		
		
		ItemStack[] V = new ItemStack[] {};
		int mSlot = 0;
		if (Q.size() > 0) {
			V = new ItemStack[Q.size()];
			for (ItemStack w : Q) {
				V[mSlot++] = w;
			}
		}
		return V;
	}

	public static void registerEvent(Object o){
		MinecraftForge.EVENT_BUS.register(o);
		FMLCommonHandler.instance().bus().register(o);
	}
}
