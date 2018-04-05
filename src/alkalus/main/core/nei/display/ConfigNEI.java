package alkalus.main.core.nei.display;

import cpw.mods.fml.common.Mod;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.api.API;
import net.minecraft.item.ItemStack;

import alkalus.main.core.WitcheryExtras;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.util.Config;
import codechicken.nei.api.IConfigureNEI;

public class ConfigNEI implements IConfigureNEI {
	public void loadConfig() {
		if (Config.instance().allowModIntegration && Config.instance().allowNotEnoughItems) {
			API.hideItem(new ItemStack(Witchery.Blocks.OVEN_IDLE));
			API.hideItem(new ItemStack(Witchery.Blocks.OVEN_BURNING));
			API.hideItem(new ItemStack(WitcheryExtras.OVEN_BURNING));
			API.registerRecipeHandler((ICraftingHandler) new WitchesOvenRecipeHandler());
			API.registerUsageHandler((IUsageHandler) new WitchesOvenRecipeHandler());
		}
	}

	public String getName() {
		return WitcheryExtras.class.getAnnotation(Mod.class).name();
	}

	public String getVersion() {
		return WitcheryExtras.class.getAnnotation(Mod.class).version();
	}
}