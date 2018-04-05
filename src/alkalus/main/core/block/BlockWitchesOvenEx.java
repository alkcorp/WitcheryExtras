package alkalus.main.core.block;

import net.minecraft.inventory.ICrafting;
import com.emoniph.witchery.util.SlotClayJar;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.InventoryPlayer;
import com.emoniph.witchery.util.BlockSide;
import com.emoniph.witchery.util.Config;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import com.emoniph.witchery.util.BlockUtil;
import net.minecraft.world.World;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;
import alkalus.main.core.util.Utils;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockBaseContainer;
import com.emoniph.witchery.blocks.BlockWitchesOven;

import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;
import java.util.Random;

public class BlockWitchesOvenEx extends BlockBaseContainer {
	private final Random furnaceRand;
	private final boolean isActive;
	private static boolean keepFurnaceInventory;

	public BlockWitchesOvenEx(final boolean burning) {
		super(Material.iron, TileEntityWitchesOvenEx.class);
		this.furnaceRand = new Random();
		super.registerTileEntity = !burning;
		super.registerWithCreateTab = !burning;
		this.isActive = burning;
		this.setHardness(3.5f);
		this.setStepSound(BlockWitchesOven.soundTypeMetal);
		if (this.isActive) {
			this.setLightLevel(0.875f);
		}
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean shouldSideBeRendered(final IBlockAccess iblockaccess, final int i, final int j, final int k,
			final int l) {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
		return Item.getItemFromBlock(WitcheryExtras.OVEN_IDLE);
	}

	public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		BlockUtil.setBlockDefaultDirection(par1World, par2, par3, par4);
	}

	public static boolean isOven(final Block block) {
		return block == WitcheryExtras.OVEN_IDLE || block == WitcheryExtras.OVEN_BURNING;
	}

