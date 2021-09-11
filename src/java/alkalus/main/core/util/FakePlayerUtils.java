package alkalus.main.core.util;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.FakePlayer;

public class FakePlayerUtils {

	public static final Map<String, EntityPlayer> mCachedFakePlayers = new WeakHashMap<String, EntityPlayer>();
	private static final Class mThaumcraftFakePlayer;
	
	static {
		if (ModCompat.Thaumcraft && ReflectionUtils.doesClassExist("thaumcraft.common.lib.FakeThaumcraftPlayer")) {
			mThaumcraftFakePlayer = ReflectionUtils.getClass("thaumcraft.common.lib.FakeThaumcraftPlayer");
		}
		else {
			mThaumcraftFakePlayer = null;
		}
	}
	
	private static void cacheFakePlayer(EntityPlayer aPlayer) {
		ChunkCoordinates aChunkLocation = aPlayer.getPlayerCoordinates();
		// Cache Fake Player
		if (aPlayer instanceof FakePlayer || (mThaumcraftFakePlayer != null && mThaumcraftFakePlayer.isInstance(aPlayer))
				|| (aPlayer.getCommandSenderName() == null
						|| aPlayer.getCommandSenderName().length() <= 0)
				|| (aPlayer.isEntityInvulnerable() && !aPlayer.canCommandSenderUseCommand(0, "")
						&& (aChunkLocation == null) || (aChunkLocation.posX == 0 && aChunkLocation.posY == 0
								&& aChunkLocation.posZ == 0))) {
			mCachedFakePlayers.put(aPlayer.getUniqueID().toString(), aPlayer);
		}
	}
	
	private static boolean isCachedFakePlayer(String aUUID) {
		return mCachedFakePlayers.containsKey(aUUID);
	}
	
	public static boolean isRealPlayer(Entity aEntity) {
		if (aEntity instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) aEntity;
			ChunkCoordinates aChunkLocation = p.getPlayerCoordinates();			
			if (p instanceof FakePlayer) {
				cacheFakePlayer(p);
				return false;
			}
			if (ModCompat.Thaumcraft && mThaumcraftFakePlayer != null && mThaumcraftFakePlayer.isInstance(p) ) {
				cacheFakePlayer(p);
				return false;
			}
			if (p.getCommandSenderName() == null) {
				cacheFakePlayer(p);
				return false;
			}
			if (p.getCommandSenderName().length() <= 0) {
				cacheFakePlayer(p);
				return false;
			}
			if (p.isEntityInvulnerable() && !p.canCommandSenderUseCommand(0, "") && (aChunkLocation.posX == 0 && aChunkLocation.posY == 0 && aChunkLocation.posZ == 0)) {
				cacheFakePlayer(p);
				return false;
			}
			if (!isCachedFakePlayer(p.getUniqueID().toString())) {
				return true;
			}			
		}		
		return false;
	}
	
}
