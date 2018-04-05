package alkalus.main.core.util;

import java.util.Set;

import com.emoniph.witchery.util.WitcheryConfigGui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory {
	public void initialize(final Minecraft minecraftInstance) {
	}

	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return (Class<? extends GuiScreen>) WitcheryConfigGui.class;
	}

	public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(
			final IModGuiFactory.RuntimeOptionCategoryElement element) {
		return null;
	}
}