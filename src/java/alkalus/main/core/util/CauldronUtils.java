package alkalus.main.core.util;

import alkalus.main.core.util.xmod.ThaumcraftCompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CauldronUtils {

	public static boolean hasFireBeneath(BlockPos aPos) {		
		return aPos != null ? getFireHeatLevel(aPos.getDown()) > 0 : false;		
	}
	
	public static int getFireHeatLevel(BlockPos aPos) {
		if (aPos == null) {
			return 0;
		}
		Block aBlock = aPos.getBlockAtPos();
		int aMeta = aPos.getMetaAtPos();
		World aWorld = aPos.world;
		TileEntity aTile = aWorld.getTileEntity(aPos.xPos, aPos.yPos, aPos.zPos);
		
		// Check simple blocks first, as we know they won't have a TileEntity.
		if (aTile == null) {
			if (aBlock == Blocks.fire) {
				//WitcheryExtras.log(0, "Found Fire");
				return 1;
			}
			if (aBlock == Blocks.lava || aBlock == Blocks.flowing_lava) {
				//WitcheryExtras.log(0, "Found Lava");
				return 2;
			}
			
			if (aBlock.getMaterial() == Material.fire) {
				return 1;
			}
		}
		else {
			if (ModCompat.Thaumcraft) {
				if (ThaumcraftCompat.isTileNitor(aTile)) {
					//WitcheryExtras.log(0, "Found Nitor");
					return 3;
				}
			}
		}		
		return 0;
	}	
	
}
