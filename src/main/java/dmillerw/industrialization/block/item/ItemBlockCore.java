package dmillerw.industrialization.block.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/21/14
 */
public class ItemBlockCore extends ItemBlock {

    public ItemBlockCore(int id) {
        super(id);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!preBlockPlace(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
            return false;
        }

        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
            return false;
        }

        if (!postBlockPlace(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
            return false;
        }

        return true;
    }

    public boolean preBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        return true;
    }

    public boolean postBlockPlace(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        return true;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + ((getNameArray() != null && getNameArray().length > 0) ? "." + getNameArray()[stack.getItemDamage()] : "");
    }

    public String[] getNameArray() {
        return new String[0];
    }

}
