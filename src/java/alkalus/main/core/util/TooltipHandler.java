package alkalus.main.core.util;

import alkalus.main.asm.AsmConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipHandler {

	private static Item mPoppetShelfItem;
	private static Block mPoppetShelfBlock;
	private static ItemStack mPoppetShelfStack;
	private static final Class mPoppetShelfClass;


	private static Item mCauldronItem;
	private static Block mCauldronBlock;
	private static ItemStack mCauldronStack;
	private static final Class mCauldronClass;


	private static Item mKettleItem;
	private static Block mKettleBlock;
	private static ItemStack mKettleStack;
	private static final Class mKettleClass;

	static {
		mPoppetShelfClass = ReflectionUtils.getClass("com.emoniph.witchery.blocks.BlockPoppetShelf");
		mCauldronClass = ReflectionUtils.getClass("com.emoniph.witchery.brewing.BlockCauldron");
		mKettleClass = ReflectionUtils.getClass("com.emoniph.witchery.blocks.BlockKettle");
	}

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event){


		if (mPoppetShelfStack == null || mCauldronStack == null || mKettleStack == null) {
			init(event);
		}

		// Variables are set Ok, now to add a tooltip.
		if (mPoppetShelfStack != null) {
			if (Utils.areStacksEqual(mPoppetShelfStack, event.itemStack, true)) {
				if (!AsmConfig.allowPoppetShelfChunkLoading) {	
					event.toolTip.add(EnumChatFormatting.RED+"This block has chunkloading disabled");							
				}
				else {
					event.toolTip.add(EnumChatFormatting.GREEN+"This block has chunkloading enabled");							
				}					
			}
		}
		if (mCauldronStack != null) {
			if (Utils.areStacksEqual(mCauldronStack, event.itemStack, true)) {
				event.toolTip.add(EnumChatFormatting.GREEN+"Can also be heated with:");	
				event.toolTip.add(EnumChatFormatting.RED+"Lava (2x rate)");	
				if (ModCompat.Thaumcraft) {
					event.toolTip.add(EnumChatFormatting.DARK_RED+"Nitor (3x rate)");							
				}								
			}
		}
		if (mKettleStack != null) {
			if (Utils.areStacksEqual(mKettleStack, event.itemStack, true)) {
				event.toolTip.add(EnumChatFormatting.GREEN+"Can be heated with:");	
				event.toolTip.add(EnumChatFormatting.RED+"Fire");	
				event.toolTip.add(EnumChatFormatting.RED+"Lava");	
				if (ModCompat.Thaumcraft) {
					event.toolTip.add(EnumChatFormatting.DARK_RED+"Nitor");							
				}								
			}
		}


	}

	private static void init(ItemTooltipEvent event) {

		// Init Poppet Shelf
		if (mPoppetShelfBlock == null || mPoppetShelfItem == null || mPoppetShelfStack == null) {
			if (event.itemStack != null && event.itemStack.getItem() != null) {					
				// Set Block
				if (mPoppetShelfBlock == null) {
					Block b = Block.getBlockFromItem(event.itemStack.getItem());
					if (mPoppetShelfClass.isInstance(b)) {
						mPoppetShelfBlock = b;
					}
				}
				if (mPoppetShelfItem == null || mPoppetShelfStack == null) {
					// Set Item/ItemStack from Block since it matches.
					if (mPoppetShelfBlock != null) {
						Item i = Item.getItemFromBlock(mPoppetShelfBlock);
						if (i != null) {
							mPoppetShelfItem = i;
							mPoppetShelfStack = new ItemStack(mPoppetShelfItem);
						}
					}
				}					
			}
		}		

		// Init Cauldron
		if (mCauldronBlock == null || mCauldronItem == null || mCauldronStack == null) {
			if (event.itemStack != null && event.itemStack.getItem() != null) {					
				// Set Block
				if (mCauldronBlock == null) {
					Block b = Block.getBlockFromItem(event.itemStack.getItem());
					if (mCauldronClass.isInstance(b)) {
						mCauldronBlock = b;
					}
				}
				if (mCauldronItem == null || mCauldronStack == null) {
					// Set Item/ItemStack from Block since it matches.
					if (mCauldronBlock != null) {
						Item i = Item.getItemFromBlock(mCauldronBlock);
						if (i != null) {
							mCauldronItem = i;
							mCauldronStack = new ItemStack(mCauldronItem);
						}
					}
				}					
			}
		}		

		// Init Kettle
		if (mKettleBlock == null || mKettleItem == null || mKettleStack == null) {
			if (event.itemStack != null && event.itemStack.getItem() != null) {					
				// Set Block
				if (mKettleBlock == null) {
					Block b = Block.getBlockFromItem(event.itemStack.getItem());
					if (mKettleClass.isInstance(b)) {
						mKettleBlock = b;
					}
				}
				if (mKettleItem == null || mKettleStack == null) {
					// Set Item/ItemStack from Block since it matches.
					if (mKettleBlock != null) {
						Item i = Item.getItemFromBlock(mKettleBlock);
						if (i != null) {
							mKettleItem = i;
							mKettleStack = new ItemStack(mKettleItem);
						}
					}
				}					
			}
		}
	}
}
