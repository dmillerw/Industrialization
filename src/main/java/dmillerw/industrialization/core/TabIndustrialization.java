package dmillerw.industrialization.core;

import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class TabIndustrialization extends CreativeTabs {

    public static final CreativeTabs TAB = new TabIndustrialization(ModInfo.NAME);

    public TabIndustrialization(String label) {
        super(label.toLowerCase().replace(" ", "_"));
        LanguageRegistry.instance().addStringLocalization("itemGroup." + label.toLowerCase().replace(" ", "_"), label);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(BlockHandler.blockTable);
    }

}
