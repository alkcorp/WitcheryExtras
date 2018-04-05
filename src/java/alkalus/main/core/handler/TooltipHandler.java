package alkalus.main.core.handler;

import com.emoniph.witchery.Witchery;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import alkalus.main.core.WitcheryExtras;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipHandler {

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event){		
		if (event.itemStack != null) {
			ItemStack eventItemStack = event.itemStack;
			Item eventItem = eventItemStack.getItem();
			if (Block.getBlockFromItem(eventItem) != null) {
				Block eventBlock = Block.getBlockFromItem(eventItem);
				if (eventBlock == Witchery.Blocks.OVEN_IDLE) {
					event.toolTip.add(EnumChatFormatting.RED+"Disabled.");
				}
				else if (eventBlock == Witchery.Blocks.OVEN_BURNING) {
					event.toolTip.add(EnumChatFormatting.RED+"Disabled.");
				}
				else if (eventBlock == WitcheryExtras.OVEN_IDLE) {
					event.toolTip.add(EnumChatFormatting.GRAY+"The brand new Witches Oven, added by "+WitcheryExtras.NAME+".");
				}
			}		
		}		
	}
}
