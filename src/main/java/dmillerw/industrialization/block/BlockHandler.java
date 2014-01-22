package dmillerw.industrialization.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.block.item.ItemBlockCoreTile;
import dmillerw.industrialization.block.item.ItemBlockGeneral;
import dmillerw.industrialization.block.item.ItemBlockRedstone;
import dmillerw.industrialization.block.tile.TileBlockDetector;
import dmillerw.industrialization.block.tile.TileConveyor;
import dmillerw.industrialization.block.tile.TileFilter;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.block.Block;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockHandler {

    public static int blockGeneralID;
    public static int blockFilterID;
    public static int blockUtilityID;
    public static int blockConveyorID;

    public static Block blockGeneral;
    public static Block blockFilter;
    public static Block blockUtility;
    public static Block blockConveyor;

    public static void initialize(IDAllocator config) {
        /* IDS */
        blockGeneralID = config.getBlock("general");
        blockFilterID = config.getBlock("filter");
        blockUtilityID = config.getBlock("utility_redstone");
        blockConveyorID = config.getBlock("conveyor");

        /* INIT */
        blockGeneral = new BlockGeneral(blockGeneralID).setUnlocalizedName("general");
        GameRegistry.registerBlock(blockGeneral, ItemBlockGeneral.class, "general");

        blockFilter = new BlockFilter(blockFilterID).setUnlocalizedName("filter");
        GameRegistry.registerBlock(blockFilter, "filter");
        GameRegistry.registerTileEntity(TileFilter.class, "filter_tile");

        blockUtility = new BlockUtility(blockUtilityID).setUnlocalizedName("utility");
        GameRegistry.registerBlock(blockUtility, ItemBlockRedstone.class, "utility");
        GameRegistry.registerTileEntity(TileBlockDetector.class, "utility_block_detector_tile");

        blockConveyor = new BlockConveyor(blockConveyorID).setUnlocalizedName("conveyor");
        GameRegistry.registerBlock(blockConveyor, ItemBlockCoreTile.class, "conveyor");
        GameRegistry.registerTileEntity(TileConveyor.class, "conveyor_tile");
    }

}
