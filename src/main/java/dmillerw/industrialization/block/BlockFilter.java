package dmillerw.industrialization.block;

import dmillerw.industrialization.Industrialization;
import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.core.handler.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockFilter extends BlockCore {

    public BlockFilter(int id) {
        super(id, Material.iron);

        setHardness(2F);
        setResistance(2F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);

            if (tile != null && tile instanceof TileFilter) {
                player.openGui(Industrialization.instance, GuiHandler.GUI_FILTER, world, x, y, z);
                return true;
            }
        }

        return false;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return Block.fenceIron.getIcon(0, 0); // Temp?
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
        return new TileFilter();
    }

}
