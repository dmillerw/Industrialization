package dmillerw.industrialization.item;

import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.industrialization.core.IDAllocator;
import net.minecraft.item.Item;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class ItemHandler {

    public static int itemGrindingID;
    public static int itemDustID;

    public static Item itemGrinding;
    public static Item itemDust;

    public static void initialize(IDAllocator config) {
        /* IDS */
        itemGrindingID = config.getItem("grinding");
        itemDustID = config.getItem("dust");

        /* INIT */
        itemGrinding = new ItemGrinding(itemGrindingID).setUnlocalizedName("grinding");
        GameRegistry.registerItem(itemGrinding, "grinding");
        itemDust = new ItemDust(itemDustID).setUnlocalizedName("dust");
        GameRegistry.registerItem(itemDust, "dust");
    }

}
