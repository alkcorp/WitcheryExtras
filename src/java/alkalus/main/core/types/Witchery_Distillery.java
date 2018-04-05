package alkalus.main.core.types;

import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;

import net.minecraft.item.ItemStack;

public class Witchery_Distillery {

	public static synchronized DistilleryRecipe getDistillingResult(ItemStack input1, ItemStack intput2, ItemStack jars){
		return DistilleryRecipes.instance().getDistillingResult(input1, intput2, jars);
	}

	public static synchronized DistilleryRecipe findRecipeFor(ItemStack result){
		return DistilleryRecipes.instance().findRecipeFor(result);
	}

	public static synchronized DistilleryRecipe findRecipeUsing(ItemStack ingredient){
		return DistilleryRecipes.instance().findRecipeUsing(ingredient);
	}

}
