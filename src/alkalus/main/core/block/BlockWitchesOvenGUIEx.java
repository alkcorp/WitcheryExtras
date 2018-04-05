package alkalus.main.core.block;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;

@SideOnly(Side.CLIENT)
public class BlockWitchesOvenGUIEx extends GuiContainer {
	private static final ResourceLocation field_110410_t;
	private BlockWitchesOvenEx.TileEntityWitchesOvenEx furnaceInventory;

	public BlockWitchesOvenGUIEx(final InventoryPlayer par1InventoryPlayer,
			final BlockWitchesOvenEx.TileEntityWitchesOvenEx par2TileEntityFurnace) {
		super((Container) new BlockWitchesOvenEx.ContainerWitchesOven(par1InventoryPlayer, par2TileEntityFurnace));
		this.furnaceInventory = par2TileEntityFurnace;
	}

	protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
		final String s = (this.furnaceInventory != null)
				? (this.furnaceInventory.hasCustomInventoryName()
						? this.furnaceInventory.getInventoryName()
						: I18n.format(this.furnaceInventory.getInventoryName(), new Object[0]))
				: "";
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2,
				4210752);
	}

	protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(BlockWitchesOvenGUIEx.field_110410_t);
		final int k = (this.width - this.xSize) / 2;
		final int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if (this.furnaceInventory != null) {
			if (this.furnaceInventory.isBurning()) {
				final int i1 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
				this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
			}
			final int i1 = this.furnaceInventory.getCookProgressScaled(24);
			this.drawTexturedModalRect(k + 79, l + 20, 176, 14, i1 + 1, 16);
		}
	}

	static {
		field_110410_t = new ResourceLocation("witchery", "textures/gui/witchesOven.png");
	}
}