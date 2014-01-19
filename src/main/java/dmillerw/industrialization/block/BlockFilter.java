package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.block.tile.TileFilter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;

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
