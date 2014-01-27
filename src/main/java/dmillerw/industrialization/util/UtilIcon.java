package dmillerw.industrialization.util;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Dylan Miller on 1/4/14
 */
public class UtilIcon {

    public static ResourceLocation getLocation(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (stack.itemID <= 4095) {
            return getLocation(Block.blocksList[stack.itemID]);
        } else {
            return getLocation(stack.getItem());
        }
    }

    public static ResourceLocation getLocation(Block block) {
        if (block == null) {
            return null;
        }

        return getLocation(block.getIcon(0, 0), true);
    }

    public static ResourceLocation getLocation(Item item) {
        if (item == null) {
            return null;
        }

        return getLocation(item.getIconFromDamage(0), false);
    }

    public static ResourceLocation getLocation(Icon icon, boolean block) {
        String name = icon.getIconName();
        String mod = "";

        if (!name.contains(":")) {
            mod = "minecraft";
        } else {
            mod = name.substring(0, name.indexOf(":"));
            name = name.trim();
            name = name.substring(name.indexOf(":") + 1); // Strips mod prefix
            if (name.contains(":")) {
                name = name.substring(0, name.indexOf(":")); // Strips any remaining colons. IC2 icon names seem to have one appended on the end
            }
        }

        return new ResourceLocation(mod + ":textures/" + (block ? "blocks/" : "items/") + name + ".png");
    }

    public static Resource getResource(ResourceLocation location) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(location);
    }

}
