package dmillerw.industrialization.block;

import dmillerw.industrialization.core.IDHandler;
import net.minecraft.block.Block;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class BlockHandler {

    public static int blockTableID;

    public static Block blockTable;

    public static void initializeIDs(IDHandler config) {
        blockTableID = config.getBlock("table");
    }

    public static void initialize() {

    }

}
