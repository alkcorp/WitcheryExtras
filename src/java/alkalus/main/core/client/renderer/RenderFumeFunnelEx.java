package alkalus.main.core.client.renderer;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.block.BlockFumeFunnelEx;
import alkalus.main.core.client.model.ModelFumeFunnelEx;

import org.lwjgl.opengl.GL11;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

@SideOnly(Side.CLIENT)
public class RenderFumeFunnelEx extends TileEntitySpecialRenderer {
	final ModelFumeFunnelEx model;
	private static final ResourceLocation TEXTURE_URL;

	public RenderFumeFunnelEx(final boolean filtered) {
		this.model = new ModelFumeFunnelEx(filtered);
	}

	public void renderTileEntityAt(final TileEntity tileEntity, final double d, final double d1, final double d2,
			final float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		final BlockFumeFunnelEx.TileEntityFumeFunnelEx tileEntityYour = (BlockFumeFunnelEx.TileEntityFumeFunnelEx) tileEntity;
		this.renderFumeFunnel(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord,
				tileEntity.zCoord, WitcheryExtras.OVEN_FUMEFUNNEL);
		GL11.glPopMatrix();
	}

	public void renderFumeFunnel(final BlockFumeFunnelEx.TileEntityFumeFunnelEx te, final World world, final int x,
			final int y, final int z, final Block par1BlockBrewingStand) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		this.bindTexture(RenderFumeFunnelEx.TEXTURE_URL);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.0f, -1.0f, 0.0f);
		if (world != null) {
			final int meta = world.getBlockMetadata(x, y, z);
			float rotation = 0.0f;
			switch (meta) {
				case 2 : {
					rotation = 0.0f;
					break;
				}
				case 3 : {
					rotation = 180.0f;
					break;
				}
				case 4 : {
					rotation = 270.0f;
					break;
				}
				case 5 : {
					rotation = 90.0f;
					break;
				}
			}
			GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		}
		this.model.render((Entity) null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f, (TileEntity) te);
		GL11.glPopMatrix();
	}

	static {
		TEXTURE_URL = new ResourceLocation("witchery", "textures/blocks/fumefunnel.png");
	}
}