package alkalus.main.core.client.model;

import net.minecraft.world.World;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.block.BlockWitchesOvenEx;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelFumeFunnelEx extends ModelBase {
	ModelRenderer chimney;
	ModelRenderer chimneyTop;
	ModelRenderer base;
	ModelRenderer body;
	ModelRenderer tubeLeft;
	ModelRenderer tubeRight;
	ModelRenderer pipeTop2;
	ModelRenderer pipeTop3;
	ModelRenderer pipeTop4;
	ModelRenderer pipeTop5;
	ModelRenderer pipeBottom1;
	ModelRenderer pipeBottom2;
	ModelRenderer pipeBottom3;
	ModelRenderer pipeBottom4;
	ModelRenderer pipeTop1;
	ModelRenderer top1;
	ModelRenderer pipeBottom5;
	ModelRenderer top2;
	ModelRenderer filterLeft;
	ModelRenderer filterRight;
	ModelRenderer filterMid;
	ModelRenderer filterCase;
	final boolean filtered;

	public ModelFumeFunnelEx(final boolean filtered) {
		this.filtered = filtered;
		this.textureWidth = 64;
		this.textureHeight = 64;
		(this.base = new ModelRenderer((ModelBase) this, 0, 51)).addBox(0.0f, 0.0f, 0.0f, 12, 1, 12);
		this.base.setRotationPoint(-6.0f, 23.0f, -6.0f);
		this.base.setTextureSize(64, 64);
		this.base.mirror = true;
		this.setRotation(this.base, 0.0f, 0.0f, 0.0f);
		(this.body = new ModelRenderer((ModelBase) this, 4, 27)).addBox(0.0f, 0.0f, 0.0f, 10, 11, 10);
		this.body.setRotationPoint(-5.0f, 12.0f, -5.0f);
		this.body.setTextureSize(64, 64);
		this.body.mirror = true;
		this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
		(this.tubeLeft = new ModelRenderer((ModelBase) this, 1, 18)).addBox(0.0f, 0.0f, 0.0f, 5, 2, 2);
		this.tubeLeft.setRotationPoint(-10.0f, 17.0f, -1.0f);
		this.tubeLeft.setTextureSize(64, 64);
		this.tubeLeft.mirror = true;
		this.setRotation(this.tubeLeft, 0.0f, 0.0f, 0.0f);
		(this.tubeRight = new ModelRenderer((ModelBase) this, 1, 18)).addBox(0.0f, 1.0f, 0.0f, 5, 2, 2);
		this.tubeRight.setRotationPoint(5.0f, 18.0f, 1.0f);
		this.tubeRight.setTextureSize(64, 64);
		this.tubeRight.mirror = true;
		this.setRotation(this.tubeRight, 0.0f, 0.0f, 0.0f);
		(this.pipeTop2 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 6);
		this.pipeTop2.setRotationPoint(-4.0f, 8.0f, -3.0f);
		this.pipeTop2.setTextureSize(64, 64);
		this.pipeTop2.mirror = true;
		this.setRotation(this.pipeTop2, 0.0f, 0.0f, 0.0f);
		(this.pipeTop3 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 11, 1, 1);
		this.pipeTop3.setRotationPoint(-3.0f, 8.0f, -3.0f);
		this.pipeTop3.setTextureSize(64, 64);
		this.pipeTop3.mirror = true;
		this.setRotation(this.pipeTop3, 0.0f, 0.0f, 0.0f);
		(this.pipeTop4 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 11, 1);
		this.pipeTop4.setRotationPoint(7.0f, 9.0f, -3.0f);
		this.pipeTop4.setTextureSize(64, 64);
		this.pipeTop4.mirror = true;
		this.setRotation(this.pipeTop4, 0.0f, 0.0f, 0.0f);
		(this.pipeTop5 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 3, 3);
		this.pipeTop5.setRotationPoint(5.0f, 18.0f, -4.0f);
		this.pipeTop5.setTextureSize(64, 64);
		this.pipeTop5.mirror = true;
		this.setRotation(this.pipeTop5, 0.0f, 0.0f, 0.0f);
		(this.pipeBottom1 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 1, 1);
		this.pipeBottom1.setRotationPoint(-7.0f, 13.0f, -3.0f);
		this.pipeBottom1.setTextureSize(64, 64);
		this.pipeBottom1.mirror = true;
		this.setRotation(this.pipeBottom1, 0.0f, 0.0f, 0.0f);
		(this.pipeBottom2 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 4);
		this.pipeBottom2.setRotationPoint(-7.0f, 20.0f, -7.0f);
		this.pipeBottom2.setTextureSize(64, 64);
		this.pipeBottom2.mirror = true;
		this.setRotation(this.pipeBottom2, 0.0f, 0.0f, 0.0f);
		(this.pipeBottom3 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 5, 1, 1);
		this.pipeBottom3.setRotationPoint(-6.0f, 20.0f, -7.0f);
		this.pipeBottom3.setTextureSize(64, 64);
		this.pipeBottom3.mirror = true;
		this.setRotation(this.pipeBottom3, 0.0f, 0.0f, 0.0f);
		(this.pipeBottom4 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 3, 1);
		this.pipeBottom4.setRotationPoint(-2.0f, 21.0f, -7.0f);
		this.pipeBottom4.setTextureSize(64, 64);
		this.pipeBottom4.mirror = true;
		this.setRotation(this.pipeBottom4, 0.0f, 0.0f, 0.0f);
		(this.pipeTop1 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 3, 1);
		this.pipeTop1.setRotationPoint(-4.0f, 8.0f, 3.0f);
		this.pipeTop1.setTextureSize(64, 64);
		this.pipeTop1.mirror = true;
		this.setRotation(this.pipeTop1, 0.0f, 0.0f, 0.0f);
		(this.top1 = new ModelRenderer((ModelBase) this, 0, 51)).addBox(0.0f, 0.0f, 0.0f, 12, 1, 12);
		this.top1.setRotationPoint(-6.0f, 11.0f, -6.0f);
		this.top1.setTextureSize(64, 64);
		this.top1.mirror = true;
		this.setRotation(this.top1, 0.0f, 0.0f, 0.0f);
		(this.pipeBottom5 = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 7, 1);
		this.pipeBottom5.setRotationPoint(-7.0f, 14.0f, -3.0f);
		this.pipeBottom5.setTextureSize(64, 64);
		this.pipeBottom5.mirror = true;
		this.setRotation(this.pipeBottom5, 0.0f, 0.0f, 0.0f);
		(this.top2 = new ModelRenderer((ModelBase) this, 37, 55)).addBox(0.0f, 0.0f, 0.0f, 6, 1, 6);
		this.top2.setRotationPoint(-3.0f, 10.0f, -3.0f);
		this.top2.setTextureSize(64, 64);
		this.top2.mirror = true;
		this.setRotation(this.top2, 0.0f, 0.0f, 0.0f);
		(this.filterLeft = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
		this.filterLeft.setRotationPoint(-4.0f, 14.0f, -7.0f);
		this.filterLeft.setTextureSize(64, 64);
		this.filterLeft.mirror = true;
		this.setRotation(this.filterLeft, 0.0f, 0.0f, 0.0f);
		(this.filterRight = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
		this.filterRight.setRotationPoint(3.0f, 14.0f, -7.0f);
		this.filterRight.setTextureSize(64, 64);
		this.filterRight.mirror = true;
		this.setRotation(this.filterRight, 0.0f, 0.0f, 0.0f);
		(this.filterMid = new ModelRenderer((ModelBase) this, 24, 0)).addBox(0.0f, 0.0f, 0.0f, 6, 1, 1);
		this.filterMid.setRotationPoint(-3.0f, 14.0f, -7.0f);
		this.filterMid.setTextureSize(64, 64);
		this.filterMid.mirror = true;
		this.setRotation(this.filterMid, 0.0f, 0.0f, 0.0f);
		(this.filterCase = new ModelRenderer((ModelBase) this, 25, 3)).addBox(0.0f, 0.0f, 0.0f, 4, 3, 2);
		this.filterCase.setRotationPoint(-2.0f, 13.0f, -8.0f);
		this.filterCase.setTextureSize(64, 64);
		this.filterCase.mirror = true;
		this.setRotation(this.filterCase, 0.0f, 0.0f, 0.0f);
		(this.chimney = new ModelRenderer((ModelBase) this, 27, 13)).addBox(0.0f, 0.0f, 0.0f, 4, 10, 4);
		this.chimney.setRotationPoint(-2.0f, 14.0f, 3.0f);
		this.chimney.setTextureSize(64, 128);
		this.chimney.mirror = true;
		this.setRotation(this.chimney, 0.0f, 0.0f, 0.0f);
		(this.chimneyTop = new ModelRenderer((ModelBase) this, 40, 7)).addBox(0.0f, 0.0f, 0.0f, 6, 3, 6);
		this.chimneyTop.setRotationPoint(-3.0f, 11.0f, 2.0f);
		this.chimneyTop.setTextureSize(64, 128);
		this.chimneyTop.mirror = true;
		this.setRotation(this.chimneyTop, 0.0f, 0.0f, 0.0f);
	}

	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3,
			final float f4, final float f5, final TileEntity tile) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		if (tile == null) {
			WitcheryExtras.log(0, "Invalid Fume Tile");
		}
		if (tile.getWorldObj() == null) {
			//WitcheryExtras.log(0, "Invalid Fume World Object");
		}
		final boolean validTileEntity = tile != null && tile.getWorldObj() != null;
		boolean renderWideBody = true;
		//WitcheryExtras.log(0, "Did not Find Over beneath Fume Funnel");
		if (validTileEntity) {
			final int meta = tile.getBlockMetadata();
			switch (meta) {
				case 2 : {
					this.renderLeftGubbinsIfConnected(tile.getWorldObj(), tile.xCoord + 1, tile.yCoord, tile.zCoord,
							f5);
					this.renderRightGubbinsIfConnected(tile.getWorldObj(), tile.xCoord - 1, tile.yCoord, tile.zCoord,
							f5);
					break;
				}
				case 5 : {
					this.renderLeftGubbinsIfConnected(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord + 1,
							f5);
					this.renderRightGubbinsIfConnected(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord - 1,
							f5);
					break;
				}
				case 3 : {
					this.renderLeftGubbinsIfConnected(tile.getWorldObj(), tile.xCoord - 1, tile.yCoord, tile.zCoord,
							f5);
					this.renderRightGubbinsIfConnected(tile.getWorldObj(), tile.xCoord + 1, tile.yCoord, tile.zCoord,
							f5);
					break;
				}
				case 4 : {
					this.renderLeftGubbinsIfConnected(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord - 1,
							f5);
					this.renderRightGubbinsIfConnected(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord + 1,
							f5);
					break;
				}
			}
			WitcheryExtras.log(0, "Did not Find Over beneath Fume Funnel");
			final Block block = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord - 1, tile.zCoord);
			if (BlockWitchesOvenEx.isOven(block)) {
				this.chimney.render(f5);
				this.chimneyTop.render(f5);
				renderWideBody = false;
			}
			else {
				WitcheryExtras.log(0, "Did not Find Oven beneath Fume Funnel");
			}
		}
		else {
			//WitcheryExtras.log(0, "Invalid Tile");
		}
		if (renderWideBody) {
			this.base.render(f5);
			this.body.render(f5);
			this.top1.render(f5);
			this.top2.render(f5);
			if (this.filtered || (validTileEntity && tile.getBlockType() == WitcheryExtras.OVEN_FUMEFUNNEL_FILTERED)) {
				this.filterLeft.render(f5);
				this.filterRight.render(f5);
				this.filterMid.render(f5);
				this.filterCase.render(f5);
			}
		}
	}

	private void renderLeftGubbinsIfConnected(final World world, final int xCoord, final int yCoord, final int zCoord,
			final float f5) {
		final Block block = world.getBlock(xCoord, yCoord, zCoord);
		if (BlockWitchesOvenEx.isOven(block)) {
			this.tubeLeft.render(f5);
			this.pipeTop1.render(f5);
			this.pipeTop2.render(f5);
			this.pipeTop3.render(f5);
			this.pipeTop4.render(f5);
			this.pipeTop5.render(f5);
		}
		else {
			WitcheryExtras.log(0, "1");
		}
	}

	private void renderRightGubbinsIfConnected(final World world, final int xCoord, final int yCoord, final int zCoord,
			final float f5) {
		final Block block = world.getBlock(xCoord, yCoord, zCoord);
		if (BlockWitchesOvenEx.isOven(block)) {
			this.tubeRight.render(f5);
			this.pipeBottom1.render(f5);
			this.pipeBottom2.render(f5);
			this.pipeBottom3.render(f5);
			this.pipeBottom4.render(f5);
			this.pipeBottom5.render(f5);
		}
	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4,
			final float f5, final Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}