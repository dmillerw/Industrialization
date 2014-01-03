package dmillerw.industrialization.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

    public ItemGrinding(int id) {
        super(id);
    }

}
