package alkalus.main.core.proxy;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.predictions.Prediction;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.recipe.CustomRecipes;
import alkalus.main.core.recipe.WitcheryRecipe;
import alkalus.main.core.types.*;

public class Proxy_Common {
	
	public void preInit(final FMLPreInitializationEvent e) {
		
	}

	public void init(final FMLInitializationEvent e) {	
		WitcheryExtras.setMinitevents(new CustomRecipes());
	}	

	public synchronized void postInit(final FMLPostInitializationEvent e) {	
		
		//Unit Tests
		WitcheryRecipe.removeKettleRecipe(Witchery.Items.GENERIC.itemHappenstanceOil.createStack());
		WitcheryRecipe.removeDistilleryRecipe(Witchery_Distillery.findRecipeUsing(new ItemStack(Items.diamond)));
		WitcheryRecipe.removeRiteFromRiteRegistry((byte) 1);
		WitcheryRecipe.removeInfusion(Witchery_Infusion.getInfusion(3));
		for (Prediction H : Witchery_Predictions.getPredictions().values()) {
			WitcheryRecipe.removePrediction(H);
		}		
		WitcheryRecipe.removeCreaturePower(Witchery_CreaturePower.getCreaturePower(Witchery_CreaturePower.getLastUsedCreaturePowerID()));

	}
}