	public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4,
			final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
		if (par1World.isRemote) {
			return true;
		}
		final TileEntity tileentityfurnace = par1World.getTileEntity(par2, par3, par4);
		if (tileentityfurnace != null) {
			par5EntityPlayer.openGui((Object) WitcheryExtras.instance, 2, par1World, par2, par3, par4);
		}
		return true;
	}

	public static void updateWitchesOvenBlockState(final boolean par0, final World par1World, final int par2,
			final int par3, final int par4) {
		final int l = par1World.getBlockMetadata(par2, par3, par4);
		final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		keepFurnaceInventory = true;
		if (par0) {
			par1World.setBlock(par2, par3, par4, WitcheryExtras.OVEN_BURNING);
		} else {
			par1World.setBlock(par2, par3, par4, WitcheryExtras.OVEN_IDLE);
		}
		keepFurnaceInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
		if (tileentity != null) {
			tileentity.validate();
			par1World.setTileEntity(par2, par3, par4, tileentity);
		}
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4,
			final Random par5Random) {
		if (this.isActive) {
			final int l = par1World.getBlockMetadata(par2, par3, par4);
			final float f = par2 + 0.5f;
			final float f2 = par3 + 0.2f + par5Random.nextFloat() * 6.0f / 16.0f;
			final float f3 = par4 + 0.5f;
			final float f4 = 0.52f;
			final float f5 = par5Random.nextFloat() * 0.6f - 0.3f;
			if (l == 4) {
				par1World.spawnParticle("smoke", (double) (f - f4), (double) f2, (double) (f3 + f5), 0.0, 0.0, 0.0);
				par1World.spawnParticle("flame", (double) (f - f4), (double) f2, (double) (f3 + f5), 0.0, 0.0, 0.0);
			} else if (l == 5) {
				par1World.spawnParticle("smoke", (double) (f + f4), (double) f2, (double) (f3 + f5), 0.0, 0.0, 0.0);
				par1World.spawnParticle("flame", (double) (f + f4), (double) f2, (double) (f3 + f5), 0.0, 0.0, 0.0);
			} else if (l == 2) {
				par1World.spawnParticle("smoke", (double) (f + f5), (double) f2, (double) (f3 - f4), 0.0, 0.0, 0.0);
				par1World.spawnParticle("flame", (double) (f + f5), (double) f2, (double) (f3 - f4), 0.0, 0.0, 0.0);
			} else if (l == 3) {
				par1World.spawnParticle("smoke", (double) (f + f5), (double) f2, (double) (f3 + f4), 0.0, 0.0, 0.0);
				par1World.spawnParticle("flame", (double) (f + f5), (double) f2, (double) (f3 + f4), 0.0, 0.0, 0.0);
			}
		}
	}

	public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4,
			final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
		final int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
		if (l == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}
		if (l == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}
		if (l == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}
		if (l == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
	}

	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5,
			final int par6) {
		if (!keepFurnaceInventory) {
			final TileEntity tile = par1World.getTileEntity(par2, par3, par4);
			final TileEntityWitchesOvenEx tileentityfurnace = (TileEntityWitchesOvenEx) BlockUtil.getTileEntity(par1World, par2, par3, par4, TileEntityWitchesOvenEx.class);
			if (tileentityfurnace != null) {
				for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1) {
					final ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);
					if (itemstack != null) {
						final float f = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
						final float f2 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
						final float f3 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
						while (itemstack.stackSize > 0) {
							int k1 = this.furnaceRand.nextInt(21) + 10;
							if (k1 > itemstack.stackSize) {
								k1 = itemstack.stackSize;
							}
							final ItemStack itemStack = itemstack;
							itemStack.stackSize -= k1;
							final EntityItem entityitem = new EntityItem(par1World, (double) (par2 + f),
									(double) (par3 + f2), (double) (par4 + f3),
									new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
							if (itemstack.hasTagCompound()) {
								entityitem.getEntityItem()
								.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							final float f4 = 0.05f;
							entityitem.motionX = (float) this.furnaceRand.nextGaussian() * f4;
							entityitem.motionY = (float) this.furnaceRand.nextGaussian() * f4 + 0.2f;
							entityitem.motionZ = (float) this.furnaceRand.nextGaussian() * f4;
							par1World.spawnEntityInWorld((Entity) entityitem);
						}
					}
				}
				par1World.func_147453_f(par2, par3, par4, par5);
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public boolean hasComparatorInputOverride() {
		return true;
	}

	public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4,
			final int par5) {
		final TileEntity te = par1World.getTileEntity(par2, par3, par4);
		return (te != null && te instanceof IInventory) ? Container.calcRedstoneFromInventory((IInventory) te) : 0;
	}

	public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
		return Item.getItemFromBlock(WitcheryExtras.OVEN_IDLE);
	}

	public static class TileEntityWitchesOvenEx extends TileEntity implements ISidedInventory {
		private ItemStack[] furnaceItemStacks;
		public int furnaceBurnTime;
		public int currentItemBurnTime;
		public int furnaceCookTime;
		static final int COOK_TIME = 180;
		private static final double FUNNEL_CHANCE = 0.25;
		private static final double FILTERED_FUNNEL_CHANCE = 0.3;
		private static final double DOUBLED_FILTERED_FUNNEL_CHANCE = 0.8;
		private static final int SLOT_TO_COOK = 0;
		private static final int SLOT_FUEL = 1;
		private static final int SLOT_COOKED = 2;
		private static final int SLOT_BY_PRODUCT = 3;
		private static final int SLOT_JARS = 4;
		private static final int[] slots_top;
		private static final int[] slots_bottom;
		private static final int[] slots_sides;

		public TileEntityWitchesOvenEx() {
			this.furnaceItemStacks = new ItemStack[5];
		}

		public int getSizeInventory() {
			return this.furnaceItemStacks.length;
		}

		public ItemStack getStackInSlot(final int par1) {
			return this.furnaceItemStacks[par1];
		}

		public ItemStack decrStackSize(final int par1, final int par2) {
			if (this.furnaceItemStacks[par1] == null) {
				return null;
			}
			if (this.furnaceItemStacks[par1].stackSize <= par2) {
				final ItemStack itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			}
			final ItemStack itemstack = this.furnaceItemStacks[par1].splitStack(par2);
			if (this.furnaceItemStacks[par1].stackSize == 0) {
				this.furnaceItemStacks[par1] = null;
			}
			return itemstack;
		}

		public ItemStack getStackInSlotOnClosing(final int par1) {
			if (this.furnaceItemStacks[par1] != null) {
				final ItemStack itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			}
			return null;
		}

		public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
			this.furnaceItemStacks[par1] = par2ItemStack;
			if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
				par2ItemStack.stackSize = this.getInventoryStackLimit();
			}
		}

		public String getInventoryName() {
			return this.getBlockType().getLocalizedName();
		}

		public boolean hasCustomInventoryName() {
			return true;
		}

		public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
			super.readFromNBT(par1NBTTagCompound);
			final NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
			this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				final NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				final byte b0 = nbttagcompound1.getByte("Slot");
				if (b0 >= 0 && b0 < this.furnaceItemStacks.length) {
					this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
			this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
			this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
			this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.furnaceItemStacks[SLOT_FUEL]);
		}

		public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
			super.writeToNBT(par1NBTTagCompound);
			par1NBTTagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
			par1NBTTagCompound.setShort("CookTime", (short) this.furnaceCookTime);
			final NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
				if (this.furnaceItemStacks[i] != null) {
					final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag((NBTBase) nbttagcompound1);
				}
			}
			par1NBTTagCompound.setTag("Items", (NBTBase) nbttaglist);
		}

		public int getInventoryStackLimit() {
			return 64;
		}

		@SideOnly(Side.CLIENT)
		public int getCookProgressScaled(final int par1) {
			return this.furnaceCookTime * par1 / this.getCookTime();
		}

		@SideOnly(Side.CLIENT)
		public int getBurnTimeRemainingScaled(final int par1) {
			if (this.currentItemBurnTime == 0) {
				this.currentItemBurnTime = 200;
			}
			return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
		}

		public boolean isBurning() {
			return this.furnaceBurnTime > 0;
		}

		public void updateEntity() {
			final boolean flag = this.furnaceBurnTime > 0;
			boolean flag2 = false;
			if (this.furnaceBurnTime > 0) {
				--this.furnaceBurnTime;
			}
			if (!this.worldObj.isRemote) {
				if (this.furnaceBurnTime == 0 && this.canSmelt()) {
					final int itemBurnTime = TileEntityFurnace.getItemBurnTime(this.furnaceItemStacks[SLOT_FUEL]);
					this.furnaceBurnTime = itemBurnTime;
					this.currentItemBurnTime = itemBurnTime;
					if (this.furnaceBurnTime > 0) {
						flag2 = true;
						if (this.furnaceItemStacks[SLOT_FUEL] != null) {
							final ItemStack itemStack = this.furnaceItemStacks[SLOT_FUEL];
							--itemStack.stackSize;
							if (this.furnaceItemStacks[SLOT_FUEL].stackSize == 0) {
								this.furnaceItemStacks[SLOT_FUEL] = this.furnaceItemStacks[SLOT_FUEL].getItem()
										.getContainerItem(this.furnaceItemStacks[SLOT_FUEL]);
							}
						}
					}
				}
				if (this.isBurning() && this.canSmelt()) {
					++this.furnaceCookTime;
					if (this.furnaceCookTime >= this.getCookTime()) {
						this.furnaceCookTime = 0;
						this.smeltItem();
						flag2 = true;
					}
				} else {
					this.furnaceCookTime = 0;
				}
				if (flag != this.furnaceBurnTime > 0) {
					flag2 = true;
					updateWitchesOvenBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord,
							this.yCoord, this.zCoord);
				}
			}
			if (flag2) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}		

		private int getFumeFunnels() {
			int funnels = 0;
			final int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			switch (meta) {
				case 2 :
				case 3 : {
					funnels += (this.isFumeFunnel(this.xCoord - 1, this.yCoord, this.zCoord, meta) ? 1 : 0);
					funnels += (this.isFumeFunnel(this.xCoord + 1, this.yCoord, this.zCoord, meta) ? 1 : 0);
					break;
				}
				case 4 :
				case 5 : {
					funnels += (this.isFumeFunnel(this.xCoord, this.yCoord, this.zCoord - 1, meta) ? 1 : 0);
					funnels += (this.isFumeFunnel(this.xCoord, this.yCoord, this.zCoord + 1, meta) ? 1 : 0);
					break;
				}
			}
			funnels += (this.isFumeFunnel(this.xCoord, this.yCoord + 1, this.zCoord, meta) ? 1 : 0);
			return funnels;
		}

		private boolean isFumeFunnel(final int xCoord, final int yCoord, final int zCoord, final int meta) {
			final Block block = this.worldObj.getBlock(xCoord, yCoord, zCoord);
			return (block == Witchery.Blocks.OVEN_FUMEFUNNEL || block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED)
					&& this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == meta;
		}

		private double getFumeFunnelsChance() {
			double funnels = 0.0;
			switch (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)) {
				case 2 : {
					funnels += this.getFumeFunnelChance(this.xCoord + 1, this.yCoord, this.zCoord, 2);
					funnels += this.getFumeFunnelChance(this.xCoord - 1, this.yCoord, this.zCoord, 2);
					break;
				}
				case 3 : {
					funnels += this.getFumeFunnelChance(this.xCoord + 1, this.yCoord, this.zCoord, 3);
					funnels += this.getFumeFunnelChance(this.xCoord - 1, this.yCoord, this.zCoord, 3);
					break;
				}
				case 4 : {
					funnels += this.getFumeFunnelChance(this.xCoord, this.yCoord, this.zCoord + 1, 4);
					funnels += this.getFumeFunnelChance(this.xCoord, this.yCoord, this.zCoord - 1, 4);
					break;
				}
				case 5 : {
					funnels += this.getFumeFunnelChance(this.xCoord, this.yCoord, this.zCoord + 1, 5);
					funnels += this.getFumeFunnelChance(this.xCoord, this.yCoord, this.zCoord - 1, 5);
					break;
				}
			}
			return funnels;
		}

		private double getFumeFunnelChance(final int x, final int y, final int z, final int meta) {
			final Block block = this.worldObj.getBlock(x, y, z);
			if (block == Witchery.Blocks.OVEN_FUMEFUNNEL) {
				if (this.worldObj.getBlockMetadata(x, y, z) == meta) {
					return FUNNEL_CHANCE;
				}
			} 
			else if (block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED
					&& this.worldObj.getBlockMetadata(x, y, z) == meta) {
				return Config.instance().doubleFumeFilterChance ? DOUBLED_FILTERED_FUNNEL_CHANCE : FILTERED_FUNNEL_CHANCE;
			}			
			return 0.0;
		}

		private int getCookTime() {
			final int time = 180 - 20 * this.getFumeFunnels();
			return time;
		}

		public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
			return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
					&& par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
		}

		public void openInventory() {
		}

		public void closeInventory() {
		}

		public boolean isItemValidForSlot(final int slot, final ItemStack itemstack) {
			if (slot == 2 || slot == 3) {
				return false;
			}
			if (slot == 1) {
				return TileEntityFurnace.isItemFuel(itemstack);
			}
			if (slot == 4) {
				return Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(itemstack);
			}
			return slot != 0 || !Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(itemstack);
		}

		public int[] getAccessibleSlotsFromSide(final int side) {
			return BlockSide.BOTTOM.isEqual(side)
					? TileEntityWitchesOvenEx.slots_bottom
							: (BlockSide.TOP.isEqual(side)
									? TileEntityWitchesOvenEx.slots_top
											: TileEntityWitchesOvenEx.slots_sides);
		}

		public boolean canInsertItem(final int slot, final ItemStack itemstack, final int par3) {
			return this.isItemValidForSlot(slot, itemstack);
		}

		public boolean canExtractItem(final int slot, final ItemStack stack, final int side) {
			if (BlockSide.TOP.isEqual(side)) {
				return false;
			}
			if (BlockSide.BOTTOM.isEqual(side)) {
				return slot == 1 && stack.getItem() == Items.bucket;
			}
			return slot == 3 || slot == 2;
		}	

		private boolean canSmelt() {
			final OvenRecipes.OvenRecipe recipe = this.getActiveRecipe();
			if (recipe == null) {
				return false;
			}
			return true;
		}

		public OvenRecipes.OvenRecipe getActiveRecipe() {
			try {
				if (this.furnaceItemStacks[SLOT_TO_COOK] == null && this.furnaceItemStacks[SLOT_JARS] == null) {
					return null;
				}
				final OvenRecipes.OvenRecipe recipe = OvenRecipes.instance().getOvenResult(this.furnaceItemStacks[SLOT_TO_COOK], this.furnaceItemStacks[SLOT_JARS].stackSize);
				return recipe;
			}
			catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}

		public void smeltItem() {
			if (this.canSmelt()) {				
				final OvenRecipes.OvenRecipe recipe = OvenRecipes.instance().getOvenResult(this.furnaceItemStacks[SLOT_TO_COOK], this.furnaceItemStacks[SLOT_JARS].stackSize);
				if (recipe != null) {					
					if (setInputSlot(recipe.inputs) && setJarInputSlot(OvenRecipes.getEmptyJar(recipe.jars))){
						if (setOutputSlot(recipe.output) && setJarOutputSlot(recipe.outputJar)) {
							WitcheryExtras.log(0, "Completed Witches Oven Recipe.");							
							if (getFumeFunnels() > 0) {								
								Random R = new Random();
								if (R.nextInt(100) <= getFumeFunnelsChance()) {
									WitcheryExtras.log(0, "Adding extra byproducts.");			
									generateExtraByproducts();
								}								
							}

						}
					}
				}								
			}
		}	


		private boolean setInputSlot(ItemStack aStack) {			
			if (furnaceItemStacks[SLOT_TO_COOK] != null) {
				if (OvenRecipes.OvenRecipe.isMatch(aStack, furnaceItemStacks[SLOT_TO_COOK])){
					int size = (furnaceItemStacks[SLOT_TO_COOK].stackSize - aStack.stackSize);
					if (size <= 64 && size > 0) {
						furnaceItemStacks[SLOT_TO_COOK].stackSize = size;
						return true;
					}
					else {
						furnaceItemStacks[SLOT_TO_COOK] = null;
						return true;
					}
				}
			}
			else {
				furnaceItemStacks[SLOT_TO_COOK] = aStack;
				return true;
			}			
			return false;
		}

		private boolean setOutputSlot(ItemStack aStack) {
			if (furnaceItemStacks[SLOT_COOKED] != null) {
				if (OvenRecipes.OvenRecipe.isMatch(aStack, furnaceItemStacks[SLOT_COOKED])){
					int size = (furnaceItemStacks[SLOT_COOKED].stackSize + aStack.stackSize);
					if (size <= 64 && size > 0) {
						furnaceItemStacks[SLOT_COOKED].stackSize = size;
						return true;
					}
					else {
						return false;
					}
				}
			}
			else {
				furnaceItemStacks[SLOT_COOKED] = aStack;
				return true;
			}			
			return false;
		}

		private boolean setJarInputSlot(ItemStack aStack) {
			if (furnaceItemStacks[SLOT_JARS] != null) {
				if (OvenRecipes.OvenRecipe.isMatch(aStack, furnaceItemStacks[SLOT_JARS])){
					int size = (furnaceItemStacks[SLOT_JARS].stackSize - aStack.stackSize);
					if (size <= 64 && size > 0) {
						furnaceItemStacks[SLOT_JARS].stackSize = size;
						return true;
					}
					else {
						furnaceItemStacks[SLOT_JARS] = null;
						return true;
					}
				}
			}
			else {
				furnaceItemStacks[SLOT_JARS] = aStack;
				return true;
			}			
			return false;
		}

		private boolean setJarOutputSlot(ItemStack aStack) {
			if (furnaceItemStacks[SLOT_BY_PRODUCT] != null) {
				if (OvenRecipes.OvenRecipe.isMatch(aStack, furnaceItemStacks[SLOT_BY_PRODUCT])){
					int size = (furnaceItemStacks[SLOT_BY_PRODUCT].stackSize + aStack.stackSize);
					if (size <= 64 && size > 0) {
						furnaceItemStacks[SLOT_BY_PRODUCT].stackSize = size;
						return true;
					}
					else {
						return false;
					}
				}
			}
			else {
				furnaceItemStacks[SLOT_BY_PRODUCT] = aStack;
				return true;
			}			
			return false;
		}

		private boolean generateExtraByproducts() {
			final double BASE_CHANCE = 0.3;
			final double funnels = this.getFumeFunnelsChance();
			Random R = new Random();
			int r1 = R.nextInt(2);
			if (this.worldObj.rand.nextDouble() <= Math.min(BASE_CHANCE + funnels, 1.0)	&& this.furnaceItemStacks[SLOT_JARS] != null) {	
				OvenRecipe x = this.getActiveRecipe();
				if (x != null) {
					if (Utils.hasValidOreDictTag("treeSapling", x.inputs)){
						if (this.getStackInSlot(SLOT_JARS) != null && this.getStackInSlot(SLOT_JARS).stackSize >= r1) {
							this.setJarOutputSlot(Utils.getSimpleStack(this.getActiveRecipe().outputJar, r1));
						}
					}	
				}
			}
			return false;
		}





		static {
			slots_top = new int[]{0, 4};
			slots_bottom = new int[]{4, 1};
			slots_sides = new int[]{3, 2, 4, 1};
		}
	}


























	public static class ContainerWitchesOven extends Container {
		private TileEntityWitchesOvenEx furnace;
		private int lastCookTime;
		private int lastBurnTime;
		private int lastItemBurnTime;

		public ContainerWitchesOven(final InventoryPlayer par1InventoryPlayer,
				final TileEntityWitchesOvenEx par2TileEntityFurnace) {
			this.furnace = par2TileEntityFurnace;
			this.addSlotToContainer(new Slot((IInventory) par2TileEntityFurnace, 0, 56, 17));
			this.addSlotToContainer(new Slot((IInventory) par2TileEntityFurnace, 1, 56, 53));
			this.addSlotToContainer(
					(Slot) new SlotFurnace(par1InventoryPlayer.player, (IInventory) par2TileEntityFurnace, 2, 118, 21));
			this.addSlotToContainer(
					(Slot) new SlotFurnace(par1InventoryPlayer.player, (IInventory) par2TileEntityFurnace, 3, 118, 53));
			this.addSlotToContainer((Slot) new SlotClayJar((IInventory) par2TileEntityFurnace, 4, 83, 53));
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 9; ++j) {
					this.addSlotToContainer(
							new Slot((IInventory) par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
				}
			}
			for (int i = 0; i < 9; ++i) {
				this.addSlotToContainer(new Slot((IInventory) par1InventoryPlayer, i, 8 + i * 18, 142));
			}
		}

		public void addCraftingToCrafters(final ICrafting par1ICrafting) {
			super.addCraftingToCrafters(par1ICrafting);
			par1ICrafting.sendProgressBarUpdate((Container) this, 0, this.furnace.furnaceCookTime);
			par1ICrafting.sendProgressBarUpdate((Container) this, 1, this.furnace.furnaceBurnTime);
			par1ICrafting.sendProgressBarUpdate((Container) this, 2, this.furnace.currentItemBurnTime);
		}

		public void detectAndSendChanges() {
			super.detectAndSendChanges();
			for (int i = 0; i < this.crafters.size(); ++i) {
				final ICrafting icrafting = (ICrafting) this.crafters.get(i);
				if (this.lastCookTime != this.furnace.furnaceCookTime) {
					icrafting.sendProgressBarUpdate((Container) this, 0, this.furnace.furnaceCookTime);
				}
				if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
					icrafting.sendProgressBarUpdate((Container) this, 1, this.furnace.furnaceBurnTime);
				}
				if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
					icrafting.sendProgressBarUpdate((Container) this, 2, this.furnace.currentItemBurnTime);
				}
			}
			this.lastCookTime = this.furnace.furnaceCookTime;
			this.lastBurnTime = this.furnace.furnaceBurnTime;
			this.lastItemBurnTime = this.furnace.currentItemBurnTime;
		}

		@SideOnly(Side.CLIENT)
		public void updateProgressBar(final int par1, final int par2) {
			if (par1 == 0) {
				this.furnace.furnaceCookTime = par2;
			}
			if (par1 == 1) {
				this.furnace.furnaceBurnTime = par2;
			}
			if (par1 == 2) {
				this.furnace.currentItemBurnTime = par2;
			}
		}

		public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
			return this.furnace.isUseableByPlayer(par1EntityPlayer);
		}

		public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
			ItemStack itemstack = null;
			final Slot slot = (Slot) this.inventorySlots.get(slotIndex);
			if (slot != null && slot.getHasStack()) {
				final ItemStack itemstack2 = slot.getStack();
				itemstack = itemstack2.copy();
				if (slotIndex == 2 || slotIndex == 3) {
					if (!this.mergeItemStack(itemstack2, 5, 41, true)) {
						return null;
					}
					slot.onSlotChange(itemstack2, itemstack);
				} else if (slotIndex != 1 && slotIndex != 0 && slotIndex != 4) {
					if (FurnaceRecipes.smelting().getSmeltingResult(itemstack2) != null) {
						if (!this.mergeItemStack(itemstack2, 0, 1, false)) {
							return null;
						}
					} else if (TileEntityFurnace.isItemFuel(itemstack2)) {
						if (!this.mergeItemStack(itemstack2, 1, 2, false)) {
							return null;
						}
					} else if (Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(itemstack2)) {
						if (!this.mergeItemStack(itemstack2, 4, 5, false)) {
							return null;
						}
					} else if (slotIndex >= 5 && slotIndex < 32) {
						if (!this.mergeItemStack(itemstack2, 32, 41, false)) {
							return null;
						}
					} else if (slotIndex >= 32 && slotIndex < 41 && !this.mergeItemStack(itemstack2, 5, 32, false)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack2, 5, 41, false)) {
					return null;
				}
				if (itemstack2.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack2.stackSize == itemstack.stackSize) {
					return null;
				}
				slot.onPickupFromSlot(player, itemstack2);
			}
			return itemstack;
		}	

	}
}