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

/**
 * Created by Dylan Miller on 1/19/14
 */
public class TileBlockDetector extends TileCore {

    public ForgeDirection orientation = ForgeDirection.UNKNOWN;

    public IInventory target = new InventoryBasic("Target", false, 1);

    public int range = 1;
    
    public boolean ignoreMeta = false;
    public boolean blockDetected = false;

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
        return blockDetected ? 15 : 0;
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (!worldObj.isRemote) {
            boolean flag = isBlockDetected();
            if (flag != blockDetected) {
                blockDetected = flag;
                worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, BlockHandler.blockUtilityID);
            }
        }
    }

    public void setRange(int range) {
        this.range = range;
        onNeighborBlockUpdate();
    }
    
    public void setIgnoreMetaFlag(boolean flag) {
        this.ignoreMeta = flag;
        onNeighborBlockUpdate();
    }
    
    private boolean isBlockDetected() {
        for (int i=1; i<=range; i++) {
            if (targetIsAtLocation(xCoord + (orientation.offsetX * i),  yCoord + (orientation.offsetY * i), zCoord + (orientation.offsetZ * i))) {
                return true;
            }
        }
        
        return false;
    }

    private boolean targetIsAtLocation(int x, int y, int z) {
        ItemStack targetStack = target.getStackInSlot(0);

        int id = worldObj.getBlockId(x, y, z);
        int meta = worldObj.getBlockMetadata(x, y, z);

        Block block = Block.blocksList[id];

        if (block != null && targetStack == null) {
            return true;
        }

        if (block == null || targetStack == null) {
            return false;
        }

        if (targetStack.getItem() instanceof ItemBlock) {
            return targetStack.itemID == id && (ignoreMeta || targetStack.getItemDamage() == meta);
        } else {
            ItemStack picked = block.getPickBlock(new MovingObjectPosition(xCoord, yCoord, zCoord, orientation.getOpposite().ordinal(), Vec3.createVectorHelper(xCoord, yCoord, zCoord)), worldObj, xCoord, yCoord, zCoord);

            if (picked == null) {
                return false;
            }

            return targetStack.itemID == picked.itemID && (ignoreMeta || targetStack.getItemDamage() == picked.getItemDamage());
        }
    }
    
    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setByte("orientation", (byte) orientation.ordinal());
        if (target.getStackInSlot(0) != null) {
            NBTTagCompound tag = new NBTTagCompound();
            target.getStackInSlot(0).writeToNBT(tag);
            nbt.setCompoundTag("target", tag);
        }
        nbt.setBoolean("ignoreMeta", ignoreMeta);
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
        range = nbt.getInteger("range");
    }

}
