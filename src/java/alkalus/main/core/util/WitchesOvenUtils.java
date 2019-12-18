package alkalus.main.core.util;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockWitchesOven;
import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;
import com.emoniph.witchery.util.Config;
import com.emoniph.witchery.util.Log;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

@SuppressWarnings("unused")
public class WitchesOvenUtils {

	private static final int COOK_TIME = 180;
	private static final double FUNNEL_CHANCE = 0.25;
	private static final double FILTERED_FUNNEL_CHANCE = 0.3;
	private static final double DOUBLED_FILTERED_FUNNEL_CHANCE = 0.8;
	private static final int SLOT_TO_COOK = 0;
	private static final int SLOT_FUEL = 1;
	private static final int SLOT_COOKED = 2;
	private static final int SLOT_BY_PRODUCT = 3;
	private static final int SLOT_JARS = 4;
    private static final int[] slots_top = new int[] { 0, 4 };
    private static final int[] slots_bottom = new int[] { 4, 1 };
    private static final int[] slots_sides = new int[] { 3, 2, 4, 1 };    

	public static ItemStack[] getFurnaceItemStacks(TileEntityWitchesOven aTile) {
		return (ItemStack[]) ReflectionUtils.getFieldValue(ReflectionUtils.getField(TileEntityWitchesOven.class, "furnaceItemStacks"), aTile);
	}
	public static int[] getSlotsTop(TileEntityWitchesOven aTile) {
		return (int[]) ReflectionUtils.getFieldValue(ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_top"), aTile);
	}
	public static int[] getSlotsBottom(TileEntityWitchesOven aTile) {
		return (int[]) ReflectionUtils.getFieldValue(ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_bottom"), aTile);
	}
	public static int[] getSlotsSides(TileEntityWitchesOven aTile) {
		return (int[]) ReflectionUtils.getFieldValue(ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_sides"), aTile);
	}

    public static void updateEntity(TileEntityWitchesOven aTile) {
        final boolean flag = aTile.furnaceBurnTime > 0;
        boolean flag2 = false;
        if (aTile.furnaceBurnTime > 0) {
            --aTile.furnaceBurnTime;
        }
        if (!aTile.getWorldObj().isRemote) {
            if (aTile.furnaceBurnTime == 0 && canSmelt(aTile)) {
                final int itemBurnTime = TileEntityFurnace.getItemBurnTime(getFurnaceItemStacks(aTile)[1]);
                aTile.furnaceBurnTime = itemBurnTime;
                aTile.currentItemBurnTime = itemBurnTime;
                if (aTile.furnaceBurnTime > 0) {
                    flag2 = true;
                    if (getFurnaceItemStacks(aTile)[1] != null) {
                        final ItemStack itemStack = getFurnaceItemStacks(aTile)[1];
                        --itemStack.stackSize;
                        if (getFurnaceItemStacks(aTile)[1].stackSize == 0) {
                            getFurnaceItemStacks(aTile)[1] = getFurnaceItemStacks(aTile)[1].getItem().getContainerItem(getFurnaceItemStacks(aTile)[1]);
                        }
                    }
                }
            }
            if (aTile.isBurning() && canSmelt(aTile)) {
                ++aTile.furnaceCookTime;
                if (aTile.furnaceCookTime >= getCookTime(aTile)) {
                    aTile.furnaceCookTime = 0;
                    aTile.smeltItem();
                    flag2 = true;
                }
            }
            else {
                aTile.furnaceCookTime = 0;
            }
            if (flag != aTile.furnaceBurnTime > 0) {
                flag2 = true;
                BlockWitchesOven.updateWitchesOvenBlockState(aTile.furnaceBurnTime > 0, aTile.getWorldObj(), aTile.xCoord, aTile.yCoord, aTile.zCoord);
            }
        }
        if (flag2) {
            aTile.getWorldObj().markBlockForUpdate(aTile.xCoord, aTile.yCoord, aTile.zCoord);
        }
    }
	




	public static boolean canSmelt(TileEntityWitchesOven aTile) {
		if (getFurnaceItemStacks(aTile)[0] == null) {
			return false;
		}
		final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getFurnaceItemStacks(aTile)[0]);
		if (itemstack == null) {
			return false;
		}
		final Item item = itemstack.getItem();
		if (item != Items.coal && !(item instanceof ItemFood) && !Witchery.Items.GENERIC.itemAshWood.isMatch(itemstack)) {
			return false;
		}
		if (getFurnaceItemStacks(aTile)[2] == null) {
			return true;
		}
		if (!getFurnaceItemStacks(aTile)[2].isItemEqual(itemstack)) {
			return false;
		}
		final int result = getFurnaceItemStacks(aTile)[2].stackSize + itemstack.stackSize;
		return result <= aTile.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
	}
	
    public static void smeltItem(TileEntityWitchesOven aTile) {
        if (canSmelt(aTile)) {
            final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getFurnaceItemStacks(aTile)[0]);
            if (getFurnaceItemStacks(aTile)[2] == null) {
                getFurnaceItemStacks(aTile)[2] = itemstack.copy();
            }
            else if (getFurnaceItemStacks(aTile)[2].isItemEqual(itemstack)) {
                final ItemStack itemStack = getFurnaceItemStacks(aTile)[2];
                itemStack.stackSize += itemstack.stackSize;
            }
            generateByProduct(aTile, itemstack);
            final ItemStack itemStack2 = getFurnaceItemStacks(aTile)[0];
            --itemStack2.stackSize;
            if (getFurnaceItemStacks(aTile)[0].stackSize <= 0) {
                getFurnaceItemStacks(aTile)[0] = null;
            }
        }
    }

	public static int getFumeFunnels(TileEntityWitchesOven aTile) {
		int funnels = 0;
		final int meta = aTile.getWorldObj().getBlockMetadata(aTile.xCoord, aTile.yCoord, aTile.zCoord);
		switch (meta) {
			case 2:
			case 3: {
				funnels += (isFumeFunnel(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, meta) ? 1 : 0);
				funnels += (isFumeFunnel(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, meta) ? 1 : 0);
				break;
			}
			case 4:
			case 5: {
				funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, meta) ? 1 : 0);
				funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, meta) ? 1 : 0);
				break;
			}
		}
		funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord + 1, aTile.zCoord, meta) ? 1 : 0);
		return funnels;
	}

	public static boolean isFumeFunnel(TileEntityWitchesOven aTile, final int xCoord, final int yCoord, final int zCoord, final int meta) {
		final Block block = aTile.getWorldObj().getBlock(xCoord, yCoord, zCoord);
		return (block == Witchery.Blocks.OVEN_FUMEFUNNEL || block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED) && aTile.getWorldObj().getBlockMetadata(xCoord, yCoord, zCoord) == meta;
	}

	public static double getFumeFunnelsChance(TileEntityWitchesOven aTile) {
		double funnels = 0.0;
		switch (aTile.getWorldObj().getBlockMetadata(aTile.xCoord, aTile.yCoord, aTile.zCoord)) {
			case 2: {
				funnels += getFumeFunnelChance(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, 2);
				funnels += getFumeFunnelChance(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, 2);
				break;
			}
			case 3: {
				funnels += getFumeFunnelChance(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, 3);
				funnels += getFumeFunnelChance(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, 3);
				break;
			}
			case 4: {
				funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, 4);
				funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, 4);
				break;
			}
			case 5: {
				funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, 5);
				funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, 5);
				break;
			}
		}
		return funnels;
	}

	public static double getFumeFunnelChance(TileEntityWitchesOven aTile, final int x, final int y, final int z, final int meta) {
		final Block block = aTile.getWorldObj().getBlock(x, y, z);
		if (block == Witchery.Blocks.OVEN_FUMEFUNNEL) {
			if (aTile.getWorldObj().getBlockMetadata(x, y, z) == meta) {
				return 0.25;
			}
		}
		else if (block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED && aTile.getWorldObj().getBlockMetadata(x, y, z) == meta) {
			return Config.instance().doubleFumeFilterChance ? 0.8 : 0.3;
		}
		return 0.0;
	}

	public static int getCookTime(TileEntityWitchesOven aTile) {
		final int time = 180 - 20 * getFumeFunnels(aTile);
		return time;
	}

	public static void generateByProduct(TileEntityWitchesOven aTile, final ItemStack itemstack) {
		try {
			final double BASE_CHANCE = 0.3;
			final double funnels = getFumeFunnelsChance(aTile);
			Log.instance().debug("" + getFurnaceItemStacks(aTile)[0] + ": " + getFurnaceItemStacks(aTile)[0].getItem().getUnlocalizedName());
			if (aTile.getWorldObj().rand.nextDouble() <= Math.min(0.3 + funnels, 1.0) && getFurnaceItemStacks(aTile)[4] != null) {
				if (getFurnaceItemStacks(aTile)[0].getItem() == Item.getItemFromBlock(Blocks.sapling) && getFurnaceItemStacks(aTile)[0].getItemDamage() != 3) {
					switch (getFurnaceItemStacks(aTile)[0].getItemDamage()) {
						case 0: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1));
							break;
						}
						case 1: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
							break;
						}
						case 2: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1));
							break;
						}
					}
				}
				else if (getFurnaceItemStacks(aTile)[0].getItem() == Item.getItemFromBlock(Witchery.Blocks.SAPLING)) {
					switch (getFurnaceItemStacks(aTile)[0].getItemDamage()) {
						case 0: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(1));
							break;
						}
						case 1: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemReekOfMisfortune.createStack(1));
							break;
						}
						case 2: {
							createByProduct(aTile, Witchery.Items.GENERIC.itemOdourOfPurity.createStack(1));
							break;
						}
					}
				}
				else if (getFurnaceItemStacks(aTile)[0].getUnlocalizedName().equals("tile.bop.saplings") && getFurnaceItemStacks(aTile)[0].getItemDamage() == 6) {
					createByProduct(aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
				}
				else if (getFurnaceItemStacks(aTile)[0].hasTagCompound() && getFurnaceItemStacks(aTile)[0].getTagCompound().hasKey("Genome")) {
					final NBTBase tag = getFurnaceItemStacks(aTile)[0].getTagCompound().getTag("Genome");
					if (tag != null && tag instanceof NBTTagCompound) {
						final NBTTagCompound compound = (NBTTagCompound)tag;
						if (compound.hasKey("Chromosomes") && compound.getTag("Chromosomes") instanceof NBTTagList) {
							final NBTTagList list = compound.getTagList("Chromosomes", 10);
							if (list != null && list.tagCount() > 0) {
								final NBTBase chromoBase = (NBTBase)list.getCompoundTagAt(0);
								if (chromoBase != null && chromoBase instanceof NBTTagCompound) {
									final NBTTagCompound chromosome = (NBTTagCompound)chromoBase;
									if (chromosome.hasKey("UID0")) {
										final String treeType = chromosome.getString("UID0");
										if (treeType != null) {
											Log.instance().debug("Forestry tree: " + treeType);
											if (treeType.equals("forestry.treeOak")) {
												createByProduct(aTile, Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1));
											}
											else if (treeType.equals("forestry.treeSpruce")) {
												createByProduct(aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
											}
											else if (treeType.equals("forestry.treeBirch")) {
												createByProduct(aTile, Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1));
											}
										}
									}
								}
							}
						}
					}
				}
				else {
					createByProduct(aTile, Witchery.Items.GENERIC.itemFoulFume.createStack(1));
				}
			}
		}
		catch (Throwable e) {
			Log.instance().warning(e, "Exception occured while generating a by product from a witches oven");
		}
	}

	public static void createByProduct(TileEntityWitchesOven aTile, final ItemStack byProduct) {
		final int BY_PRODUCT_INDEX = 3;
		if (getFurnaceItemStacks(aTile)[3] == null) {
			getFurnaceItemStacks(aTile)[3] = byProduct;
			final ItemStack itemStack = getFurnaceItemStacks(aTile)[4];
			if (--itemStack.stackSize <= 0) {
				getFurnaceItemStacks(aTile)[4] = null;
			}
		}
		else if (getFurnaceItemStacks(aTile)[3].isItemEqual(byProduct) && getFurnaceItemStacks(aTile)[3].stackSize + byProduct.stackSize < getFurnaceItemStacks(aTile)[3].getMaxStackSize()) {
			final ItemStack itemStack2 = getFurnaceItemStacks(aTile)[3];
			itemStack2.stackSize += byProduct.stackSize;
			final ItemStack itemStack3 = getFurnaceItemStacks(aTile)[4];
			if (--itemStack3.stackSize <= 0) {
				getFurnaceItemStacks(aTile)[4] = null;
			}
		}
	}

}
