package alkalus.main.core.client.model;

import net.minecraft.entity.Entity;

import alkalus.main.core.block.BlockWitchesOvenEx.TileEntityWitchesOvenEx;

import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelWitchesOvenEx extends ModelBase {
	ModelRenderer body;
	ModelRenderer lidBottom;
	ModelRenderer lidTop;
	ModelRenderer chimney;
	ModelRenderer chimneyTop;
	ModelRenderer legBackRight;
	ModelRenderer legFrontRight;
	ModelRenderer legBackLeft;
	ModelRenderer legFrontLeft;

	public ModelWitchesOvenEx() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.setTextureOffset("legBackRight.legBackRightH", 0, 0);
		this.setTextureOffset("legBackRight.legBackRightV", 0, 2);
		this.setTextureOffset("legFrontRight.legFrontRightH", 0, 0);
		this.setTextureOffset("legFrontRight.legFrontRightV", 0, 2);
		this.setTextureOffset("legBackLeft.legBackLeftH", 0, 0);
		this.setTextureOffset("legBackLeft.legBackLeftV", 0, 2);
		this.setTextureOffset("legFrontLeft.legFrontLeftH", 0, 0);
		this.setTextureOffset("legFrontLeft.legFrontLeftV", 0, 2);
		(this.body = new ModelRenderer((ModelBase) this, 0, 0)).addBox(0.0f, 1.0f, 0.0f, 12, 8, 12);
		this.body.setRotationPoint(-6.0f, 14.0f, -6.0f);
		this.body.setTextureSize(64, 64);
		this.body.mirror = true;
		this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
		(this.lidBottom = new ModelRenderer((ModelBase) this, 0, 20)).addBox(0.0f, 0.0f, 0.0f, 14, 1, 14);
		this.lidBottom.setRotationPoint(-7.0f, 14.0f, -7.0f);
		this.lidBottom.setTextureSize(64, 64);
		this.lidBottom.mirror = true;
		this.setRotation(this.lidBottom, 0.0f, 0.0f, 0.0f);
		(this.lidTop = new ModelRenderer((ModelBase) this, 8, 35)).addBox(0.0f, 0.0f, 0.0f, 10, 1, 10);
		this.lidTop.setRotationPoint(-5.0f, 13.0f, -5.0f);
		this.lidTop.setTextureSize(64, 64);
		this.lidTop.mirror = true;
		this.setRotation(this.lidTop, 0.0f, 0.0f, 0.0f);
		(this.chimney = new ModelRenderer((ModelBase) this, 48, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 13, 4);
		this.chimney.setRotationPoint(-2.0f, 8.0f, 3.0f);
		this.chimney.setTextureSize(64, 64);
		this.chimney.mirror = true;
		this.setRotation(this.chimney, 0.0f, 0.0f, 0.0f);
		(this.chimneyTop = new ModelRenderer((ModelBase) this, 38, 0)).addBox(0.0f, 0.0f, 0.0f, 4, 4, 1);
		this.chimneyTop.setRotationPoint(-2.0f, 8.0f, 7.0f);
		this.chimneyTop.setTextureSize(64, 64);
		this.chimneyTop.mirror = true;
		this.setRotation(this.chimneyTop, 0.0f, 0.0f, 0.0f);
		(this.legBackRight = new ModelRenderer((ModelBase) this, "legBackRight")).setRotationPoint(-5.0f, 21.0f, -7.0f);
		this.setRotation(this.legBackRight, 0.0f, 0.0f, 0.0f);
		this.legBackRight.mirror = true;
		this.legBackRight.addBox("legBackRightH", -2.0f, 0.0f, 0.0f, 2, 1, 1);
		this.legBackRight.addBox("legBackRightV", -3.0f, 0.0f, 0.0f, 1, 3, 1);
		(this.legFrontRight = new ModelRenderer((ModelBase) this, "legFrontRight")).setRotationPoint(-5.0f, 21.0f,
				6.0f);
		this.setRotation(this.legFrontRight, 0.0f, 0.0f, 0.0f);
		this.legFrontRight.mirror = true;
		this.legFrontRight.addBox("legFrontRightH", -2.0f, 0.0f, 0.0f, 2, 1, 1);
		this.legFrontRight.addBox("legFrontRightV", -3.0f, 0.0f, 0.0f, 1, 3, 1);
		(this.legBackLeft = new ModelRenderer((ModelBase) this, "legBackLeft")).setRotationPoint(5.0f, 21.0f, -7.0f);
		this.setRotation(this.legBackLeft, 0.0f, 0.0f, 0.0f);
		this.legBackLeft.mirror = true;
		this.legBackLeft.addBox("legBackLeftH", 0.0f, 0.0f, 0.0f, 2, 1, 1);
		this.legBackLeft.addBox("legBackLeftV", 2.0f, 0.0f, 0.0f, 1, 3, 1);
		(this.legFrontLeft = new ModelRenderer((ModelBase) this, "legFrontLeft")).setRotationPoint(5.0f, 21.0f, 6.0f);
		this.setRotation(this.legFrontLeft, 0.0f, 0.0f, 0.0f);
		this.legFrontLeft.mirror = true;
		this.legFrontLeft.addBox("legFrontLeftH", 0.0f, 0.0f, 0.0f, 2, 1, 1);
		this.legFrontLeft.addBox("legFrontLeftV", 2.0f, 0.0f, 0.0f, 1, 3, 1);
	}

	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3,
			final float f4, final float f5, final TileEntityWitchesOvenEx te) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.body.render(f5);
		this.lidBottom.render(f5);
		this.lidTop.render(f5);
		this.chimney.render(f5);
		this.chimneyTop.render(f5);
		this.legBackRight.render(f5);
		this.legFrontRight.render(f5);
		this.legBackLeft.render(f5);
		this.legFrontLeft.render(f5);
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