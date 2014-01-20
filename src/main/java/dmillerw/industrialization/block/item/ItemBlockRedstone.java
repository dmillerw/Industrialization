package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockUtilityRedstone;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemBlockRedstone extends ItemBlock {

    public ItemBlockRedstone(int id) {
        super(id);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile.utility." + BlockUtilityRedstone.NAMES[stack.getItemDamage()];
    }


}
