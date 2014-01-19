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

    public static void initialize(IDAllocator config) {
        /* IDS */
        itemGrindingID = config.getItem("grinding");

        /* INIT */
        itemGrinding = new ItemGrinding(itemGrindingID).setUnlocalizedName("grinding");
        GameRegistry.registerItem(itemGrinding, "grinding");
    }

}
