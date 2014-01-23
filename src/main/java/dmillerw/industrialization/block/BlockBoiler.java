package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileBoiler;
import dmillerw.industrialization.block.tile.TileCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;

/**
 * Created by Dylan Miller on 1/22/14
 */
public class BlockBoiler extends BlockCore {

    public BlockBoiler(int id) {
        super(id, Material.rock);

        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 1F, 0.9375F);
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return Block.furnaceIdle.getIcon(1, 0);
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
