package dmillerw.industrialization.core.handler;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.ItemData;
import dmillerw.industrialization.core.helper.CoreLogger;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ModHandler {

    public static final ModHandler INSTANCE = new ModHandler();

    private Map<Integer, ItemData> itemIDMapping;

    public ModHandler() {
        try {
            Field idMapField = GameData.class.getDeclaredField("idMap");
            idMapField.setAccessible(true);
            itemIDMapping = (Map<Integer, ItemData>) idMapField.get(null);
        } catch(Exception ex) {
            CoreLogger.warn("Failed to get item mapping. Preferred dust config will be ignored!");
        }
    }

    public String getOwner(ItemStack stack) {
        if (itemIDMapping != null && itemIDMapping.containsKey(stack.getItem().itemID)) {
            return itemIDMapping.get(stack.getItem().itemID).getModId();
        } else {
            return null;
        }
    }

}
