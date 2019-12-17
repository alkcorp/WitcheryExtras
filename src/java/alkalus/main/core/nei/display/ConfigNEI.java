package alkalus.main.core.nei.display;

import com.emoniph.witchery.util.Config;

import alkalus.main.core.WitcheryExtras;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Mod;

public class ConfigNEI implements IConfigureNEI {
	public void loadConfig() {
		if (Config.instance().allowModIntegration && Config.instance().allowNotEnoughItems) {
			//API.hideItem(new ItemStack(Witchery.Blocks.OVEN_IDLE));
			//API.hideItem(new ItemStack(Witchery.Blocks.OVEN_BURNING));
			//API.hideItem(new ItemStack(WitcheryExtras.OVEN_BURNING));
		}
	}

	public String getName() {
		return WitcheryExtras.class.getAnnotation(Mod.class).name();
	}

	public String getVersion() {
		return WitcheryExtras.class.getAnnotation(Mod.class).version();
	}
}