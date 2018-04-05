package alkalus.main.core.proxy;

import com.emoniph.witchery.client.renderer.RenderBlockItem;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.block.BlockWitchesOvenEx;
import alkalus.main.core.block.BlockWitchesOvenEx.TileEntityWitchesOvenEx;
import alkalus.main.core.block.BlockWitchesOvenGUIEx;
import alkalus.main.core.client.renderer.RenderWitchesOvenEx;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class Proxy_Client extends Proxy_Common {

	private final static Minecraft mc = Minecraft.getMinecraft();
	public static final synchronized Minecraft getMC() {
		return mc;
	}
	
	public void preInit(final FMLPreInitializationEvent e) {
		super.preInit(e);
		
	}

	public void init(final FMLInitializationEvent e) {
		super.init(e);
		
	}	

	public void postInit(final FMLPostInitializationEvent e) {
		super.postInit(e);
		
	}
	
	@Override
	public void registerRenderers() {
		this.bindRenderer((Class<? extends TileEntity>) TileEntityWitchesOvenEx.class,
				(TileEntitySpecialRenderer) new RenderWitchesOvenEx(), Item.getItemFromBlock(WitcheryExtras.OVEN_IDLE));
	}
	
	private void bindRenderer(final Class<? extends TileEntity> clazz, final TileEntitySpecialRenderer render,
			final Item... items) {
		ClientRegistry.bindTileEntitySpecialRenderer((Class<? extends TileEntity>) clazz, render);
		for (final Item item : items) {
			if (item != null) {
				try {
					MinecraftForgeClient.registerItemRenderer(item,
							(IItemRenderer) new RenderBlockItem(render, (TileEntity) clazz.newInstance()));
				} catch (IllegalAccessException ex) {
				} catch (InstantiationException ex2) {
				}
			}
		}
	}
	
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
			final int y, final int z) {
		switch (ID) {
			case 2 : {
				return new BlockWitchesOvenGUIEx(player.inventory,
						(BlockWitchesOvenEx.TileEntityWitchesOvenEx) world.getTileEntity(x, y, z));
			}
			default : {
				return null;
			}
		}
	}
	
}
