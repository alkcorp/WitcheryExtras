package alkalus.main.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import alkalus.main.core.block.BlockWitchesOvenEx;
import alkalus.main.core.block.BlockWitchesOvenEx.TileEntityWitchesOvenEx;
import alkalus.main.core.handler.TooltipHandler;
import alkalus.main.core.recipe.CustomRecipeLoader;
import alkalus.main.core.util.Utils;

public class Proxy_Common implements IGuiHandler{
	
	public void preInit(final FMLPreInitializationEvent e) {
		
	}

	public void init(final FMLInitializationEvent e) {	
		new CustomRecipeLoader();
	}	

	public synchronized void postInit(final FMLPostInitializationEvent e) {	
		Utils.registerEvent(new TooltipHandler());
	}

	public void registerRenderers() {
		
	}
	
	public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
			final int y, final int z) {
		switch (ID) {
			case 0 : {
				return null;
			}
			case 2 : {
				return new BlockWitchesOvenEx.ContainerWitchesOven(player.inventory,
						(TileEntityWitchesOvenEx) world.getTileEntity(x, y, z));
			}
			default : {
				return null;
			}
		}
	}

	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
			final int y, final int z) {
		return null;
	}
}
