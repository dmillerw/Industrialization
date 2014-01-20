package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.block.BlockHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class TileBlockDetector extends TileCore {

    public ForgeDirection orientation = ForgeDirection.UNKNOWN;

    public ItemStack target;

    public boolean ignoreMeta = false;
    public boolean blockDetected = false;

    @Override
    public void onFirstLoad() {
        onNeighborBlockUpdate();
    }

    @Override
    public int getSignalStrength(ForgeDirection side) {
        return blockDetected ? 15 : 0;
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (!worldObj.isRemote) {
            boolean flag = blockDetected();
            if (flag != blockDetected) {
                blockDetected = flag;
                worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, BlockHandler.blockUtilityRedstoneID);
            }
        }
    }

    private boolean blockDetected() {
        return true;
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        nbt.setByte("orientation", (byte) orientation.ordinal());
        if (target != null) {
            NBTTagCompound tag = new NBTTagCompound();
            target.writeToNBT(tag);
            nbt.setCompoundTag("target", tag);
        }
        nbt.setBoolean("ignoreMeta", ignoreMeta);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        orientation = ForgeDirection.values()[nbt.getByte("orientation")];
        if (nbt.hasKey("target")) {
            target = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("target"));
        } else {
            target = null;
        }
        ignoreMeta = nbt.getBoolean("ignoreMeta");
    }

}
