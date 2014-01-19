package dmillerw.industrialization.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.block.item.ItemBlockGeneral;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.block.Block;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockHandler {

    public static int blockGeneralID;
    public static int blockFilterID;

    public static Block blockGeneral;
    public static Block blockFilter;

    public static void initialize(IDAllocator config) {
        /* IDS */
        blockGeneralID = config.getBlock("general");
        blockFilterID = config.getBlock("filter");

        /* INIT */
        blockGeneral = new BlockGeneral(blockGeneralID).setUnlocalizedName("general");
        GameRegistry.registerBlock(blockGeneral, ItemBlockGeneral.class, "general");

        blockFilter = new BlockFilter(blockFilterID).setUnlocalizedName("filter");
        GameRegistry.registerBlock(blockFilter, "filter");
        GameRegistry.registerTileEntity(TileFilter.class, "filter_tile");
    }

}
