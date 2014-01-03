package dmillerw.industrialization.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.block.tile.TileTable;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.block.Block;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockHandler {

    public static int blockTableID;

    public static Block blockTable;

    public static void initializeIDs(IDAllocator config) {
        blockTableID = config.getBlock("table");
    }

    public static void initialize() {
        blockTable = new BlockTable(blockTableID).setUnlocalizedName("table");
        GameRegistry.registerBlock(blockTable, "table");
        GameRegistry.registerTileEntity(TileTable.class, "table_tile");
    }

}
