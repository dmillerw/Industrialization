package dmillerw.industrialization.block;

import dmillerw.industrialization.block.tile.TileCore;
import dmillerw.industrialization.block.tile.TileTable;
import net.minecraft.block.material.Material;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockTable extends BlockCore {

    public BlockTable(int id) {
        super(id, Material.rock);

        setHardness(2F);
        setResistance(2F);
    }

    @Override
    public TileCore getTile(int meta) {
        return new TileTable();
    }
}
