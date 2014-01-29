package dmillerw.industrialization.block.tile;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

public class TileItemDetector extends TileDetector {

    @Override
    public boolean targetDetected() {
        AxisAlignedBB aabb = AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).addCoord(range * orientation.offsetX, range * orientation.offsetY, range * orientation.offsetZ);
        List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
        
        ItemStack targetStack = target.getStackInSlot(0);
        
        if (items != null && items.size() > 0) {
            for (EntityItem item : items) {
                if (!item.isDead && item.getEntityItem() != null) {
                    ItemStack stack = item.getEntityItem();
                    
                    if (targetStack == null) {
                        return true;
                    }
                    
                    String targetOreID = OreDictionary.getOreName(OreDictionary.getOreID(targetStack));
                    String thisOreID = OreDictionary.getOreName(OreDictionary.getOreID(stack));
                    
                    return (targetStack.itemID == stack.itemID && (ignoreMeta || targetStack.getItemDamage() == stack.getItemDamage()) && !useOreDict) || (useOreDict && (targetOreID.equals(thisOreID)));
                }
            }
        }
        
        return false;
    }
    
}
