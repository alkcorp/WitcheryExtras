package alkalus.main.core.util.xmod;

import alkalus.main.core.util.ReflectionUtils;
import net.minecraft.tileentity.TileEntity;

public class ThaumcraftCompat {

	private static Class BlockClass;
	private static Class TileNitor;
	
	static {
		init();
	}
	
	
	public static void init() {
		BlockClass = ReflectionUtils.getClass("thaumcraft.common.config.ConfigBlocks");
		TileNitor = ReflectionUtils.getClass("thaumcraft.common.tiles.TileNitor");
	}
	
	public static boolean isTileNitor(TileEntity tile) {
		return TileNitor != null ? TileNitor.isInstance(tile) : false;
	}
	
	
}
