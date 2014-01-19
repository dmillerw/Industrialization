package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.item.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class TileFilter extends TileCore implements IInventory {

    private ItemStack[] inv = new ItemStack[getSizeInventory()];

    private boolean waterFlowing = false;

    @Override
    public void onBlockAdded() {
        onNeighborBlockUpdate();
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (!waterFlowing) {
            if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == Block.waterMoving.blockID) {
                waterFlowing = true;

                if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord - 1, zCoord, Block.waterMoving.blockID);
                }
            }
        } else {
            if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != Block.waterMoving.blockID) {
                waterFlowing = false;

                if (worldObj.getBlockMaterial(xCoord, yCoord - 1, zCoord) == Material.water) {
                    worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
                }
            }
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < inv.length; ++i) {
            if (inv[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                inv[i].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }

        nbt.setTag("Items", tagList);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList("Items");

        inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inv.length) {
                inv[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    private ForgeDirection flowDirectionFromDouble(double flow) {
        if (flow != -1000D) {
            double sin = Math.sin(flow) * 0.25;
            double cos = Math.cos(flow) * 0.25;

            if (sin == -0.25) {
                return ForgeDirection.EAST;
            } else if (sin == 0.25) {
                return ForgeDirection.WEST;
            } else if (sin == 0) {
                return ForgeDirection.SOUTH;
            } else if (cos == -0.25) {
                return ForgeDirection.NORTH;
            }
        }

        return ForgeDirection.UNKNOWN;
    }

    /* IINVENTORY */
    @Override
    public int getSizeInventory() {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inv[slot] != null) {
            ItemStack itemStack = inv[slot];
            inv[slot] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inv[slot] = stack;

        if (inv[slot].stackSize > getInventoryStackLimit()) {
            inv[slot].stackSize = getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Filter";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return stack != null && stack.getItem() == ItemHandler.itemGrinding;
    }

}
