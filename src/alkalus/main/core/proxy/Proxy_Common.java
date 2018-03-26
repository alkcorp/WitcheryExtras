package alkalus.main.core.proxy;

import com.emoniph.witchery.Witchery;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.recipe.WitcheryRecipe;
import alkalus.main.core.types.Witchery_CreaturePower;
import alkalus.main.core.types.Witchery_Distillery;

public class Proxy_Common {
	
	public void preInit(final FMLPreInitializationEvent e) {
		WitcheryExtras.log(0, "Common pre-init Stage");
	}

	public void init(final FMLInitializationEvent e) {	
		WitcheryExtras.log(0, "Common init Stage");

	}	

	public synchronized void postInit(final FMLPostInitializationEvent e) {	
		WitcheryExtras.log(0, "Common post-init Stage");	
		
		//Unit Tests
		WitcheryRecipe.removeKettleRecipe(Witchery.Items.GENERIC.itemHappenstanceOil.createStack());
		WitcheryRecipe.removeDistilleryRecipe(Witchery_Distillery.findRecipeUsing(new ItemStack(Items.diamond)));
		WitcheryRecipe.removeCreaturePower(Witchery_CreaturePower.getCreaturePower(Witchery_CreaturePower.getLastUsedCreaturePowerID()));

	}
}
