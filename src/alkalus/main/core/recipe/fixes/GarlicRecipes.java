package alkalus.main.core.recipe.fixes;

import com.emoniph.witchery.Witchery;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import alkalus.main.core.util.Utils;

public class GarlicRecipes implements Runnable {

	@Override
	public void run() {
		fixGarlicArmour();
		fixVampireBook();
	}
	
	private void fixGarlicArmour() {
		ItemStack[] aStack = Utils.getAllItemsFromOreDictEntry("cropGarlic", new ItemStack[] {new ItemStack(Witchery.Items.SEEDS_GARLIC)});
		for (ItemStack garlic : aStack) {
			Item[][] hunterItemsSilvered = { { Witchery.Items.HUNTER_BOOTS_SILVERED, Witchery.Items.HUNTER_BOOTS_GARLICKED }, { Witchery.Items.HUNTER_LEGS_SILVERED, Witchery.Items.HUNTER_LEGS_GARLICKED }, { Witchery.Items.HUNTER_COAT_SILVERED, Witchery.Items.HUNTER_COAT_GARLICKED }, { Witchery.Items.HUNTER_HAT_SILVERED, Witchery.Items.HUNTER_HAT_GARLICKED } };
		    for (int i = 0; i < hunterItemsSilvered.length; i++) {
		      CraftingManager.getInstance().addRecipe(new ItemStack(hunterItemsSilvered[i][1]), new Object[] { " g ", "g#g", " s ", Character.valueOf('#'), new ItemStack(hunterItemsSilvered[i][0]), Character.valueOf('s'), new ItemStack(Items.string), Character.valueOf('g'), garlic.copy()}).func_92100_c();
		    }	
		}
	}
	
	
	private void fixVampireBook() {
		ItemStack[] aStack = Utils.getAllItemsFromOreDictEntry("cropGarlic", new ItemStack[] {new ItemStack(Witchery.Items.SEEDS_GARLIC)});
		for (ItemStack garlic : aStack) {
			GameRegistry.addShapedRecipe(new ItemStack(Witchery.Items.VAMPIRE_BOOK), new Object[] { "#s#", "#b#", "#g#", Character.valueOf('s'), new ItemStack(Items.nether_star), Character.valueOf('b'), new ItemStack(Items.book), Character.valueOf('g'), garlic.copy(), Character.valueOf('#'), new ItemStack(Items.nether_wart) });
		}		
	}

	
	
}
