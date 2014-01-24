package dmillerw.industrialization.block.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Dylan Miller on 1/22/14
 */
public class TileBoiler extends TileCore implements IFluidHandler {

    public static final int HEAT_CAP = 200;

    public static boolean isBaseBoiler(World world, int x, int y, int z) {
        TileEntity below = world.getBlockTileEntity(x, y - 1, z);
        return (below == null || (below != null && below instanceof TileEntityFurnace));
    }

    public static boolean isBaseBoiler(TileBoiler tile) {
        return isBaseBoiler(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    protected FluidTank waterTank = new FluidTank(FluidRegistry.WATER, 0, 8000);
    protected FluidTank steamTank = new FluidTank(FluidRegistry.getFluid("steam"), 0, 8000);

    public int internalHeat = 0;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }

        // Transition fluids based on adjacent boilers
        if (waterTank.getFluidAmount() >= 100) {
            // Water drips down into lower boilers
            TileBoiler below = getBoilerBelow(this);

            if (below != null && below.waterTank.getFluidAmount() + 100 <= below.waterTank.getCapacity()) {
                below.waterTank.fill(new FluidStack(FluidRegistry.WATER, 100), true);
                waterTank.drain(100, true);
            }
        }

        if (steamTank.getFluidAmount() >= 100) {
            // Steam rises into above boilers
            TileBoiler above = getBoilerAbove(this);

            if (above != null && above.steamTank.getFluidAmount() + 100 <= above.steamTank.getCapacity()) {
                above.steamTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), 100), true);
                steamTank.drain(100, true);
            }
        }

        // If is base boiler, should handle furnace control and water heating
        if (isBaseBoiler(this)) {
            boolean heatedThisTick = false;

            TileEntityFurnace furnace = (TileEntityFurnace) worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);

            // Can't supply enough heat to the boilers if already cooking something for... reasons
            if (furnace != null && furnace.furnaceCookTime == 0) {
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
                        if (internalHeat < HEAT_CAP) {
                            internalHeat++;
                        }
                        heatedThisTick = true;
                    }
                }
            }

            if (!heatedThisTick) {
                if (worldObj.getTotalWorldTime() % 20 == 0 && internalHeat > 0) {
                    internalHeat--;
                }
            }

            //TODO Better boiling based on heat?

            if (worldObj.getTotalWorldTime() % 20 == 0 && internalHeat > 0) {
                TileBoiler boiler = this;

                while (true) {
                    if (boiler != null) {
                        int steamProduced = 100; // Temp for now

                        FluidStack waterDrained = boiler.waterTank.drain(100, false);
                        int steamFilled = boiler.steamTank.fill(FluidRegistry.getFluidStack("steam", steamProduced), false);

                        if ((waterDrained != null && waterDrained.amount == 100) && steamFilled == steamProduced) {
                            boiler.waterTank.drain(100, true);
                            boiler.steamTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), steamProduced), true);

                            return;
                        }
                    } else {
                        break;
                    }

                    boiler = getBoilerAbove(boiler);
                }
            }
        }
    }

    private TileBoiler getTopBoiler() {
        return getTopBoiler(this);
    }

    private TileBoiler getBottomBoiler() {
        return getBottomBoiler(this);
    }

    public static TileBoiler getTopBoiler(TileBoiler tile) {
        TileBoiler last = tile;

        while (true) {
            TileEntity below = getBoilerAbove(last);
            if (below == null || !(below instanceof TileBoiler)) {
                break;
            } else {
                last = (TileBoiler) below;
            }
        }

        return last;
    }

    public static TileBoiler getBottomBoiler(TileBoiler tile) {
        TileBoiler last = tile;

        while (true) {
            TileEntity above = getBoilerBelow(last);
            if (above == null || !(above instanceof TileBoiler)) {
                break;
            } else {
                last = (TileBoiler) above;
            }
        }

        return last;
    }

    public static TileBoiler getBoilerAbove(TileBoiler tile) {
        TileEntity boiler = tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord + 1, tile.zCoord);
        if (boiler != null && boiler instanceof TileBoiler) {
            return (TileBoiler) boiler;
        } else {
            return null;
        }
    }

    public static TileBoiler getBoilerBelow(TileBoiler tile) {
        TileEntity boiler = tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord - 1, tile.zCoord);
        if (boiler != null && boiler instanceof TileBoiler) {
            return (TileBoiler) boiler;
        } else {
            return null;
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        if (waterTank != null) {
            NBTTagCompound waterTag = new NBTTagCompound();
            waterTank.writeToNBT(waterTag);
            nbt.setCompoundTag("waterTank", waterTag);
        }

        if (steamTank != null) {
            NBTTagCompound steamTag = new NBTTagCompound();
            steamTank.writeToNBT(steamTag);
            nbt.setCompoundTag("steamTank", steamTag);
        }

        nbt.setInteger("heat", internalHeat);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("waterTank")) {
            waterTank.readFromNBT(nbt.getCompoundTag("waterTank"));
        }

        if (nbt.hasKey("steamTank")) {
            steamTank.readFromNBT(nbt.getCompoundTag("steamTank"));
        }

        internalHeat = nbt.getInteger("heat");
    }

    /* IFLUIDHANDLER */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || resource.getFluid() != FluidRegistry.WATER) {
            return 0;
        }

        resource = resource.copy();
        int totalUsed = 0;
        TileBoiler targetBoiler = getBottomBoiler();

        while (targetBoiler != null && resource.amount > 0) {
            int used = targetBoiler.waterTank.fill(resource, doFill);
            resource.amount -= used;
            totalUsed += used;

            targetBoiler = getBoilerAbove(targetBoiler);
        }

        return totalUsed;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return steamTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == FluidRegistry.WATER;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluid.getName().equalsIgnoreCase("steam"); // Temp
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {waterTank.getInfo(), steamTank.getInfo()};
    }

}
