package dmillerw.industrialization.item;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.item.Item;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class ItemHandler {

    public static int itemGrindingID;

    public static Item itemGrinding;

    public static void initializeIDs(IDAllocator config) {
        itemGrindingID = config.getItem("grinding");
    }

    public static void initialize() {
        itemGrinding = new ItemGrinding(itemGrindingID);
        GameRegistry.registerItem(itemGrinding, "grinding");
    }

}
