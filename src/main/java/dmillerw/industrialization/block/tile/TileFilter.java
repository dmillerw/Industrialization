package dmillerw.industrialization.block.tile;

import dmillerw.industrialization.block.BlockHandler;
import dmillerw.industrialization.recipe.FilterManager;
import dmillerw.industrialization.recipe.FilterRecipe;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Dylan Miller on 1/18/14
 */
public class TileFilter extends TileCore {

    public static int processingTime = 80;

    private Stack<ItemStack> processingQueue = new Stack<ItemStack>();

    private int currentProcessingTime = processingTime;

    private boolean waterFlowing = false;

    @Override
    public void onFirstLoad() {
        onNeighborBlockUpdate();
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && waterFlowing) {
            // Handle insertion of new items
            if (worldObj.getTotalWorldTime() % 5 == 0) {
                List<EntityItem> nearbyItems = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1));

                if (nearbyItems != null && nearbyItems.size() > 0) {
                    for (EntityItem item : nearbyItems) {
                        if (!item.isDead) {
                            FilterRecipe recipe = FilterManager.INSTANCE.getRecipeFor(item.getEntityItem());

                            if (recipe != null) {
                                for (int i=0; i<item.getEntityItem().stackSize; i++) {
                                    processingQueue.push(UtilStack.resize(item.getEntityItem(), 1));
                                }
                                item.setDead();
                            }
                        }
                    }
                }
            }

            // Handle item processing
            if (processingQueue.size() > 0) {
                if (currentProcessingTime == 0) { // If done, pop from stack and produce result
                    ItemStack item = processingQueue.pop();
                    FilterRecipe recipe = FilterManager.INSTANCE.getRecipeFor(item);
                    EntityItem entity = new EntityItem(worldObj, xCoord + 0.5, yCoord - 0.5, zCoord + 0.5, recipe.getOutput());
                    worldObj.spawnEntityInWorld(entity);

                    // If still has items, restart process
                    if (processingQueue.size() > 0) {
                        currentProcessingTime = processingTime;
                    }
                }

                if (currentProcessingTime > 0) {
                    currentProcessingTime--;
                    if (worldObj.getTotalWorldTime() % 4 == 0) { // Add particles four times a second
                        ItemStack dust = FilterManager.INSTANCE.getRecipeFor(processingQueue.peek()).getOutput();
                        worldObj.addBlockEvent(xCoord, yCoord, zCoord, BlockHandler.blockFilterID, dust.itemID, dust.getItemDamage());
                    }
                }
            } else {
                currentProcessingTime = processingTime;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int param) {
        Random rand = new Random();
        for (int i=0; i<10; i++) {
            Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(((double)rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3.rotateAroundY(-rand.nextInt(360) * (float)Math.PI / 180.0F);

            double motionX = vec3.xCoord + 0.5F * (rand.nextFloat() * (rand.nextBoolean() ? -1.0F : 1.0F));
            double motionZ = vec3.xCoord + 0.5F * (rand.nextFloat() * (rand.nextBoolean() ? -1.0F : 1.0F));

            this.worldObj.spawnParticle("iconcrack_" + id + "_" + param, xCoord + 0.5 + motionX, yCoord + 0.5, zCoord + 0.5 + motionZ, vec3.xCoord, vec3.yCoord, vec3.zCoord);
        }
        return true;
    }

    @Override
    public void onBlockBreak() {
        for (ItemStack stack : processingQueue) {
            UtilStack.dropStack(this, stack);
        }
    }

    @Override
    public void onNeighborBlockUpdate() {
        if (!waterFlowing) {
            // int idAbove = worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
            if (worldObj.getBlockMaterial(xCoord, yCoord + 1, zCoord) == Material.water) {
                waterFlowing = true;

                if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord - 1, zCoord, Block.waterMoving.blockID);
                }
            }
        } else {
            int idAbove = worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
            if (idAbove != Block.waterStill.blockID && idAbove != Block.waterMoving.blockID) {
                waterFlowing = false;

                if (worldObj.getBlockMaterial(xCoord, yCoord - 1, zCoord) == Material.water) {
                    worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
                }
            }
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        if (processingQueue.size() > 0) {
            NBTTagList queue = new NBTTagList();
            for (ItemStack stack : processingQueue) {
                if (stack != null) {
                    NBTTagCompound itemTag = new NBTTagCompound();
                    stack.writeToNBT(itemTag);
                    queue.appendTag(itemTag);
                }
            }
            nbt.setTag("queue", queue);
        }

        nbt.setInteger("processingTime", currentProcessingTime);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("queue")) {
            NBTTagList queue = nbt.getTagList("queue");
            for (int i=0; i<queue.tagCount(); i++) {
                NBTTagCompound itemTag = (NBTTagCompound) queue.tagAt(i);
                processingQueue.push(ItemStack.loadItemStackFromNBT(itemTag));
            }
        }

        nbt.setInteger("processingTime", currentProcessingTime);
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