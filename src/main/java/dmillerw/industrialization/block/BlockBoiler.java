package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileBoiler;
import dmillerw.industrialization.block.tile.TileCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;

/**
 * Created by Dylan Miller on 1/22/14
 */
public class BlockBoiler extends BlockCore {

    public BlockBoiler(int id) {
        super(id, Material.rock);

        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 1F, 0.9375F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz) {
        if (!world.isRemote) {
            if (!player.isSneaking() && player.getCurrentEquippedItem() == null) {
                TileBoiler tile = (TileBoiler) world.getBlockTileEntity(x, y, z);

                FluidTankInfo waterInfo = tile.getTankInfo(ForgeDirection.UNKNOWN)[0];
                FluidTankInfo steamInfo = tile.getTankInfo(ForgeDirection.UNKNOWN)[1];

                player.addChatMessage(String.format("Water: %s/%s", waterInfo.fluid != null ? waterInfo.fluid.amount : 0, waterInfo.capacity));
                player.addChatMessage(String.format("Steam: %s/%s", steamInfo.fluid != null ? steamInfo.fluid.amount : 0, steamInfo.capacity));
            }
        }

        return true;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return Block.furnaceIdle.getIcon(1, 0);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileCore getTile(int meta) {
        return new TileBoiler();
    }

}
