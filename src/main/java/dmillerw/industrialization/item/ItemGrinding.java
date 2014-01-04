package dmillerw.industrialization.item;

import dmillerw.industrialization.core.TabIndustrialization;
import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class ItemGrinding extends Item {

    public static ItemStack getGrinding(String oreTag) {
        ItemStack grinding = new ItemStack(ItemHandler.itemGrinding);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("oreTag", oreTag);
        grinding.setTagCompound(nbt);
        return grinding;
    }

    public static String getTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt.hasKey("oreTag")) {
            return nbt.getString("oreTag");
        } else {
            return "NULL";
        }
    }

    public ItemGrinding(int id) {
        super(id);

        setHasSubtypes(true);
        setCreativeTab(TabIndustrialization.TAB);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String tag = getTag(stack);
        return super.getUnlocalizedName(stack) + "." + tag.toLowerCase();
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            if (ore.getGrinding() != null) {
                list.add(ore.getGrinding());
            }
        }
    }

}
