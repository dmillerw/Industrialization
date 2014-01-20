package dmillerw.industrialization.block.item;

import dmillerw.industrialization.block.BlockGeneral;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class ItemBlockGeneral extends ItemBlock {

    public ItemBlockGeneral(int id) {
        super(id);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + BlockGeneral.NAMES[stack.getItemDamage()];
    }


}
