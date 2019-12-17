package alkalus.main.core.types;

import com.emoniph.witchery.crafting.SpinningRecipes;
import com.emoniph.witchery.crafting.SpinningRecipes.SpinningRecipe;

import net.minecraft.item.ItemStack;

public class Witchery_SpinningWheel {

	public SpinningRecipe getRecipe(ItemStack fibre, ItemStack[] modifiers) {
		return SpinningRecipes.instance().getRecipe(fibre, modifiers);
	}

	public SpinningRecipe findRecipeFor(ItemStack result) {
		return SpinningRecipes.instance().findRecipeFor(result);
	}

	public SpinningRecipe findRecipeUsing(ItemStack ingredient) {
		return SpinningRecipes.instance().findRecipeUsing(ingredient);
	}

	public SpinningRecipe findRecipeUsingFibre(ItemStack ingredient) {
		return SpinningRecipes.instance().findRecipeUsingFibre(ingredient);
	}

}
