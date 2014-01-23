package dmillerw.industrialization.block.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

/**
 * Created by Dylan Miller on 1/22/14
 */
public class TileBoiler extends TileCore {

    public static boolean isBaseBoiler(World world, int x, int y, int z) {
        TileEntity below = world.getBlockTileEntity(x, y - 1, z);
        return (below != null && below instanceof TileEntityFurnace);
    }

    public static boolean isBaseBoiler(TileBoiler tile) {
        return isBaseBoiler(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public int internalHeat = 0;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }

        // If is base boiler, should handle furnace control
        if (isBaseBoiler(this)) {
            boolean heatedThisTick = false;

            TileEntityFurnace furnace = (TileEntityFurnace) worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);

            // Can't supply enough heat to the boilers if already cooking something for... reasons
            if (furnace.furnaceCookTime == 0) {
                // If out of fuel, check to see if it can be refueled
                // We do this because the furnace won't refuel itself if not cooking something
                if (furnace.furnaceBurnTime == 0) {
                    ItemStack fuel = furnace.getStackInSlot(1);

                    if (fuel != null && TileEntityFurnace.getItemBurnTime(fuel) > 0) {
                        furnace.furnaceBurnTime = furnace.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(fuel);

                        ItemStack fuelCopy = fuel.copy();
                        fuelCopy.stackSize--;
                        if (fuelCopy.stackSize == 0) {
                            fuelCopy = fuel.getItem().getContainerItemStack(fuel);
                        }
                        furnace.setInventorySlotContents(1, fuelCopy);

                        if (worldObj.getBlockId(furnace.xCoord, furnace.yCoord, furnace.zCoord) != Block.furnaceBurning.blockID) {
                            BlockFurnace.updateFurnaceBlockState(true, worldObj, furnace.xCoord, furnace.yCoord, furnace.zCoord);
                        }
                    }
                } else {
                    if (worldObj.getTotalWorldTime() % 20 == 0) {
                        internalHeat++;
                        heatedThisTick = true;
                    }
                }
            }

            if (!heatedThisTick) {
                if (worldObj.getTotalWorldTime() % 20 == 0 && internalHeat > 0) {
                    internalHeat--;
                }
            }

            //TODO Actual boiling of water...
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {

    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {

    }

}
