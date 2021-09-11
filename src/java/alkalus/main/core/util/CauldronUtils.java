package alkalus.main.core.util;

import alkalus.main.core.util.xmod.ThaumcraftCompat;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CauldronUtils {

	public static boolean hasFireBeneath(BlockPos aPos) {		
		return getFireHeatLevel(aPos.getDown()) > 0;		
	}
	
	public static int getFireHeatLevel(BlockPos aPos) {
		
		Block aBlock = aPos.getBlockAtPos();
		int aMeta = aPos.getMetaAtPos();
		World aWorld = aPos.world;
		TileEntity aTile = aWorld.getTileEntity(aPos.xPos, aPos.yPos, aPos.zPos);
		
		// Check simple blocks first, as we know they won't have a TileEntity.
		if (aTile == null) {
			if (aBlock == Blocks.fire) {
				return 1;
			}
			if (aBlock == Blocks.lava) {
				return 2;
			}
		}
		else {
			if (ModCompat.Thaumcraft) {
				if (ThaumcraftCompat.isTileNitor(aTile)) {
					return 3;
				}
			}
		}		
		return 0;
	}
	
	
}
