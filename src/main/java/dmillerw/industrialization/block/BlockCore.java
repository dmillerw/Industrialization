package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.core.TabIndustrialization;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/1/14
 */
public abstract class BlockCore extends BlockContainer {

    public BlockCore(int id, Material material) {
        super(id, material);

        setCreativeTab(TabIndustrialization.TAB);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);

            if (tile != null && tile instanceof TileCore) {
                return ((TileCore)tile).onBlockActivated(player);
            }
        }

        return false;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TileCore) {
            ((TileCore)tile).onBlockAdded();
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TileCore) {
            ((TileCore)tile).onNeighborBlockUpdate();
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TileCore) {
            ((TileCore)tile).onBlockBreak();
        }

        super.breakBlock(world, x, y, z, id, meta);
    }


    public abstract dmillerw.industrialization.block.tile.TileCore getTile(int meta);

    /* IGNORE */
    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return getTile(meta);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

}
