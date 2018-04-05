package alkalus.main.core.block;

import com.emoniph.witchery.util.ParticleEffect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import com.emoniph.witchery.blocks.BlockFumeFunnel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import alkalus.main.core.WitcheryExtras;

public class BlockFumeFunnelEx extends BlockFumeFunnel {

	public BlockFumeFunnelEx(final boolean filtered) {
		super(filtered);
		this.registerTileEntity = !filtered;
	}

	@Override
	public boolean onBlockActivated(final World world, int posX, int posY, int posZ, final EntityPlayer player,
			final int par6, final float par7, final float par8, final float par9) {
		if (world.isRemote) {
			return true;
		}
		final int meta = world.getBlockMetadata(posX, posY, posZ);
		switch (meta) {
			case 2 :
			case 3 : {
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX + 1, posY, posZ))) {
					++posX;
					break;
				}
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX - 1, posY, posZ))) {
					--posX;
					break;
				}
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX, posY - 1, posZ))) {
					--posY;
					break;
				}
				break;
			}
			case 4 :
			case 5 : {
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX, posY, posZ + 1))) {
					++posZ;
					break;
				}
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX, posY, posZ - 1))) {
					--posZ;
					break;
				}
				if (BlockWitchesOvenEx.isOven(world.getBlock(posX, posY - 1, posZ))) {
					--posY;
					break;
				}
				break;
			}
		}
		if (world.getTileEntity(posX, posY, posZ) instanceof BlockWitchesOvenEx.TileEntityWitchesOvenEx) {
			player.openGui((Object) WitcheryExtras.instance, 2, world, posX, posY, posZ);
		}
		return super.onBlockActivated(world, posX, posY, posZ, player, par6, par7, par8, par9);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
		final int metadata = world.getBlockMetadata(x, y, z);
		if (metadata == 1) {
			final double d0 = x + 0.45f;
			final double d2 = y + 0.4f;
			final double d3 = z + 0.5f;
			world.spawnParticle(ParticleEffect.SMOKE.toString(), d0, d2, d3, 0.0, 0.0, 0.0);
		}
	}

	public static class TileEntityFumeFunnelEx extends TileEntityFumeFunnel {
		public boolean canUpdate() {
			return false;
		}
	}
}