package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.recipe.CrushingManager;
import dmillerw.industrialization.recipe.CrushingRecipe;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class TileTable extends TileCore implements IInventory {

    public static final String CONTENTS_TAG = "contents";

    public IInventory contents = new InventoryBasic("Contents", false, 1) {
        @Override
        public void onInventoryChanged() {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    };

    @Override
    public boolean onBlockActivated(EntityPlayer player) {
        ItemStack held = player.getCurrentEquippedItem();
        ItemStack contents = this.contents.getStackInSlot(0) != null ? this.contents.getStackInSlot(0).copy() : null;

        if (held != null && contents == null) {
            contents = held.copy();
            contents.stackSize = 1;
            this.contents.setInventorySlotContents(0, contents);
            held.stackSize--;
            return true;
        } else if (held == null && contents != null) {
            player.setCurrentItemOrArmor(0, contents.copy());
            this.contents.setInventorySlotContents(0, null);
            return true;
        } else if (held != null && contents != null) {
            if (contents.isItemEqual(held) && held.stackSize <= 63) {
                held.stackSize++;
                this.contents.setInventorySlotContents(0, null);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNeighborBlockUpdate() {
        ItemStack contents = this.contents.getStackInSlot(0) != null ? this.contents.getStackInSlot(0).copy() : null;

        if (contents != null) {
            if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == Block.blockIron.blockID && worldObj.getBlockId(xCoord, yCoord + 2, zCoord) == Block.pistonMoving.blockID) {
                CrushingRecipe recipe = CrushingManager.INSTANCE.getRecipeFor(contents);

                if (recipe != null) {
                    this.contents.setInventorySlotContents(0, recipe.getOutput());
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
            }
        }
    }

    @Override
    public void onBlockBreak() {
        ItemStack contents = this.contents.getStackInSlot(0) != null ? this.contents.getStackInSlot(0).copy() : null;

        if (contents != null) {
            UtilStack.dropStack(this, contents);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        ItemStack contents = this.contents.getStackInSlot(0) != null ? this.contents.getStackInSlot(0).copy() : null;

        if (contents != null) {
            NBTTagCompound tag = new NBTTagCompound();
            contents.writeToNBT(tag);
            nbt.setCompoundTag(CONTENTS_TAG, tag);
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(CONTENTS_TAG)) {
            this.contents.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(CONTENTS_TAG)));
        } else {
            this.contents.setInventorySlotContents(0, null);
        }
    }

    /* IINVENTORY */
    @Override
    public int getSizeInventory() {
        return this.contents.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.contents.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int i2) {
        return this.contents.decrStackSize(i, i2);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return this.contents.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.contents.setInventorySlotContents(i, itemStack);
    }

    @Override
    public String getInvName() {
        return this.contents.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        return this.contents.isInvNameLocalized();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

}
