package dmillerw.industrialization.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.block.item.ItemBlockGeneral;
import dmillerw.industrialization.block.item.ItemBlockRedstone;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.block.Block;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockHandler {

    public static int blockGeneralID;
    public static int blockFilterID;
    public static int blockUtilityRedstoneID;

    public static Block blockGeneral;
    public static Block blockFilter;
    public static Block blockUtilityRedstone;

    public static void initialize(IDAllocator config) {
        /* IDS */
        blockGeneralID = config.getBlock("general");
        blockFilterID = config.getBlock("filter");
        blockUtilityRedstoneID = config.getBlock("utility_redstone");

        /* INIT */
        blockGeneral = new BlockGeneral(blockGeneralID).setUnlocalizedName("general");
        GameRegistry.registerBlock(blockGeneral, ItemBlockGeneral.class, "general");

        blockFilter = new BlockFilter(blockFilterID).setUnlocalizedName("filter");
        GameRegistry.registerBlock(blockFilter, "filter");
        GameRegistry.registerTileEntity(TileFilter.class, "filter_tile");

        blockUtilityRedstone = new BlockUtilityRedstone(blockUtilityRedstoneID).setUnlocalizedName("utility_redstone");
        GameRegistry.registerBlock(blockUtilityRedstone, ItemBlockRedstone.class, "utility_redstone");
        GameRegistry.registerTileEntity(TileBlockDetector.class, "utility_redstone_block_detector_tile");
    }

}
