package alkalus.main.core.proxy;


import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;

public class KeyHandler {

	private final Minecraft mc;	
	public KeyHandler(Minecraft mc) {
		this.mc = mc;		
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
	public void onEvent(KeyInputEvent event){
		
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onTickEvent(TickEvent event){
	
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=false)
	public void packetSentEvent(ServerCustomPacketEvent e) {
		
	}

}
