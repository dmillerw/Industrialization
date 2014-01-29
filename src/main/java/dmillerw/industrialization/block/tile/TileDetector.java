package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.util.UtilEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Dylan Miller on 1/19/14
 */
public abstract class TileDetector extends TileCore {

    public ForgeDirection orientation = ForgeDirection.UNKNOWN;

    public IInventory target = new InventoryBasic("Target", false, 1);

    public int range = 1;
    
    // Detection flags
    public boolean ignoreMeta = false;
    public boolean useOreDict = false;
    
    public boolean targetDetected = false;

    @Override
    public void onFirstLoad() {
        onNeighborBlockUpdate();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 4 == 0) { // Scan for blocks four times a second
            onNeighborBlockUpdate();
        }
    }
    
    @Override
    public void onBlockAdded(EntityPlayer player) {
        if (player != null) {
            orientation = UtilEntity.determine3DOrientation_Forge(worldObj, xCoord, yCoord, zCoord, player);
        }
    }

    @Override
    public int getSignalStrength(ForgeDirection side) {
        return targetDetected ? 15 : 0;
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (!worldObj.isRemote) {
            boolean flag = targetDetected();
            if (flag != targetDetected) {
                targetDetected = flag;
                worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, BlockHandler.blockUtilityID);
            }
        }
    }

    public void setRange(int range) {
        this.range = range;
        onNeighborBlockUpdate();
    }
    
    public void setUseOreDictionaryFlag(boolean flag) {
        this.useOreDict = flag;
        onNeighborBlockUpdate();
    }
    
    public void setIgnoreMetaFlag(boolean flag) {
        this.ignoreMeta = flag;
        onNeighborBlockUpdate();
    }
    
    public abstract boolean targetDetected();
    
    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setByte("orientation", (byte) orientation.ordinal());
        if (target.getStackInSlot(0) != null) {
            NBTTagCompound tag = new NBTTagCompound();
            target.getStackInSlot(0).writeToNBT(tag);
            nbt.setCompoundTag("target", tag);
        }
        nbt.setBoolean("ignoreMeta", ignoreMeta);
        nbt.setBoolean("useOreDict", useOreDict);
        nbt.setInteger("range", range);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        orientation = ForgeDirection.values()[nbt.getByte("orientation")];
        if (nbt.hasKey("target")) {
            target.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("target")));
        } else {
            target.setInventorySlotContents(0, null);
        }
        ignoreMeta = nbt.getBoolean("ignoreMeta");
        useOreDict = nbt.getBoolean("useOreDict");
        range = nbt.getInteger("range");
    }

}
