package dmillerw.industrialization.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class UtilStack {

    public static ItemStack resize(ItemStack stack, int size) {
        ItemStack newStack = stack.copy();
        newStack.stackSize = size;
        if (newStack.stackSize > newStack.getMaxStackSize()) {
            newStack.stackSize = newStack.getMaxStackSize();
        }
        return newStack;
    }

    public static void dropStack(TileEntity tile, ItemStack stack) {
        dropStack(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, stack, true);
    }

    public static void dropStack(World world, double x, double y, double z, ItemStack stack, boolean bounce) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            if (!bounce) {
                d0 = d1 = d2 = 0.5;
            }
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

}
