package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.recipe.CrushingRecipe;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class TileTable extends TileCore {

    public static final String CONTENTS_TAG = "contents";

    public ItemStack contents;

    @Override
    public boolean onBlockActivated(EntityPlayer player) {
        ItemStack held = player.getCurrentEquippedItem();

        if (held != null && contents == null) {
            contents = held.copy();
            contents.stackSize = 1;
            held.stackSize--;
            return true;
        } else if (held == null && contents != null) {
            player.setCurrentItemOrArmor(0, contents.copy());
            contents = null;
            return true;
        } else if (held != null && contents != null) {
            if (contents.isItemEqual(held) && held.stackSize <= 63) {
                held.stackSize++;
                contents = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (contents != null) {
            if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == Block.blockIron.blockID && worldObj.getBlockId(xCoord, yCoord + 2, zCoord) == Block.pistonMoving.blockID) {
                CrushingRecipe recipe = CrushingManager.INSTANCE.getRecipeFor(contents);

                if (recipe != null) {
                    contents = recipe.getOutput();
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
            }
        }
    }

    @Override
    public void onBlockBreak() {
        if (contents != null) {
            UtilStack.dropStack(this, contents);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        if (contents != null) {
            NBTTagCompound tag = new NBTTagCompound();
            contents.writeToNBT(tag);
            nbt.setCompoundTag(CONTENTS_TAG, tag);
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(CONTENTS_TAG)) {
            contents = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(CONTENTS_TAG));
        } else {
            contents = null;
        }
    }

}
