package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.recipe.FilterManager;
import dmillerw.industrialization.recipe.FilterRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class TileFilter extends TileCore {

    private boolean waterFlowing = false;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && waterFlowing) {
            List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1));

            if (nearbyItems != null && nearbyItems.size() > 0) {
                for (EntityItem item : nearbyItems) {
                    FilterRecipe recipe = FilterManager.INSTANCE.getRecipeFor(item.getEntityItem());

                    if (recipe != null) {
                        EntityItem newItem = new EntityItem(worldObj, xCoord + 0.5, yCoord - 0.5, zCoord + 0.5, recipe.getOutput());
                        worldObj.spawnEntityInWorld(newItem);
                        item.setDead();
                    }
                }
            }
        }
    }

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

    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {

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

}